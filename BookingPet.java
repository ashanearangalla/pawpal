package com.example.petcare;
import java.io.Serializable;

public class BookingPet implements Serializable {
    private String name;
    private String type;
    private String age;
    private String color;
    private int id;
    private String breed;
    private String sex;
    private String note;
    private String startDate;
    private String endDate;
    private String town;
    private double price;
    private String ownerName;
    private String ownerContact;
    private String ownerAddress;

    private byte[] image;
    public BookingPet(int id,String name,String type,String age,String sex,String color,String breed,
               String note, byte[] image, String startDate, String endDate,String town,double price,
                      String ownerName, String ownerContact, String ownerAddress) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setAge(age);
        this.setSex(sex);
        this.setColor(color);
        this.setBreed(breed);
        this.setNote(note);
        this.setImage(image);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setTown(town);
        this.setPrice(price);
        this.setOwnerName(ownerName);
        this.setOwnerContact(ownerContact);
        this.setOwnerAddress(ownerAddress);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }
}