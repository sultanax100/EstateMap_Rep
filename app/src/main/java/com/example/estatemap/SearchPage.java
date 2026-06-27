package com.example.estatemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchPage extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<Property> propertyList = new ArrayList<>();      // Original list from Firebase
    private ArrayList<Property> filteredList = new ArrayList<>();      // Filtered list for search and filters
    private PropertyAdapter propertyAdapter;
    private EditText searchEditText;
    private Spinner filterSpinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("properties");

        // Set up RecyclerView with horizontal orientation
        RecyclerView recyclerView = findViewById(R.id.propertyRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Set up adapter with filteredList
        propertyAdapter = new PropertyAdapter(filteredList);
        recyclerView.setAdapter(propertyAdapter);

        // Initialize search bar and spinner
        searchEditText = findViewById(R.id.searchEditText);
        filterSpinner = findViewById(R.id.filterSpinner);

        // Set up filter dropdown (spinner)
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filter_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        // Fetch data from Firebase
        fetchDataFromFirebase();

        // Set up search functionality
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchEditText.getText().toString().trim();
            filterByName(query);
            return true;
        });

        // Set up filter functionality
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString();
                applyFilter(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle no selection
            }
        });
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                propertyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Property property = snapshot.getValue(Property.class);
                    propertyList.add(property);
                }

                // Initially, set filteredList to show all properties
                updateList(propertyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });
    }

    // Method to update the filtered list and refresh RecyclerView
    private void updateList(ArrayList<Property> newList) {
        filteredList.clear();
        filteredList.addAll(newList);
        propertyAdapter.notifyDataSetChanged();
    }

    // Method to filter properties by name
    private void filterByName(String query) {
        ArrayList<Property> tempList = new ArrayList<>();
        for (Property property : propertyList) {
            if (property.getName().toLowerCase().contains(query.toLowerCase())) {
                tempList.add(property);
            }
        }
        updateList(tempList);
    }

    // Method to apply sorting or type filters
    private void applyFilter(String selectedFilter) {
        ArrayList<Property> tempList = new ArrayList<>(propertyList);

        switch (selectedFilter) {
            case "Low-High Price":
                Collections.sort(tempList, Comparator.comparingDouble(Property::getPrice));
                break;

            case "High-Low Price":
                Collections.sort(tempList, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;

            case "Apartment":
                tempList.removeIf(property -> !property.getType().equalsIgnoreCase("Apartment"));
                break;

            case "Villa":
                tempList.removeIf(property -> !property.getType().equalsIgnoreCase("Villa"));
                break;

            default:
                // Default to showing all properties if no filter is selected
                tempList = new ArrayList<>(propertyList);
                break;
        }
        updateList(tempList);
    }
}
