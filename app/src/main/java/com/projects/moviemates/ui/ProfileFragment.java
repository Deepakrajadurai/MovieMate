package com.projects.moviemates.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.projects.moviemates.R;
// Import NavController if you need to navigate from here to other fragments within the same graph
// import androidx.navigation.NavController;
// import androidx.navigation.Navigation;

// Import your ViewBinding class
import com.projects.moviemates.databinding.FragmentProfileBinding;
// Assuming you have an AuthActivity for login/logout
// import com.projects.moviemates.AuthActivity;
// If using Firebase Auth:
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    // private FirebaseAuth mAuth; // If using Firebase

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUserProfile();

        binding.buttonEditProfile.setOnClickListener(v -> {
            // TODO: Implement navigation to an EditProfileFragment or Activity
            Toast.makeText(getContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show();
        });

        binding.buttonSettings.setOnClickListener(v -> {
            // TODO: Implement navigation to a SettingsFragment or Activity
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
        });

        binding.buttonLogout.setOnClickListener(v -> {
            // TODO: Implement logout logic
            // Example for Firebase Auth:
            // mAuth.signOut();
            // Intent intent = new Intent(getActivity(), AuthActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);
            // if (getActivity() != null) {
            //     getActivity().finish();
            // }
            Toast.makeText(getContext(), "Logout clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserProfile() {
        // TODO: Load actual user data (from SharedPreferences, ViewModel, Firebase, etc.)
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        // if (currentUser != null) {
        //     binding.textViewProfileName.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "MovieMate User");
        //     binding.textViewProfileEmail.setText(currentUser.getEmail());
        //     // Load profile picture using Glide or Picasso
        //     // if (currentUser.getPhotoUrl() != null) {
        //     //     Glide.with(this).load(currentUser.getPhotoUrl()).circleCrop().into(binding.imageViewProfilePicture);
        //     // } else {
        //     //     binding.imageViewProfilePicture.setImageResource(R.drawable.ic_default_profile); // a default placeholder
        //     // }
        // } else {
        // Fallback or navigate to login
        binding.textViewProfileName.setText("Guest User");
        binding.textViewProfileEmail.setText("guest@example.com");
        binding.imageViewProfilePicture.setImageResource(R.drawable.ic_launcher_foreground); // Default placeholder
        // }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
