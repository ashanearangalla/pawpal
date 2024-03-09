package com.example.petcare.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.petcare.OwnerViewModel;
import com.example.petcare.Pet;
import com.example.petcare.databinding.FragmentPetInfoOwnerBinding;

public class PetFragmentOwner extends Fragment {

    private @NonNull FragmentPetInfoOwnerBinding binding;
    private OwnerViewModel sharedViewModelOwner;
    private Pet pet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentPetInfoOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pet = (Pet) requireActivity().getIntent().getSerializableExtra("pet");

        if (pet != null) {
            // Update the UI with Owner details
            updateUI();
        }




        return root;
    }

    private void updateUI() {
        EditText petName = binding.editTextPetName;
        petName.setText(pet.getPetName());

        EditText petAge = binding.editTextAge;
        petAge.setText(pet.getPetAge());

        EditText petColor = binding.editTextColor;
        petColor.setText(pet.getPetColor());

        EditText petSex = binding.editTextSex;
        petSex.setText(pet.getPetSex());

        EditText petBreed = binding.editTextBreed;
        petBreed.setText(pet.getPetBreed());

        EditText petNote = binding.editTextSpecialNote;
        petNote.setText(pet.getPetNote());

        byte [] image = pet.getPetImage();

        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        ImageView petImage = binding.imageViewPet;
        petImage.setImageBitmap(bmp);






    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}