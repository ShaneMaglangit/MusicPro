package com.example.musicpro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicpro.databinding.ActivityMainBinding;
import com.example.musicpro.venue.VenueActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the activity
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Attach a click listener to start the venue activity.
        // Starts the venue activity on click.
        binding.buttonVenues.setOnClickListener((view) -> {
            Intent intent = new Intent(this, VenueActivity.class);
            startActivity(intent);
        });

        // TODO: Map feature
        binding.buttonMap.setOnClickListener((view) -> {
            // Temporarily show a error on a toast
            Toast.makeText(this, "The map feature is not yet implemented", Toast.LENGTH_SHORT).show();
        });

        setContentView(binding.getRoot());
    }
}
