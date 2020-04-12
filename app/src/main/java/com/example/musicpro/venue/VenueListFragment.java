package com.example.musicpro.venue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.musicpro.adapter.VenueListAdapter;
import com.example.musicpro.data.Venue;
import com.example.musicpro.data.VenueContract.VenueEntry;
import com.example.musicpro.data.VenueDbHelper;
import com.example.musicpro.databinding.FragmentVenueListBinding;

import java.util.ArrayList;

public class VenueListFragment extends Fragment {
    private FragmentVenueListBinding binding;
    private VenueListAdapter venueListAdapter;
    private ArrayList<Venue> venues;

    public VenueListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVenueListBinding.inflate(inflater, container, false);
        venues = getVenueFromDb();
        venueListAdapter = new VenueListAdapter(getContext(), venues);

        binding.listVenue.setAdapter(venueListAdapter);
        binding.listVenue.setOnItemClickListener(((parent, view, position, id) -> {
            Venue venue = venues.get(position);
            NavHostFragment.findNavController(this).navigate(
                    VenueListFragmentDirections.openDetailAction(venue.getId(), false)
            );
        }));

        return binding.getRoot();
    }

    private ArrayList<Venue> getVenueFromDb() {
        VenueDbHelper venueDbHelper = new VenueDbHelper(getContext());
        SQLiteDatabase venueDb = venueDbHelper.getReadableDatabase();
        ArrayList<Venue> venues = new ArrayList<>();

        String[] projection = {
                VenueEntry._ID,
                VenueEntry.COLUMN_NAME_NAME,
                VenueEntry.COLUMN_NAME_ADDRESS,
                VenueEntry.COLUMN_NAME_OPENING_TIME
        };

        Cursor cursor = venueDb.query(
                VenueEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(VenueEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_NAME));
            String address = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_ADDRESS));
            String openingTime = cursor.getString(cursor.getColumnIndex(VenueEntry.COLUMN_NAME_OPENING_TIME));
            Venue tempVenue = new Venue(id, name, address, openingTime);
            venues.add(tempVenue);
        }

        cursor.close();
        return venues;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
