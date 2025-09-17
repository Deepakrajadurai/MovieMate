package com.projects.moviemates;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

// These are the imports needed for the project structure and common Android classes
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// These are the imports needed for the Google Sign-in and Firebase Authentication
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

// This is the import for the Data Binding class generated from your XML
import com.projects.moviemates.databinding.ActivityMainBinding;

// This is the import for your navigation target
import com.projects.moviemates.ui.GenreSearchActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflate the layout using Data Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize mAuth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in and navigate if they are
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToGenreSearch();
        }

        // --- Google Sign-In Setup ---
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up click listeners for the button
        binding.googleSignInButton.setOnClickListener(v -> {
            signInWithGoogle();
        });
    }

    private void navigateToGenreSearch() {
        Intent intent = new Intent(MainActivity.this, GenreSearchActivity.class);
        startActivity(intent);
        finish();
    }

    // --- Google Sign-In Logic ---
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    // The launcher to handle the Google Sign-in result
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this, authTask -> {
                                if (authTask.isSuccessful()) {
                                    // Sign in success, navigate to the next screen
                                    navigateToGenreSearch();
                                } else {
                                    Toast.makeText(MainActivity.this, "Google Sign-in failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (ApiException e) {
                    // Google Sign In failed, inform the user
                    Toast.makeText(MainActivity.this, "Google Sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
}