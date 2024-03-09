package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class PetOwnerSignupActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextContactNo;
    private EditText editTextAddress;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonNext;
    private TextView textViewError;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_signup);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextNameOwnerSignup);
        editTextEmail = findViewById(R.id.editTextEmailOwnerSignup);
        editTextContactNo = findViewById(R.id.editTextContactNoOwnerSignup);
        editTextAddress = findViewById(R.id.editTextAddressOwnerSignup);
        editTextPassword = findViewById(R.id.editTextPasswordOwnerSignup);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordOwnerSignup);
        buttonNext = findViewById(R.id.buttonNextOwnerRegister);
        textViewError = findViewById(R.id.textViewErrorOwnerSignup);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String contactNo = editTextContactNo.getText().toString();
                String address = editTextAddress.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || contactNo.isEmpty() || address.isEmpty()
                        || password.isEmpty() || confirmPassword.isEmpty()) {
                    textViewError.setText("Please fill in all the required fields!");
                } else if (!password.equals(confirmPassword)) {
                    textViewError.setText("Passwords do not match");
                } else if (!isValidEmail(email)) {
                    textViewError.setText("Email is not valid");
                } else {
                    // Data is valid, move to the next activity
                    Intent intent = new Intent(getApplicationContext(), PetOwnerSignupActivity2.class);

                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("contactNo", contactNo);
                    intent.putExtra("address", address);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });
    }

    // Function to validate email format using a regular expression
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";
        return Pattern.matches(emailPattern, email);
    }

    public void OnNavigateOwnerLogin(View view) {
        Intent in= new Intent(getApplicationContext(), PetOwnerLoginActivity.class);  // Use requireContext() instead of this
        startActivity(in);
    }
}