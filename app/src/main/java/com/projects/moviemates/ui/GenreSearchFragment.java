package com.projects.moviemates.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
// Import your ViewBinding class (generated from fragment_genre_search.xml)
import com.projects.moviemates.databinding.FragmentGenreSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreSearchFragment extends Fragment {

    // Declare the binding variable
    private FragmentGenreSearchBinding binding;

    // List to hold our genres (you'd typically get this from a ViewModel or data source)
    private List<String> allGenres = Arrays.asList(
            "Action", "Comedy", "Drama", "Horror", "Sci-Fi",
            "Romance", "Thriller", "Adventure", "Animation", "Fantasy",
            "Mystery", "Documentary", "Family", "Crime", "Musical"
    );

    // List to hold currently selected genres
    private List<String> selectedGenres = new ArrayList<>();

    public GenreSearchFragment() {
        // Required empty public constructor
    }

    // You can remove the newInstance factory method and parameters
    // if you are not passing any specific data to this fragment upon creation
    // via arguments. If you are, keep and adapt it. For now, let's simplify.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If you had arguments passed via newInstance, retrieve them here
        // For example:
        // if (getArguments() != null) {
        //     // mParam1 = getArguments().getString(ARG_PARAM1);
        // }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentGenreSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateGenreChips();
        setupApplyFiltersButton();
    }

    private void populateGenreChips() {
        if (getContext() == null) return; // Ensure context is available

        binding.chipGroupGenres.removeAllViews(); // Clear any existing chips if repopulating

        for (String genreName : allGenres) {
            Chip chip = new Chip(getContext());
            chip.setText(genreName);
            chip.setCheckable(true); // Allow the chip to be selected/deselected
            // chip.setChipIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_your_genre_icon)); // Optional: if you have icons per genre
            chip.setEnsureMinTouchTargetSize(true); // Recommended for accessibility

            // Listener for individual chip selection changes
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedGenres.add(genreName);
                    // You can add a Toast or log for debugging
                    // Toast.makeText(getContext(), genreName + " selected", Toast.LENGTH_SHORT).show();
                } else {
                    selectedGenres.remove(genreName);
                    // Toast.makeText(getContext(), genreName + " deselected", Toast.LENGTH_SHORT).show();
                }
            });
            binding.chipGroupGenres.addView(chip);
        }
    }

    private void setupApplyFiltersButton() {
        binding.buttonApplyGenreFilters.setOnClickListener(v -> {
            if (selectedGenres.isEmpty()) {
                Toast.makeText(getContext(), "Please select at least one genre.", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Implement your filter logic here
                // For example, navigate to a results screen or update a list
                String message = "Applying filters for: " + String.join(", ", selectedGenres);
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                // Example: Pass selected genres to another fragment or activity,
                // or use a Shared ViewModel to communicate the filter.
                // Bundle bundle = new Bundle();
                // bundle.putStringArrayList("selected_genres", new ArrayList<>(selectedGenres));
                // NavHostFragment.findNavController(this).navigate(R.id.action_to_results_screen, bundle);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding when the view is destroyed to avoid memory leaks
        binding = null;
    }
}

