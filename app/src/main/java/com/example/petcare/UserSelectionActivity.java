package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class UserSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        Button btnPetOwner = findViewById(R.id.btnPetOwner);
        Button btnCaregiver = findViewById(R.id.btnCaregiver);

        btnPetOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserSelectionActivity.this, PetOwnerLoginActivity.class));
            }
        });

        btnCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserSelectionActivity.this, CaregiverLoginActivity.class));
            }
        });
    }
}