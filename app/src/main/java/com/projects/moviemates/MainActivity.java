package com.projects.moviemates;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
// View, ImageView, TextView are fine if used, e.g., in updateNavHeader (which is commented out)
// import android.view.View;
// import android.widget.ImageView;
// import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.projects.moviemates.databinding.ActivityMainBinding;
import com.projects.moviemates.sensors.CameraScanActivity;
import com.projects.moviemates.ui.ChatActivity;
// Firebase imports would be needed if you uncomment Firebase related code
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding; // Correct: Using ViewBinding
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawerLayout; // Correct: drawerLayout is a field
    private NavigationView navigationView; // Correct: navigationView is a field

    // private FirebaseAuth mAuth; // Uncomment if using Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // mAuth = FirebaseAuth.getInstance(); // Uncomment if using Firebase Auth

        // CRITICAL LINE for using your Toolbar:
        // This assumes your Toolbar in XML has android:id="@+id/top_app_bar"
        //setSupportActionBar(binding.topAppBar);

        // Drawer and Navigation setup - this looks correct
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;

        // Define top-level destinations for AppBarConfiguration
        // These IDs should be present in your navigation graph (e.g., home_nav_graph.xml)
        // and in your navigation menu (e.g., your_navigation_menu.xml)
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_content, R.id.nav_genre_search, R.id.nav_mood_input, R.id.nav_profile)
                .setOpenableLayout(drawerLayout) // Use setOpenableLayout for DrawerLayout
                .build();

        // Ensure R.id.nav_host_fragment_content_home is the ID of your NavHostFragment
        // in activity_main.xml
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Setup ActionBar with NavController
        // This makes the Toolbar aware of navigation changes (e.g., updating title, showing Up button)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // Setup NavigationView with NavController
        // This handles click events on navigation menu items to navigate
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set your custom listener if you need to handle some items manually or do extra actions
        navigationView.setNavigationItemSelectedListener(this);

        binding.fabChatbot.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // updateNavHeader(); // Uncomment and implement if needed
    }

    /*
    // Example: Method to update navigation drawer header with user info
    // Ensure IDs (nav_header_title, nav_header_subtitle, nav_header_logo) exist in nav_header_main.xml
    private void updateNavHeader() {
        FirebaseUser currentUser = mAuth.getCurrentUser(); // Assuming mAuth is initialized
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_title);
        TextView navUserEmail = headerView.findViewById(R.id.nav_header_subtitle);
        // ImageView navUserImage = headerView.findViewById(R.id.nav_header_logo);

        if (currentUser != null) {
            navUsername.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "MovieMate User");
            navUserEmail.setText(currentUser.getEmail());
            // Example: Load image with Glide or Picasso
            // Glide.with(this).load(currentUser.getPhotoUrl()).placeholder(R.mipmap.ic_launcher_round).into(navUserImage);
        } else {
            navUsername.setText(getString(R.string.app_name)); // Use getString for resource strings
            navUserEmail.setText("");
            // navUserImage.setImageResource(R.mipmap.ic_launcher_round); // Default image
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Ensure R.menu.top_app_bar_menu exists in res/menu/
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here.
        // The NavController will automatically handle clicks on the Up button
        // if setupActionBarWithNavController is used.
        // Ensure R.id.action_scan exists in top_app_bar_menu.xml
        if (item.getItemId() == R.id.action_scan) {
            Intent intent = new Intent(this, CameraScanActivity.class);
            startActivity(intent);
            return true;
        }
        // Let NavigationUI try to handle it for navigation actions
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // This is called when the Up button (<-) in the ActionBar is pressed.
        // NavigationUI.navigateUp will handle navigating to the parent destination
        // from the navigation graph.
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // If the navigation drawer is open, close it. Otherwise, perform default back press.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // This method is called when an item in the navigation drawer is selected.
        // NavigationUI.setupWithNavController will handle most navigation automatically.
        // You only need to implement this if you want to override default behavior
        // or handle items not directly tied to navigation graph destinations (like Logout).

        int id = item.getItemId();

        // Example of handling specific items manually (if not handled by NavController)
        // Ensure these IDs correspond to menu items in your_navigation_menu.xml
        if (id == R.id.nav_logout) {
            // Handle Logout
            // mAuth.signOut(); // Example: Firebase sign out
            // Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            // startActivity(intent);
            // finish();
            Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer
            return true; // Return true to indicate item was handled
        } else if (id == R.id.nav_profile) {
            // If R.id.nav_profile is a destination in your nav graph,
            // NavigationUI.setupWithNavController should handle it.
            // If you want to do something else or it's not in the graph:
            Toast.makeText(this, "Profile Clicked (handled in onNavigationItemSelected)", Toast.LENGTH_SHORT).show();
            // Optionally, navigate if it's not part of the auto-setup:
            // navController.navigate(R.id.profileFragment); // Ensure profileFragment ID exists in nav graph
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        // IMPORTANT: If NavigationUI.setupWithNavController is used,
        // it usually handles navigation based on menu item IDs matching nav graph destination IDs.
        // For items handled by NavigationUI, you might not need to do anything here,
        // or you could just let it fall through.
        // Returning true marks the item as selected.

        // Default behavior: allow NavigationUI to handle the selection for navigation
        // boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
        // if (!handled) {
        // If NavigationUI didn't handle it, you can add custom logic here
        // }


        // For standard navigation items linked in setupWithNavController,
        // you often don't need to explicitly call navController.navigate() here
        // UNLESS you are overriding the default behavior or it's a special case.
        // The following 'if/else if' for nav_home_content, etc., might be redundant
        // if their IDs match destinations in your nav graph and are simple navigations.

        /*
        if (id == R.id.nav_home_content) {
            navController.navigate(R.id.homeContentFragment);
        } else if (id == R.id.nav_genre_search) {
            navController.navigate(R.id.genreSearchFragment);
        } else if (id == R.id.nav_mood_input) {
            navController.navigate(R.id.moodInputFragment);
        }
        */

        drawerLayout.closeDrawer(GravityCompat.START);
        // Return true to display the item as the selected item,
        // but let NavigationUI.onNavDestinationSelected do the actual navigation if possible.
        // If you manually navigated, return true. If NavigationUI handles it, its return value matters.
        // A common pattern is:
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
        // However, since we might have special items like logout, a more explicit structure:
        // (The one you had is fine if logout/profile are special)
    }
}

