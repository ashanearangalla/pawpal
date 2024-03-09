package com.example.petcare;

import java.io.Serializable;

public class Caregiver implements Serializable {

    private String name;
    private String email;
    private String contact_no;
    private String address;
    private int caregiver_id;

    public Caregiver(int caregiver_id, String name, String email, String contact_no, String address) {
        this.caregiver_id = caregiver_id;
        this.name = name;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCaregiver_id() {
        return caregiver_id;
    }

    public void setCaregiver_id(int caregiver_id) {
        this.caregiver_id = caregiver_id;
    }
}