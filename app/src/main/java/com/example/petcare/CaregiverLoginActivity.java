package com.example.petcare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CaregiverLoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewError;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_login);

        dbHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmailCaregiver);
        editTextPassword = findViewById(R.id.editTextPasswordCaregiver);
        buttonLogin = findViewById(R.id.btnLoginCaregiver);
        textViewError = findViewById(R.id.textViewError);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    textViewError.setText("Please fill in all fields");
                } else {
                    Caregiver caregiver = checkUserCredentials(email, password, "caregivers");

                    if (caregiver != null) {


                        // Successful Login
                        Toast.makeText(CaregiverLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Pass the owner object to the next activity
                        Intent intent = new Intent(CaregiverLoginActivity.this, CaregiverBottomNavigationActivity.class);

                        intent.putExtra("caregiver", caregiver);

                        startActivity(intent);
                        finish();
                    } else {
                        textViewError.setText("Login failed. Invalid credentials.");
                    }
                }
            }
        });
    }

    private Caregiver checkUserCredentials(String email, String password, String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "email = ? AND password = ?";
        String[] selectionArgs =  {email, password};

        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // Retrieve owner details and create an Owner object
            int caregiverId = cursor.getInt(cursor.getColumnIndex("caregiver_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String contactNo = cursor.getString(cursor.getColumnIndex("contact_no"));
            String address = cursor.getString(cursor.getColumnIndex("address"));

            Caregiver caregiver = new Caregiver(caregiverId, name, email, contactNo, address);

            cursor.close();
            dbHelper.close();

            return caregiver;
        } else {
            cursor.close();
            dbHelper.close();

            return null;
        }
    }
    public void OnNavigateCaregiverSignup(View view) {
        Intent in= new Intent(getApplicationContext(), CaregiverSignupActivity.class);  // Use requireContext() instead of this
        startActivity(in);
    }


}