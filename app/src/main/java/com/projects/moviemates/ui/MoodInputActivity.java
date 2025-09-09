// File: app/src/main/java/com/example/moviemate/ui/MoodInputActivity.java
package com.projects.moviemates.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.moviemates.databinding.ActivityMoodInputBinding;
import com.google.android.material.chip.Chip;

public class MoodInputActivity extends AppCompatActivity {

    private ActivityMoodInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoodInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.findMoviesButton.setOnClickListener(v -> {
            int selectedChipId = binding.moodChipGroup.getCheckedChipId();

            if (selectedChipId != -1) {
                Chip selectedChip = findViewById(selectedChipId);
                String mood = selectedChip.getText().toString();

                // Pass the selected mood to the GenreSearchActivity to handle the logic
                Intent intent = new Intent(MoodInputActivity.this, GenreSearchActivity.class);
                intent.putExtra("USER_MOOD", mood);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select a mood first!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}