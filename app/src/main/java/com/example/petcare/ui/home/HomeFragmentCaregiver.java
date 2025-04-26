package com.example.petcare.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petcare.Caregiver;
import com.example.petcare.DatabaseHelper;
import com.example.petcare.R;

public class HomeFragmentCaregiver extends Fragment {

    private Caregiver caregiver;

    private DatabaseHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_caregiver, container, false);


        caregiver = (Caregiver) requireActivity().getIntent().getSerializableExtra("caregiver");
        dbHelper = new DatabaseHelper(requireContext());

        // Display the owner's name in a TextView
        LinearLayout linearLayout = root.findViewById(R.id.linearlayoutfindpets);

        // Set OnClickListener on the LinearLayout
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFindPets(view);
            }
        });

        LinearLayout linearLayoutBookedPets = root.findViewById(R.id.linearlayoutbookedpets);

        // Set OnClickListener on the LinearLayout
        linearLayoutBookedPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookedPets(view);
            }
        });

        return root;
    }

    public void onFindPets(View view) {
        if (isBookingConfirmed()) {
            Toast.makeText(requireContext(), "You cannot book more than one pet once", Toast.LENGTH_SHORT).show();

        }else {
            Intent intent = new Intent(requireContext(), PetListActivity.class);
            intent.putExtra("caregiver", caregiver);
            startActivity(intent);

        }

    }

    public void onBookedPets(View view) {
        if (isBookingConfirmed()) {
            Intent intent = new Intent(requireContext(), BookedPets.class);
            intent.putExtra("caregiver", caregiver);
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), "No Booked Pets to show", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isBookingConfirmed() {
        // Implement the logic to check if the booking status is confirmed
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM bookings WHERE caregiver_id = ? AND status = 'Confirmed'",
                new String[]{String.valueOf(caregiver.getCaregiver_id())}
        );

        boolean isConfirmed = cursor.getCount() > 0;

        cursor.close();
        return isConfirmed;
    }

}