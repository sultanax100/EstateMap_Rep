package com.example.estatemap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private ArrayList<Property> propertyList;

    public PropertyAdapter(ArrayList<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName, propertyPrice, propertyType; // Added propertyType
        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
//            propertyName = itemView.findViewById(R.id.propertyName);
//            propertyPrice = itemView.findViewById(R.id.propertyPrice);
//            propertyType = itemView.findViewById(R.id.propertyType); // Initialize propertyType
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
        Property property = propertyList.get(position);
        holder.propertyName.setText(property.getName());
        holder.propertyPrice.setText(String.valueOf(property.getPrice()));
        holder.propertyType.setText(property.getType()); // Bind propertyType

    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
}