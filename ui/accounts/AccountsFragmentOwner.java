package com.example.petcare.ui.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petcare.Owner;
import com.example.petcare.databinding.FragmentAccountsOwnerBinding;

public class AccountsFragmentOwner extends Fragment {

    private FragmentAccountsOwnerBinding binding;
    private Owner owner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAccountsOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        owner = (Owner) requireActivity().getIntent().getSerializableExtra("owner");



            if (owner != null) {
                // Update the UI with Owner details
                updateUI();
            }



        return root;
    }

    private void updateUI() {
        EditText ownerName = binding.editTextOwnerName;
        ownerName.setText(owner.getName());

        EditText ownerEmail = binding.editTextOwnerEmail;
        ownerEmail.setText(owner.getEmail());

        EditText ownerContact = binding.editTextOwnerContactNo;
        ownerContact.setText(owner.getContact_no());

        EditText ownerAddress = binding.editTextOwnerAddress;
        ownerAddress.setText(owner.getAddress());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}