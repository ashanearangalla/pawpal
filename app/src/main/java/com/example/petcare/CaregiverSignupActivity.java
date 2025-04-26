package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CaregiverSignupActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextContactNo, editTextAddress, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_signup);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextNameCaregiverSignup);
        editTextEmail = findViewById(R.id.editTextEmailCaregiverSignup);
        editTextContactNo = findViewById(R.id.editTextContactNoCaregiverSignup);
        editTextAddress = findViewById(R.id.editTextAddressCaregiverSignup);
        editTextPassword = findViewById(R.id.editTextPasswordCaregiverSignup);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordCaregiverSignup);
        buttonRegister = findViewById(R.id.buttonCaregiverRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCaregiver();
            }
        });
    }

    private void registerCaregiver() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String contactNo = editTextContactNo.getText().toString();
        String address = editTextAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (validateInputs(name, email, contactNo, address, password, confirmPassword)) {
            insertCaregiverDetails(name, email, contactNo, address, password);

            // You can add further logic as needed

            // Clear the input fields after registration
            editTextName.setText("");
            editTextEmail.setText("");
            editTextContactNo.setText("");
            editTextAddress.setText("");
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
        }
    }

    private boolean validateInputs(String name, String email, String contactNo, String address, String password, String confirmPassword) {
        if (name.isEmpty() || email.isEmpty() || contactNo.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all the required fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void insertCaregiverDetails(String name, String email, String contactNo, String address, String password) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("contact_no", contactNo);
        values.put("address", address);
        values.put("password", password);

        long newRowId = database.insert("caregivers", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Caregiver Registration Successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Registering Caregiver", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnNavigateCaregiverLogin(View view) {
        Intent i= new Intent(getApplicationContext(), CaregiverLoginActivity.class);  // Use requireContext() instead of this
        startActivity(i);
    }
}