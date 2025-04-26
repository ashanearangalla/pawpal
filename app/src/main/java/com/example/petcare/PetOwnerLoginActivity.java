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

import java.util.regex.Pattern;

public class PetOwnerLoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewError;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_login);

        dbHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmailOwnerLogin);
        editTextPassword = findViewById(R.id.editTextPasswordOwnerLogin);
        buttonLogin = findViewById(R.id.btnLoginOwner);
        textViewError = findViewById(R.id.textViewErrorOwnerLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    textViewError.setText("Please fill in all fields");
                }else if (!isValidEmail(email)) {
                    textViewError.setText("Email is not valid");
                }  else {
                    Owner owner = checkUserCredentials(email, password, "owners");

                    if (owner != null) {

                        Pet pet = getOwnerPet(owner.getOwner_id());

                        // Successful Login
                        Toast.makeText(PetOwnerLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Pass the owner object to the next activity
                        Intent intent = new Intent(PetOwnerLoginActivity.this, PetOwnerBottomNavigationActivity.class);

                        intent.putExtra("owner", owner);
                        intent.putExtra("pet", pet);
                        int id =pet.getPetId();
                        startActivity(intent);
                        finish();
                    } else {
                        textViewError.setText("Login failed. Invalid credentials.");
                    }
                }
            }
        });
    }

    private Owner checkUserCredentials(String email, String password, String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "email = ? AND password = ?";
        String[] selectionArgs =  {email, password};

        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // Retrieve owner details and create an Owner object
            String ownerId = cursor.getString(cursor.getColumnIndex("owner_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String contactNo = cursor.getString(cursor.getColumnIndex("contact_no"));
            String address = cursor.getString(cursor.getColumnIndex("address"));

            Owner owner = new Owner(ownerId, name, email, contactNo, address);

            cursor.close();
            dbHelper.close();

            return owner;
        } else {
            cursor.close();
            dbHelper.close();

            return null;
        }
    }

    private Pet getOwnerPet(String ownerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "owner_id = ?";
        String[] selectionArgs = {ownerId};

        Cursor cursor = db.query("pets", null, selection, selectionArgs, null, null, null);

        Pet ownerPet = null;

        if (cursor.moveToFirst()) {
            // Retrieve pet details and create a Pet object
            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pet_id")));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            String breed = cursor.getString(cursor.getColumnIndex("breed"));
            String note = cursor.getString(cursor.getColumnIndex("note"));

            Pet pet = new Pet(id,name, type, age, sex, color, breed, note, image);

            return pet;
        }
        else {
            cursor.close();
            dbHelper.close();

            return null;
        }



    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";
        return Pattern.matches(emailPattern, email);
    }
    public void OnNavigateOwnerSignup(View view) {
        Intent i= new Intent(getApplicationContext(), PetOwnerSignupActivity.class);  // Use requireContext() instead of this
        startActivity(i);
    }

}