package com.example.petcare.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.petcare.BookingPet;
import com.example.petcare.Caregiver;
import com.example.petcare.DatabaseHelper;
import com.example.petcare.Owner;
import com.example.petcare.Pet;
import com.example.petcare.R;
import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listviewTasks;
    private Caregiver caregiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        caregiver = (Caregiver) getIntent().getSerializableExtra("caregiver");

        listviewTasks = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        displayPetList("");

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displayPetList(newText);
                return true;
            }
        });

        listviewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookingPet selectedPet = (BookingPet) listviewTasks.getItemAtPosition(position);
                Caregiver caregiver = (Caregiver) getIntent().getSerializableExtra("caregiver");

                Intent petViewIntent = new Intent(PetListActivity.this, PetView.class);

                petViewIntent.putExtra("pet", selectedPet);
                petViewIntent.putExtra("caregiver", caregiver);

                startActivity(petViewIntent);
            }
        });
    }

    private void displayPetList(String searchTerm) {
        ArrayList<BookingPet> petList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT pets.pet_id, pets.name AS pet_name, pets.type, pets.age, pets.sex, pets.color, pets.breed, pets.note, pets.image, bookings.start_date, bookings.end_date, bookings.town, bookings.price, owners.name AS owner_name, owners.contact_no, owners.address " +
                "FROM pets " +
                "INNER JOIN bookings ON pets.pet_id = bookings.pet_id " +
                "INNER JOIN owners ON pets.owner_id = owners.owner_id " +
                "WHERE bookings.status = 'Pending' " +
                "AND (pets.type LIKE ? OR pets.breed LIKE ? OR bookings.town LIKE ? OR pets.name LIKE ?)";

        String[] selectionArgs = {"%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%"};

        Cursor cursor = database.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pet_id")));
            String name = cursor.getString(cursor.getColumnIndex("pet_name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String color = cursor.getString(cursor.getColumnIndex("color"));
            String breed = cursor.getString(cursor.getColumnIndex("breed"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            String startDate = cursor.getString(cursor.getColumnIndex("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndex("end_date"));
            String town = cursor.getString(cursor.getColumnIndex("town"));
            String priceBooking = cursor.getString(cursor.getColumnIndex("price"));
            double price = Double.parseDouble(priceBooking);
            String ownerName = cursor.getString(cursor.getColumnIndex("owner_name"));
            String ownerContact = cursor.getString(cursor.getColumnIndex("contact_no"));
            String ownerAddress = cursor.getString(cursor.getColumnIndex("address"));

            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

            petList.add(new BookingPet(id, name, type, age, sex, color, breed, note, image, startDate, endDate, town,price,ownerName,ownerContact,ownerAddress));
        }

        cursor.close();

        PetListAdapter adapter = new PetListAdapter(this, R.layout.activity_list_view, petList,caregiver);
        listviewTasks.setAdapter(adapter);
    }
}