package com.example.petcare.ui.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petcare.Caregiver;
import com.example.petcare.databinding.FragmentAccountsCaregiverBinding;

public class AccountsFragmentCaregiver extends Fragment {

    private FragmentAccountsCaregiverBinding binding;
    private Caregiver caregiver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAccountsCaregiverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        caregiver = (Caregiver) requireActivity().getIntent().getSerializableExtra("caregiver");



        if (caregiver != null) {
            // Update the UI with Owner details
            updateUI();
        }



        return root;
    }

    private void updateUI() {
        EditText ownerName = binding.editTextCaregiverName;
        ownerName.setText(caregiver.getName());

        EditText ownerEmail = binding.editTextCaregiverEmail;
        ownerEmail.setText(caregiver.getEmail());

        EditText ownerContact = binding.editTextCaregiverContactNo;
        ownerContact.setText(caregiver.getContact_no());

        EditText ownerAddress = binding.editTextCaregiverAddress;
        ownerAddress.setText(caregiver.getAddress());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}