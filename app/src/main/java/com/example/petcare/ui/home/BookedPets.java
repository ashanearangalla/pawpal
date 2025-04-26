package com.example.petcare.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petcare.Caregiver;
import com.example.petcare.DatabaseHelper;
import com.example.petcare.R;

public class BookedPets extends AppCompatActivity {
    private Caregiver caregiver;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_pets);

        dbHelper = new DatabaseHelper(this);

        caregiver = (Caregiver) getIntent().getSerializableExtra("caregiver");

        // Set text for TextViews
        setTextViews();
    }

    private void setTextViews() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(
                "SELECT p.name AS pet_name, start_date, end_date,price,town, sex, age, color, breed, note, image, o.name AS owner_name,contact_no,address " +
                        "FROM pets p " +
                        "INNER JOIN bookings b ON p.pet_id = b.pet_id " +
                        "INNER JOIN owners o ON p.owner_id = o.owner_id " +
                        "WHERE b.caregiver_id = ? AND b.status = 'Confirmed'",
                new String[]{String.valueOf(caregiver.getCaregiver_id())}
        );

        TextView textViewName = findViewById(R.id.textViewPetNameBooked);
        ImageView imageViewPet = findViewById(R.id.imageViewPetBooked);
        TextView textViewAge = findViewById(R.id.textViewAgeBooked);
        TextView textViewColor = findViewById(R.id.textViewColorBooked);
        TextView textViewSex = findViewById(R.id.textViewSexBooked);
        TextView textViewBreed = findViewById(R.id.textViewBreedBooked);
        TextView textViewPrice = findViewById(R.id.textViewPriceBooked);
        TextView textViewTown = findViewById(R.id.textViewTownBooked);
        TextView textViewStartDate = findViewById(R.id.textViewStartBooked);
        TextView textViewEndDate = findViewById(R.id.textViewEndBooked);
        TextView textViewOwnerName= findViewById(R.id.textViewOwnerNameBooked);
        TextView textViewOwnerContact = findViewById(R.id.textViewOwnerContactBooked);
        TextView textViewOwnerAddress = findViewById(R.id.textViewOwnerAddressBooked);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("pet_name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            String breed = cursor.getString(cursor.getColumnIndex("breed"));

            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndex("end_date"));
            String town = cursor.getString(cursor.getColumnIndex("town"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String ownerName = cursor.getString(cursor.getColumnIndex("owner_name"));
            String ownerContact = cursor.getString(cursor.getColumnIndex("contact_no"));
            String ownerAddress = cursor.getString(cursor.getColumnIndex("address"));


            textViewName.setText(name);
            textViewSex.setText(sex);
            textViewAge.setText(age);
            textViewColor.setText(color);
            textViewBreed.setText(breed);
            textViewOwnerName.setText(ownerName);
            textViewOwnerContact.setText(ownerContact);
            textViewOwnerAddress.setText(ownerAddress);
            textViewPrice.setText(price);
            textViewTown.setText(town);




            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageViewPet.setImageBitmap(bmp);

            textViewStartDate.setText(startDate);
            textViewEndDate.setText(endDate);
        }

        cursor.close();
    }
}