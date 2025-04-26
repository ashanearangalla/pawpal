package com.example.petcare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.petcare.databinding.ActivityPetOwnerBottomNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PetOwnerBottomNavigationActivity extends AppCompatActivity {

    private ActivityPetOwnerBottomNavigationBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private Owner owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        owner = (Owner) getIntent().getSerializableExtra("owner");

        binding = ActivityPetOwnerBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Specify the top-level destinations in the AppBarConfiguration
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_owner, R.id.navigation_pet_info_pet_owner, R.id.navigation_accounts_owner)
                .build();

        // Initialize NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_pet_owner_dashboard);

        // Set up the ActionBar with NavController and AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up the BottomNavigationView with NavController
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_pet_owner_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}