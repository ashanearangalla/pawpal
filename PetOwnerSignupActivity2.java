package com.example.petcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PetOwnerSignupActivity2 extends AppCompatActivity {

    private EditText editTextPetName, editTextAge, editTextColor, editTextSex, editTextBreed, editTextSpecialNote;
    private Button buttonUploadPic, buttonNext;
    private ImageView imageView;
    private RadioGroup petTypeRadioGroup;
    private RadioButton selectedType;
    private DatabaseHelper dbHelper;
    private TextView textViewError;

    Uri selectedImageURI;
    byte[] imageBytes;

    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_signup2);

        dbHelper = new DatabaseHelper(this);

        editTextPetName = findViewById(R.id.editTextPetNameSignup);
        petTypeRadioGroup = findViewById(R.id.radioGroupPetType);
        editTextAge = findViewById(R.id.editTextPetAgeSignup);
        editTextColor = findViewById(R.id.editTextPetColorSignup);
        editTextSex = findViewById(R.id.editTextPetSexSignup);
        editTextBreed = findViewById(R.id.editTextPetBreedSignup);
        editTextSpecialNote =  findViewById(R.id.editTextPetNoteSignup);
        buttonUploadPic = findViewById(R.id.btnUploadPetImage);
        buttonNext = findViewById(R.id.buttonSignupOwnerRegister);
        imageView = findViewById(R.id.imageViewPet);
        textViewError = findViewById(R.id.textViewErrorOwnerPetSignup);

        buttonUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchImage();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String petName = editTextPetName.getText().toString();
                String type;
                int selectedRadioButtonId = petTypeRadioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedType = findViewById(selectedRadioButtonId);
                    type = selectedType.getText().toString();
                }else {

                    type=null;
                }


                String age = editTextAge.getText().toString();
                String color = editTextColor.getText().toString();
                String sex = editTextSex.getText().toString();
                String breed = editTextBreed.getText().toString();
                String note = editTextSpecialNote.getText().toString();

                if (validateInputs(petName, age, color, sex, breed,type,imageBytes)) {



                    // Assuming these values are passed from PetOwnerSignupActivity
                    String ownerName = getIntent().getStringExtra("name");
                    String ownerEmail = getIntent().getStringExtra("email");
                    String ownerContactNo = getIntent().getStringExtra("contactNo");
                    String ownerAddress = getIntent().getStringExtra("address");
                    String ownerPassword = getIntent().getStringExtra("password");

                    long ownerId = insertOwnerDetails(ownerName, ownerEmail, ownerContactNo, ownerAddress, ownerPassword);


                    if (ownerId != -1) {
                        // Owner details added successfully, now insert pet details
                        insertPetDetails(petName,type, age, color, sex, breed,note, imageBytes, ownerId);

                        // Now you have both pet details and owner details, you can use them as needed
                        Intent intent = new Intent(getApplicationContext(),PetOwnerLoginActivity.class);
                        startActivity(intent);
                        editTextPetName.setText("");
                        editTextAge.setText("");
                        editTextColor.setText("");
                        editTextSex.setText("");
                        editTextBreed.setText("");
                    }
                }else {
                    textViewError.setText("Please fill in all the required fields!");
                }
            }
        });
    }

    public void fetchImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            selectedImageURI = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageURI);
                imageBytes = getBytesFromInputStream(inputStream);
                imageView.setImageURI(selectedImageURI);
                Toast.makeText(this, "Pet Image Fetched", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error fetching pet image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    private void insertPetDetails(String petName,String type, String age, String color, String sex, String breed,String note, byte[] imageBytes, long ownerId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", petName);
        values.put("type", type);
        values.put("age", age);
        values.put("color", color);
        values.put("sex", sex);
        values.put("breed", breed);
        values.put("note", note);
        values.put("image", imageBytes);
        values.put("owner_id", ownerId); // Include the owner ID in the pet details

        long newRowId = database.insert("pets", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Pet Details Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Adding Pet Details", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertOwnerDetails(String ownerName, String ownerEmail, String ownerContactNo, String ownerAddress, String ownerPassword) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", ownerName);
        values.put("email", ownerEmail);
        values.put("contact_no", ownerContactNo);
        values.put("address", ownerAddress);
        values.put("password", ownerPassword);

        long newRowId = database.insert("owners", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Owner Details Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Adding Owner Details", Toast.LENGTH_SHORT).show();
        }
        return newRowId;
    }

    private boolean validateInputs(String petName, String age, String color, String sex, String breed, String type,byte[] imageBytes ) {
        if (petName.isEmpty() || age.isEmpty() || sex.isEmpty() || breed.isEmpty()
                || type.isEmpty() || imageBytes.toString().isEmpty()) {
            textViewError.setText("Please fill in all the required fields!");
            return false;
        }  else {
            return true;
        }



    }


}