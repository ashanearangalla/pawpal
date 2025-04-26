package com.example.petcare;



import androidx.lifecycle.ViewModel;

public class OwnerViewModel extends ViewModel {

    private Owner owner;

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }


}