package com.example.petcare.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petcare.BookingPet;
import com.example.petcare.Caregiver;
import com.example.petcare.Pet;
import com.example.petcare.R;

import java.util.List;
public class PetListAdapter extends ArrayAdapter<BookingPet> {

    private final int resource;
    private final Caregiver caregiver;

    public PetListAdapter(Context context, int resource, List<BookingPet> objects, Caregiver caregiver) {
        super(context, resource, objects);
        this.resource = resource;
        this.caregiver = caregiver;
    }

    static class ViewHolder {
        TextView textViewPetName;
        TextView textViewStartDate;
        TextView textViewEndDate;
        TextView textViewTown;
        TextView textViewPrice;
        ImageView imageViewPet;


        Button buttonViewPet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewPetName = convertView.findViewById(R.id.textViewPetName);
            viewHolder.textViewStartDate = convertView.findViewById(R.id.textViewStartDate);
            viewHolder.textViewEndDate = convertView.findViewById(R.id.textViewEndDate);
            viewHolder.textViewTown = convertView.findViewById(R.id.textViewTown);
            viewHolder.textViewPrice = convertView.findViewById(R.id.textViewPrice);
            viewHolder.imageViewPet = convertView.findViewById(R.id.imageViewPetList);
            viewHolder.buttonViewPet = convertView.findViewById(R.id.buttonViewPet);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BookingPet pet = getItem(position);


        if (pet != null) {
            viewHolder.textViewPetName.setText(pet.getName());
            viewHolder.textViewStartDate.setText("From: " + pet.getStartDate());
            viewHolder.textViewEndDate.setText("To: " + pet.getEndDate());
            viewHolder.textViewTown.setText( pet.getTown());
            viewHolder.textViewPrice.setText("Rs " + pet.getPrice());

            if (pet.getImage() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(pet.getImage(), 0, pet.getImage().length);
                viewHolder.imageViewPet.setImageBitmap(bmp);
            }

            viewHolder.buttonViewPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Handle "View" button click
                    Intent petViewIntent = new Intent(getContext(), PetView.class);
                    petViewIntent.putExtra("pet", pet);
                    petViewIntent.putExtra("caregiver", caregiver);
                    getContext().startActivity(petViewIntent);
                }
            });
        }

        return convertView;
    }
}