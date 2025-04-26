package com.example.petcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PawPalDB"; // Constant variable to store the database name
    private static final int DB_VERSION = 2; // Increment the version for database upgrade

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dropOwnersTableSQL= "DROP TABLE IF EXISTS owners";
        db.execSQL(dropOwnersTableSQL);

        // Table for owners
        String createOwnersTableSQL = "CREATE TABLE owners (owner_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, email TEXT, contact_no TEXT, address TEXT, password TEXT)";
        db.execSQL(createOwnersTableSQL);




        // Table for pets
        String createPetsTableSQL = "CREATE TABLE pets (pet_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "owner_id INTEGER, image BLOB, name TEXT, type TEXT, age INTEGER, breed TEXT, sex TEXT, color TEXT, note TEXT, " +
                "FOREIGN KEY (owner_id) REFERENCES owners(owner_id))";
        db.execSQL(createPetsTableSQL);

        // Table for caregivers
        String createCaregiversTableSQL = "CREATE TABLE caregivers (caregiver_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, email TEXT, contact_no TEXT,address TEXT, password TEXT)";
        db.execSQL(createCaregiversTableSQL);

        // Table for bookings
        String createBookingsTableSQL = "CREATE TABLE bookings (booking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "owner_id INTEGER, caregiver_id INTEGER, pet_id INTEGER, start_date TEXT, end_date TEXT, " +
                "town TEXT, status TEXT, price REAL, " +
                "FOREIGN KEY (owner_id) REFERENCES owners(owner_id), " +
                "FOREIGN KEY (caregiver_id) REFERENCES caregivers(caregiver_id), " +
                "FOREIGN KEY (pet_id) REFERENCES pets(pet_id))";
        db.execSQL(createBookingsTableSQL);

        // Table for reviews
        String createReviewsTableSQL = "CREATE TABLE reviews (review_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "owner_id INTEGER, caregiver_id INTEGER, booking_id INTEGER, " +
                "owner_rating INTEGER, caregiver_rating INTEGER, " +
                "owner_review TEXT, caregiver_review TEXT, " +
                "FOREIGN KEY (owner_id) REFERENCES owners(owner_id), " +
                "FOREIGN KEY (caregiver_id) REFERENCES caregivers(caregiver_id), " +
                "FOREIGN KEY (booking_id) REFERENCES bookings(booking_id))";
        db.execSQL(createReviewsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS owners");
        db.execSQL("DROP TABLE IF EXISTS pets");
        db.execSQL("DROP TABLE IF EXISTS caregivers");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("DROP TABLE IF EXISTS reviews");
        onCreate(db);
    }
}