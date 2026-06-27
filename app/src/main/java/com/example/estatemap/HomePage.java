package com.example.estatemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private PropertyAdapter1 propertyAdapter;
    private final List<Apartment> propertyList = new ArrayList<>();
    private RecyclerView recyclerViewProperties;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Set the listener for navigation item selection
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.map) {
                Toast.makeText(HomePage.this, "Map clicked!", Toast.LENGTH_SHORT).show();
                Intent mapIntent = new Intent(HomePage.this, MapsActivity.class);
                startActivity(mapIntent);
                return true;
            } else if (id == R.id.save) {
                // Handle "save" action
                return true;
            } else if (id == R.id.profile) {
                // Handle "profile" action
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        });

        recyclerViewProperties = findViewById(R.id.recyclerViewProperties);
        recyclerViewProperties.setLayoutManager(new GridLayoutManager(this, 1));
        propertyAdapter = new PropertyAdapter1(propertyList);
        recyclerViewProperties.setAdapter(propertyAdapter);

        searchView = findViewById(R.id.searchView);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(HomePage.this, SignIn.class);
            Toast.makeText(this, "The error User is not logged ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "The User is logged ", Toast.LENGTH_SHORT).show();
            fetchDataFromFirestore();
        }

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(HomePage.this, SearchResultsActivity.class);
                intent.putExtra("searchQuery", query.trim().toLowerCase());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu for the navigation bar
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Apartment")
                .limit(6)
                .get()
                .addOnSuccessListener(this::onSuccess)
                .addOnFailureListener(e -> Log.w("HomePage", "Error fetching data", e));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        propertyList.clear();
        for (DocumentSnapshot document : queryDocumentSnapshots) {
            Double price = document.getDouble("price") != null ? document.getDouble("price") : 20.3;
            String imageURL = document.getString("imageURL");
            String classification = document.getString("classification");
            double rate = document.getDouble("rate") != null ? document.getDouble("rate") : 0.0;
            String location = document.getString("location");
            Apartment property = new Apartment(imageURL, price, location, rate, classification);
            propertyList.add(property);
        }
        propertyAdapter.notifyDataSetChanged();
    }

    public void SeeAll(View view) {
        Intent intent = new Intent(HomePage.this, AllProperties.class);
        startActivity(intent);
    }
}