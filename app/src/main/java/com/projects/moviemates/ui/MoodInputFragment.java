package com.projects.moviemates.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

// Import your ViewBinding class
import com.projects.moviemates.databinding.FragmentMoodInputBinding;
// Import R if you need to navigate (e.g., to a results screen)
// import com.projects.moviemates.R;


public class MoodInputFragment extends Fragment {

    private FragmentMoodInputBinding binding;
    private NavController navController; // If you need to navigate from here

    public MoodInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoodInputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize NavController if needed for further navigation
        // navController = Navigation.findNavController(view);

        binding.buttonGetMoodRecommendations.setOnClickListener(v -> {
            String mood = "";
            if (binding.editTextMood.getText() != null) {
                mood = binding.editTextMood.getText().toString().trim();
            }

            if (TextUtils.isEmpty(mood)) {
                binding.textInputLayoutMood.setError("Please enter your mood");
                // Alternatively, show a Toast:
                // Toast.makeText(getContext(), "Please enter your mood", Toast.LENGTH_SHORT).show();
            } else {
                binding.textInputLayoutMood.setError(null); // Clear error
                // TODO: Implement logic to get recommendations based on mood
                // This could involve:
                // 1. Making an API call
                // 2. Querying a local database
                // 3. Applying some business logic
                Toast.makeText(getContext(), "Searching for movies for mood: " + mood, Toast.LENGTH_LONG).show();

                // Example: Navigate to a results screen (you'd need to define this action in nav_graph.xml)
                // Bundle bundle = new Bundle();
                // bundle.putString("user_mood", mood);
                // navController.navigate(R.id.action_moodInputFragment_to_movieResultsFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
