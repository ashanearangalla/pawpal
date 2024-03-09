package com.example.petcare;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.petcare.databinding.ActivityCaregiverBottomNavigationBinding;

public class CaregiverBottomNavigationActivity extends AppCompatActivity {

    private ActivityCaregiverBottomNavigationBinding binding;
    private AppBarConfiguration appBarConfiguration;  // Move it here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCaregiverBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_caregiver,  R.id.navigation_accounts_caregiver)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_caregiver_dashboard);

        // Set up the ActionBar with NavController and AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up the BottomNavigationView with NavController
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_caregiver_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}