package com.example.petcare.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.petcare.DatabaseHelper;
import com.example.petcare.Pet;
import com.example.petcare.R;

public class ViewBookings extends AppCompatActivity {

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        dbHelper = new DatabaseHelper(this);

        Pet pet = (Pet) getIntent().getSerializableExtra("pet");

        int petId=pet.getPetId();

        setTextViews(petId);

    }

    private void setTextViews(int petId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // Query to retrieve caregiver details based on the given petId and status 'Confirmed'
        String query = "SELECT * " +
                "FROM caregivers c " +
                "JOIN bookings b ON c.caregiver_id = b.caregiver_id " +
                "WHERE b.pet_id = ? AND b.status = 'Confirmed'";


            // Execute the query
            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(petId)});

            // Set text for TextViews
            TextView textViewCaregiverName = findViewById(R.id.textViewCaregiverNameViewBookings);
            TextView textViewCaregiverEmail = findViewById(R.id.textViewCaregiverEmailViewBooking);
            TextView textViewCaregiverContact = findViewById(R.id.textViewCaregiverContactNoViewBookings);
            TextView textViewCaregiverAddress = findViewById(R.id.textViewCaregiverAddressViewbooking);

            if (cursor.moveToFirst()) {


                String name= cursor.getString(cursor.getColumnIndex("name"));
                String email=cursor.getString(cursor.getColumnIndex("email"));
                String contact=cursor.getString(cursor.getColumnIndex("contact_no"));
                String address=cursor.getString(cursor.getColumnIndex("address"));


                textViewCaregiverName.setText(name);
                textViewCaregiverEmail.setText(email);
                textViewCaregiverContact.setText(contact);
                textViewCaregiverAddress.setText(address);
            }


            cursor.close();

    }
}