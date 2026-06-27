package com.example.estatemap;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PropertyAdapter1 extends RecyclerView.Adapter<PropertyAdapter1.PropertyViewHolder> {
    private List<Apartment> propertyList;


    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyImage;
        TextView propertyPrice, propertyRate, propertyLocation;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyImage = itemView.findViewById(R.id.propertyImage);
            propertyPrice = itemView.findViewById(R.id.propertyPrice);
            propertyRate = itemView.findViewById(R.id.propertyRate);
            propertyLocation = itemView.findViewById(R.id.propertyLocation);
        }
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Apartment property = propertyList.get(position);

        String picUrl = property.getImageURL();
        if (picUrl != null && !picUrl.isEmpty()) {
            // Load image with Glide
            Glide.with(holder.itemView.getContext())
                    .load(picUrl)
                    .into(holder.propertyImage);
        } else {
            // Log a warning if the URL is null
            Log.w("PropertyAdapter1", "Image URL is null or empty for property: " + property.getPrice());
            // Optionally set a placeholder or error image
            holder.propertyImage.setImageResource(R.drawable.house4); // Add a default image
        }

        // Set the price, rate, and location
        holder.propertyPrice.setText(property.getPrice() + " SAR");
//        holder.propertyRate.setText("Rate: " + property.getRate());
        holder.propertyLocation.setText(property.getLocation());

        // Set OnClickListener to open a new Activity on click
//    holder.propertyImage.setOnClickListener(new View.OnClickListener()
//    {
//        @Override
//        public void onClick (View v){
//        int currentPosition = holder.getAdapterPosition();
//        if (currentPosition != RecyclerView.NO_POSITION) {
//            Apartment currentProperty = propertyList.get(currentPosition);
//            Intent intent = new Intent(holder.itemView.getContext(), PropertyDetails.class); // Replace with your target activity
//            intent.putExtra("imageURL", currentProperty.getImageURL()); // Assume getId() gives property ID or unique identifier
//            holder.itemView.getContext().startActivity(intent);
//        }
//    }
//    });
        holder.propertyImage.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Apartment currentProperty = propertyList.get(currentPosition);

                // إرسال رابط الصورة فقط
                Intent intent = new Intent(holder.itemView.getContext(), PropertyDetails.class);
                intent.putExtra("imageURL", currentProperty.getImageURL());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }
        @Override
        public int getItemCount () {
            return propertyList.size();
        }
    }


