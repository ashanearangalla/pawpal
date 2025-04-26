package com.example.petcare.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcare.DatabaseHelper;
import com.example.petcare.Owner;
import com.example.petcare.Pet;
import com.example.petcare.R;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostPet extends AppCompatActivity {

    private Pet pet;
    private DatabaseHelper dbHelper;

    ImageView imageViewPet;
    TextView textViewPetName, textViewError,priceTextView;
    EditText editTextStartDate, editTextEndDate, editTextTown;
    Button btnPost;
    private Owner owner;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pet);

        btnPost = findViewById(R.id.btnPostPet);
        imageViewPet = findViewById(R.id.imageViewPetPost);
        textViewPetName = findViewById(R.id.textViewPetNamePostPet);
        editTextStartDate = findViewById(R.id.editTextPostPetStartDate);
        editTextEndDate = findViewById(R.id.editTextPostPetEndDate);
        editTextTown = findViewById(R.id.editTextPostPetTown);
        textViewError = findViewById(R.id.textViewErrorPostPet);
        priceTextView = findViewById(R.id.priceTextView);
        dbHelper = new DatabaseHelper(this);

        owner = (Owner) getIntent().getSerializableExtra("owner");

        pet = (Pet) getIntent().getSerializableExtra("pet");

        if (pet != null) {
            // Update the UI with Owner details
            updateUI();
        }

        // Check if the pet is already booked
        if (isPetBooked()) {
            btnPost.setText("Cancel");
        }

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPetBooked()) {
                    // If pet is booked, cancel the booking
                    cancelBooking();
                    editTextStartDate.setText("");
                    editTextEndDate.setText("");
                    editTextTown.setText("");
                    btnPost.setText("Book"); // Change button text to "Book" after canceling
                } else {
                    // If pet is not booked, book the pet
                    String startDate = editTextStartDate.getText().toString();
                    String endDate = editTextEndDate.getText().toString();
                    String town = editTextTown.getText().toString();
                    int ownerId = Integer.parseInt(owner.getOwner_id());
                    int petId = pet.getPetId();
                    double price = Double.parseDouble(priceTextView.getText().toString());

                    String status = "Pending";

                    if (startDate.isEmpty() || endDate.isEmpty()) {
                        textViewError.setText("Please fill in all the required fields!");
                    } else {
                        postPet(ownerId, petId, startDate, endDate, town, price, status);
                        btnPost.setText("Cancel"); // Change button text to "Cancel" after booking
                    }
                }
            }
        });

        // Set up date pickers for StartDate and EndDate
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextStartDate);
            }
        });

        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextEndDate);
            }
        });
    }

    private void updateUI() {
        TextView petName = textViewPetName;
        petName.setText(pet.getPetName());

        byte[] image = pet.getPetImage();

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        ImageView petImage = imageViewPet;
        petImage.setImageBitmap(bmp);

        // Check if the pet is already booked
        if (isPetBooked()) {
            // If booked, retrieve and set the start date and end date in the EditText fields
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "SELECT start_date, end_date FROM bookings WHERE pet_id = ? AND status = 'Pending'",
                    new String[]{String.valueOf(pet.getPetId())}
            );

            if (cursor.moveToFirst()) {
                String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
                String endDate = cursor.getString(cursor.getColumnIndex("end_date"));
                String town = cursor.getString(cursor.getColumnIndex("town"));
                String price = cursor.getString(cursor.getColumnIndex("price"));

                editTextStartDate.setText(startDate);
                editTextEndDate.setText(endDate);
                editTextTown.setText(town);
                priceTextView.setText(price);
            }

            cursor.close();
        }
    }

    private boolean isPetBooked() {
        // Implement the logic to check the bookings table for the pet's status
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM bookings WHERE pet_id = ? AND status = 'Pending'",
                new String[]{String.valueOf(pet.getPetId())}
        );

        boolean isBooked = cursor.getCount() > 0;

        cursor.close();
        return isBooked;
    }

    private void postPet(int owner_id, int pet_id, String start_date, String end_date, String town, double price, String status) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("owner_id", owner_id);
        values.put("pet_id", pet_id);
        values.put("start_date", start_date);
        values.put("end_date", end_date);
        values.put("town", town);
        values.put("status", status);
        values.put("price", price);

        long newRowId = database.insert("bookings", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Booking Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Adding Booking Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelBooking() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "Canceled");

        int rowsAffected = database.update(
                "bookings",
                values,
                "pet_id = ? AND status = 'Pending'",
                new String[]{String.valueOf(pet.getPetId())}
        );

        if (rowsAffected > 0) {
            Toast.makeText(this, "Booking Canceled Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Canceling Booking", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog(final EditText editText) {
        // To show current date in the date picker
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(PostPet.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MM/yy"; // Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                editText.setText(sdf.format(myCalendar.getTime()));

                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
                updatePrice();
            }
        }, mYear, mMonth, mDay);

        // Show date picker dialog
        mDatePicker.show();
    }

    private void updatePrice() {
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);

                long daysDifference = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
                double total_price = daysDifference * 500.0; // Replace 500.0 with your actual price per day

                priceTextView.setText(""+total_price);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}