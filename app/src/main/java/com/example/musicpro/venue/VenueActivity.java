package com.example.musicpro.venue;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.musicpro.R;
import com.example.musicpro.databinding.ActivityVenueBinding;

public class VenueActivity extends AppCompatActivity {
    private ActivityVenueBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the activity.
        binding = ActivityVenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Show or hide the new venue action button depending on the current fragment.
        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            switch(destination.getId()) {
                case R.id.venueListFragment:
                    binding.buttonNewVenue.setVisibility(View.VISIBLE);
                    break;
                case R.id.venueDetailFragment:
                    binding.buttonNewVenue.setVisibility(View.INVISIBLE);
                    break;
            }
        });

        // Create a new venue when action button is clicked.
        binding.buttonNewVenue.setOnClickListener((view) -> {
            navController.navigate(VenueListFragmentDirections.openDetailAction(0, true));
        });
    }
}
