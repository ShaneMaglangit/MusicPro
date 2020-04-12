package com.example.musicpro.venue;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;
import com.example.musicpro.data.VenueContract.VenueEntry;
import com.example.musicpro.data.VenueDbHelper;
import com.example.musicpro.databinding.FragmentVenueDetailBinding;

public class VenueDetailFragment extends Fragment {
    private FragmentVenueDetailBinding binding;
    private VenueDbHelper venueDbHelper;
    private SQLiteDatabase venueDb;
    private long venueId;
    private boolean isNew;

    public VenueDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment
        binding = FragmentVenueDetailBinding.inflate(inflater, container, false);

        // Instantiate the SQLite-related objects.
        venueDbHelper = new VenueDbHelper(getContext());
        venueDb = venueDbHelper.getWritableDatabase();

        // Get the arguments passed from the previous fragment.
        venueId = VenueDetailFragmentArgs.fromBundle(getArguments()).getId();
        isNew = VenueDetailFragmentArgs.fromBundle(getArguments()).getIsNew();

        // If the venue is not new and already exists, then load the venue from the database.
        if(!isNew) {
            Venue venue = loadVenue();

            // Update the view with the values from the retrieved venue.
            binding.editName.setText(venue.getName());
            binding.editAddress.setText(venue.getAddress());
            binding.editOpeningTime.setText(venue.getOpeningTime());
        }

        // Show a confirmation dialog when delete is pressed.
        binding.buttonDelete.setOnClickListener((view) -> {
            showDeleteConfirmation();
        });

        // Check the detail fields when done is pressed.
        binding.buttonDone.setOnClickListener((view) -> {
            checkDetails();
        });

        return binding.getRoot();
    }

    /**
     * Used to get the venue from the database.
     * @return venue retrieved
     */
    public Venue loadVenue() {
        String name, address, openingTime;
        Venue venue;

        // The columns needed after the query
        String[] projection = {
                BaseColumns._ID,
                VenueEntry.COLUMN_NAME_NAME,
                VenueEntry.COLUMN_NAME_ADDRESS,
                VenueEntry.COLUMN_NAME_OPENING_TIME
        };

        // Filter the results. Only select the venue with matching id.
        String selection = VenueEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(venueId) };

        // Perform the query
        Cursor cursor = venueDb.query(
                VenueEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        );

        // Move the cursor to the first venue retrieved.
        cursor.moveToFirst();

        name = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_NAME));
        address = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_ADDRESS));
        openingTime = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_OPENING_TIME));
        venue = new Venue(venueId, name, address, openingTime);

        cursor.close();
        return venue;
    }

    /**
     * Used to update or save the venue.
     * @param name name of the venue
     * @param address address of the venue
     * @param openingTime opening time of the venue
     */
    private void saveVenue(String name, String address, String openingTime) {
        ContentValues values = new ContentValues();

        // If an existing venue is being created, add the current id to the venue
        if (!isNew) values.put(VenueEntry._ID, venueId);
        values.put(VenueEntry.COLUMN_NAME_NAME, name);
        values.put(VenueEntry.COLUMN_NAME_ADDRESS, address);
        values.put(VenueEntry.COLUMN_NAME_OPENING_TIME, openingTime);

        // Insert the created venue to the database.
        // If primary key already exists, replace the older entity to update it.
        venueDb.insertWithOnConflict(VenueEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * Used to check if the detail fields are filled up.
     */
    private void checkDetails() {
        String name = binding.editName.getText().toString();
        String address = binding.editAddress.getText().toString();
        String openingTime = binding.editOpeningTime.getText().toString();

        // If both fields is empty, then delete the venue from the database.
        if(name.isEmpty() && address.isEmpty()) {
            if(!isNew) deleteVenueFromDb();
        }
        // If either name or address is empty, show the incomplete detail warning.
        else if(name.isEmpty() || address.isEmpty()) {
            showIncompleteDetailsWarning();
            return;
        }
        else {
            saveVenue(name, address, openingTime);
        }

        // Navigate back to the venue list fragment.
        NavHostFragment.findNavController(this).navigateUp();
    }

    /**
     * Used to delete a venue from the database.
     */
    private void deleteVenueFromDb() {
        String selection = VenueEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(venueId) };
        venueDb.delete(VenueEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Show an alert dialog for incomplete fields / details.
     */
    private void showIncompleteDetailsWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.warning_dialog_title);
        builder.setMessage(R.string.warning_dialog_message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    /**
     * Show an alert dialog for confirmation of deleting a venue.
     */
    private void showDeleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton("YES", (dialog, which) -> {
            // If the view is not new, then delete it from the database.
            if(!isNew) deleteVenueFromDb();
            NavHostFragment.findNavController(this).navigateUp();
        });
        builder.setNegativeButton("NO", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
}
