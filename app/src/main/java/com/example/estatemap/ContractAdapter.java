package com.example.estatemap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ContractAdapter {
    private DatabaseReference databaseReference;

    public ContractAdapter() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("contracts");
    }

    public void fetchPropertyData(String propertyId, Activity activity) {
        databaseReference.child(propertyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Contract contract = snapshot.getValue(Contract.class);

                    if (contract != null) {
                        // Populate Contract Information
                        ((EditText) activity.findViewById(R.id.contractTypeEditText)).setText(contract.getContractType());
                        ((EditText) activity.findViewById(R.id.contractRegistryNumberEditText)).setText(contract.getContractRegistryNumber());
                        ((EditText) activity.findViewById(R.id.rentStartDateEditText)).setText(contract.getRentStartDate());
                        ((EditText) activity.findViewById(R.id.rentEndDateEditText)).setText(contract.getRentEndDate());

                        // Populate Landlord Information
                        ((EditText) activity.findViewById(R.id.landlordNameEditText)).setText(contract.getOwnerName());
                        ((EditText) activity.findViewById(R.id.landlordMobileEditText)).setText(contract.getMobileNumber());
                        ((EditText) activity.findViewById(R.id.landlordIDEditText)).setText(contract.getIdNumber());
                        ((EditText) activity.findViewById(R.id.landlordAddressEditText)).setText(contract.getNationality());

                        // Populate Property Information
                        ((EditText) activity.findViewById(R.id.propertyNumberEditText)).setText(String.valueOf(contract.getArea()));
                        ((EditText) activity.findViewById(R.id.propertyTypeEditText)).setText(contract.getType());
                        ((EditText) activity.findViewById(R.id.landAreaEditText)).setText(contract.getLocation());
                    }
                } else {
                    Toast.makeText(activity, "No data found for this property", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
