package com.example.petcare;

import java.io.Serializable;

public class Owner implements Serializable {

    private String name;
    private String email;
    private String contact_no;
    private String address;
    private String owner_id;

    public Owner(String owner_id, String name, String email, String contact_no, String address) {
        this.owner_id = owner_id;
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

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}