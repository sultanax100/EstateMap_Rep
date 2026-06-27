package com.example.estatemap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import android.widget.AdapterView;


public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";
    private PropertyAdapter1 propertyAdapter;
    private final List<Apartment> filteredPropertyList = new ArrayList<>();
    private RecyclerView recyclerViewSearchResults;
    private TextView emptyView;
    private Spinner filterSpinner;
    private String searchQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        emptyView = findViewById(R.id.emptyView);
        filterSpinner = findViewById(R.id.filterSpinner);

        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));
        propertyAdapter = new PropertyAdapter1(filteredPropertyList);
        recyclerViewSearchResults.setAdapter(propertyAdapter);

        // Get the search query from the Intent
        searchQuery = getIntent().getStringExtra("searchQuery");

        if (searchQuery == null || searchQuery.isEmpty()) {
            Log.w(TAG, "No search query received. Fetching all properties.");
            // Fetch all data from Firestore if no query is provided
            fetchFilteredDataFromFirestore();
        } else {
            Log.d(TAG, "Search query received: " + searchQuery);
            // Fetch filtered data based on the search query
            fetchFilteredDataFromFirestore(searchQuery);
        }


        // Set up the filter spinner for price and classification filtering
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedFilter = parentView.getItemAtPosition(position).toString();
                applyFilter(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void fetchFilteredDataFromFirestore(String propertyType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String formattedType = capitalizeFirstLetter(propertyType); // Capitalize input

        Log.d(TAG, "Searching for property type: " + formattedType); // Log formatted input

        db.collection("Apartment") // Assuming collection name is "Properties"
                .whereEqualTo("type", formattedType)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    filteredPropertyList.clear();
                    if (queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()) {
                        Log.d(TAG, "No matching documents found for type: " + formattedType);
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerViewSearchResults.setVisibility(View.GONE);
                        Toast.makeText(this, "No properties found for this type", Toast.LENGTH_SHORT).show();
                    } else {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Log.d(TAG, "Document ID: " + document.getId() + " | Type: " + document.getString("type"));
                            Double price = document.getDouble("price") != null ? document.getDouble("price") : 20.3;
                            String imageURL = document.getString("imageURL");
                            String Classification = document.getString("classification"); // "for sell" or "for rent"

                            if (imageURL != null && !imageURL.isEmpty()) {
                                Apartment property = new Apartment(price, imageURL,Classification );
                                filteredPropertyList.add(property);
                            } else {
                                Log.w(TAG, "Document with ID " + document.getId() + " is missing a valid imageURL.");
                            }
                        }

                        if (filteredPropertyList.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                            recyclerViewSearchResults.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            recyclerViewSearchResults.setVisibility(View.VISIBLE);
                        }

                        propertyAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error fetching data", e);
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });
    }
    private void fetchFilteredDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d(TAG, "Fetching all properties from the Apartment collection"); // Log the action

        db.collection("Apartment")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    filteredPropertyList.clear();
                    if (queryDocumentSnapshots == null || queryDocumentSnapshots.isEmpty()) {
                        Log.d(TAG, "No documents found in the Apartment collection.");
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerViewSearchResults.setVisibility(View.GONE);
                        Toast.makeText(this, "No properties found", Toast.LENGTH_SHORT).show();
                    } else {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Log.d(TAG, "Document ID: " + document.getId() + " | Type: " + document.getString("type"));
                            Double price = document.getDouble("price") != null ? document.getDouble("price") : 20.3;
                            String imageURL = document.getString("imageURL");
                            String classification = document.getString("classification"); // "for sell" or "for rent"

                            if (imageURL != null && !imageURL.isEmpty()) {
                                Apartment property = new Apartment(price, imageURL, classification);
                                filteredPropertyList.add(property);
                            } else {
                                Log.w(TAG, "Document with ID " + document.getId() + " is missing a valid imageURL.");
                            }
                        }

                        if (filteredPropertyList.isEmpty()) {
                            emptyView.setVisibility(View.VISIBLE);
                            recyclerViewSearchResults.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            recyclerViewSearchResults.setVisibility(View.VISIBLE);
                        }

                        propertyAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error fetching data", e);
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });
    }

    private void applyFilter(String selectedFilter) {
        if (selectedFilter.equals("For Sale")) {
            filteredPropertyList.removeIf(apartment -> !"for sell".equalsIgnoreCase(apartment.getClassification()));
        } else if (selectedFilter.equals("For Rent")) {
            filteredPropertyList.removeIf(apartment -> !"for rent".equalsIgnoreCase(apartment.getClassification()));
        } else if (selectedFilter.equals("Low to High")) {
            Collections.sort(filteredPropertyList, Comparator.comparingDouble(Apartment::getPrice));
        } else if (selectedFilter.equals("High to Low")) {
            Collections.sort(filteredPropertyList, (a1, a2) -> Double.compare(a2.getPrice(), a1.getPrice()));
        }

        propertyAdapter.notifyDataSetChanged();
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
