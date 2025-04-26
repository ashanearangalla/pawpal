package com.example.petcare;
import java.io.Serializable;

public class Pet implements Serializable {
    private String name;
    private String type;
    private String age;
    private String color;
    private int id;
    private String breed;
    private String sex;
    private String note;

    private byte[] image;
    public Pet(int id,String name,String type,String age,String sex,String color,String breed,
               String note, byte[] image) {
        this.id =id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.sex = sex;
        this.color = color;
        this.breed = breed;
        this.note = note;
        this.image = image;
    }



    public void setPetName (String name) {
        this.name = name;

    }
    public void setPetType (String type) {
        this.type = type;

    }
    public void setPetAge (String age) {
        this.age = age;

    }
    public void setPetSex (String sex) {
        this.sex = sex;

    }
    public void setPetBreed (String breed) {
        this.breed = breed;

    }
    public void setPetColor (String color) {
        this.color = color;

    }
    public void setPetNote (String note) {
        this.note = note;

    }
    public void setPetImage ( byte[] image) {
        this.image = image;
    }

    public int getPetId() {
        return id;
    }
    public String getPetName() {
        return name;
    }
    public String getPetAge() {
        return age;
    }
    public String getPetType() {
        return type;
    }
    public String getPetBreed() {
        return breed;
    }
    public String getPetSex() {
        return sex;
    }
    public String getPetColor() {
        return color;
    }
    public String getPetNote() {
        return note;
    }

    public byte[] getPetImage() {
        return image;
    }
}