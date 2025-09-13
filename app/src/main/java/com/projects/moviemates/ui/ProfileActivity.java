// File: app/src/main/java/com/projects/moviemates/ui/ProfileActivity.java
package com.projects.moviemates.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// CORRECTED: Import Picasso and the new CircleTransform class
import com.projects.moviemates.R;
import com.projects.moviemates.databinding.ActivityProfileBinding;
import com.projects.moviemates.utils.CircleTransform; // Import the new class
//import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso; // Import Picasso

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        populateUserProfile();

        binding.logoutButton.setOnClickListener(v -> signOut());
    }

    private void populateUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            binding.profileName.setText(user.getDisplayName());
            binding.profileEmail.setText(user.getEmail());

            // REPLACED GLIDE WITH PICASSO
            Picasso.get()
                    .load(user.getPhotoUrl())
                    .transform(new CircleTransform()) // Apply the circular transformation
                    .placeholder(R.drawable.ic_profile_person) // Placeholder icon
                    .error(R.drawable.ic_profile_person) // Error fallback icon
                    .into(binding.profileImage);
        }
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
//        LoginManager.getInstance().logOut();

        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}