package com.example.petcare.ui.home;

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

import com.example.petcare.DatabaseHelper;
import com.example.petcare.Owner;
import com.example.petcare.Pet;
import com.example.petcare.R;
import com.example.petcare.databinding.FragmentHomeOwnerBinding;

public class HomeFragmentOwner extends Fragment {

    private FragmentHomeOwnerBinding binding;
    private Owner owner;
    private DatabaseHelper dbHelper;

    private Pet pet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbHelper = new DatabaseHelper(requireContext());

        // Retrieve the Owner object from the PetOwnerBottomNavigationActivity
        owner = (Owner) requireActivity().getIntent().getSerializableExtra("owner");

        pet = (Pet) requireActivity().getIntent().getSerializableExtra("pet");

        LinearLayout linearLayout = root.findViewById(R.id.linearlayoutpostpet);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPetPost(view);
            }
        });

        LinearLayout linearLayoutBookings = root.findViewById(R.id.linearlayoutviewbookingspet);


        linearLayoutBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPetViewBookings(view);
            }
        });


        return root;

    }


    public void onPetPost(View view) {
        Intent intent = new Intent(requireContext(), PostPet.class);  // Use requireContext() instead of this
        intent.putExtra("owner", owner);
        intent.putExtra("pet", pet);
        startActivity(intent);
    }
    public void onPetViewBookings(View view) {
        if (isPetBooked()) {
            Intent in = new Intent(requireContext(), ViewBookings.class);
            in.putExtra("owner", owner);
            in.putExtra("pet", pet);
            startActivity(in);

        } else {

            Toast.makeText(requireContext(), "No bookings yet for this pet.", Toast.LENGTH_SHORT).show();

        }
    }



    private boolean isPetBooked() {
        // Implement the logic to check the bookings table for the pet's status
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM bookings WHERE pet_id = ? AND status = 'Confirmed'",
                new String[]{String.valueOf(pet.getPetId())}
        );

        boolean isBooked = cursor.getCount() > 0;

        cursor.close();
        return isBooked;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}