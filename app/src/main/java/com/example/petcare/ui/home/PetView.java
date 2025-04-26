package com.example.petcare.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.BookingPet;
import com.example.petcare.Caregiver;
import com.example.petcare.DatabaseHelper;
import com.example.petcare.Pet;
import com.example.petcare.R;

public class PetView extends AppCompatActivity {

    private BookingPet pet;
    private Caregiver caregiver;
    private DatabaseHelper dbHelper;
    Button btnBookPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_view);

        btnBookPet = findViewById(R.id.buttonBookPet);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            pet = (BookingPet) getIntent().getSerializableExtra("pet");
            caregiver = (Caregiver) getIntent().getSerializableExtra("caregiver");

            int id = pet.getId();
            String name = pet.getName();
            String sex =pet.getSex();
            String age = pet.getAge();
            String color =pet.getColor();
            String breed= pet.getBreed();
            String town= pet.getTown();
            double price =pet.getPrice();
            String ownerName= pet.getOwnerName();
            String ownerContact= pet.getOwnerContact();
            String ownerAddress= pet.getOwnerAddress();

            byte[] image = pet.getImage();
            int caregiverId = caregiver.getCaregiver_id();

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            TextView textViewName = findViewById(R.id.textViewPetNameView); // Make sure this ID matches the one in activity_pet_view.xml
            ImageView imageViewPet = findViewById(R.id.imageViewPetView);
            TextView textViewAge = findViewById(R.id.textViewAgeView);
            TextView textViewColor = findViewById(R.id.textViewColorView);
            TextView textViewSex = findViewById(R.id.textViewSexView);
            TextView textViewBreed = findViewById(R.id.textViewBreedView);
            TextView textViewPrice = findViewById(R.id.textViewPriceView);
            TextView textViewTown = findViewById(R.id.textViewTownView);
            TextView textViewOwnerName = findViewById(R.id.textViewOwnerNameView);
            TextView textViewOwnerContact = findViewById(R.id.textViewOwnerContactView);
            TextView textViewOwnerAddress = findViewById(R.id.textViewOwnerAddressView);

            getStartEndDates(id);

            imageViewPet.setImageBitmap(bmp);
            textViewName.setText(name);
            textViewAge.setText(age);
            textViewColor.setText(color);
            textViewSex.setText(sex);
            textViewBreed.setText(breed);
            textViewTown.setText(town);
            textViewPrice.setText(""+price);
            textViewOwnerName.setText(ownerName);
            textViewOwnerContact.setText(ownerContact);
            textViewOwnerAddress.setText(ownerAddress);

            // Now you can use the 'owner' data as needed

        }

        btnBookPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBookingStatus(pet.getId(),caregiver.getCaregiver_id());

                Intent intent = new Intent(getApplicationContext(), BookedPets.class);
                intent.putExtra("caregiver", caregiver);
                startActivity(intent);




            }
        });
    }



    private void getStartEndDates(int petId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT start_date, end_date FROM bookings WHERE pet_id = ? AND status = 'Pending'",
                new String[]{String.valueOf(petId)}
        );

        TextView textViewStartDate = findViewById(R.id.textViewStartView);
        TextView textViewEndDate = findViewById(R.id.textViewEndView);

        if (cursor.moveToFirst()) {
            String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndex("end_date"));

            textViewStartDate.setText(startDate);
            textViewEndDate.setText(endDate);
        }

        cursor.close();
    }

    private void updateBookingStatus(int petId, int caregiverId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Create ContentValues to update the status to "Confirmed"
        ContentValues values = new ContentValues();
        values.put("status", "Confirmed");
        values.put("caregiver_id", caregiverId);

        // Update the status in the bookings table for the relevant pet ID
        int rowsAffected = database.update(
                "bookings",
                values,
                "pet_id = ? AND status = 'Pending'",
                new String[]{String.valueOf(petId)}
        );

        // Check if the update was successful
        if (rowsAffected > 0) {
            Toast.makeText(PetView.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PetView.this, "Booking cannot be Added!", Toast.LENGTH_SHORT).show();
        }
    }
}