package com.projects.moviemates.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

// Import your ViewBinding class (generated from fragment_home.xml)
import com.projects.moviemates.R; // For navigation action IDs
import com.projects.moviemates.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding; // View Binding variable
    private NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }

    // Consider removing newInstance if not passing arguments, or adapt as needed.
    // public static HomeFragment newInstance(String param1, String param2) { ... }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getArguments() != null) { ... }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize NavController
        navController = Navigation.findNavController(view);

        // Set up click listeners for navigation
        binding.buttonToGenreSearch.setOnClickListener(v -> {
            // Use the action ID defined in your navigation graph
            navController.navigate(R.id.action_homeContentFragment_to_genreSearchFragment);
        });

        binding.buttonToMoodInput.setOnClickListener(v -> {
            // Use the action ID defined in your navigation graph
            navController.navigate(R.id.action_homeContentFragment_to_moodInputFragment);
        });

        binding.buttonToProfile.setOnClickListener(v -> {
            // Use the action ID defined in your navigation graph
            navController.navigate(R.id.action_homeContentFragment_to_profileFragment);
        });

        // TODO: Add any other UI setup or logic for your HomeFragment here
        // For example, fetching and displaying a list of popular movies.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }
}
