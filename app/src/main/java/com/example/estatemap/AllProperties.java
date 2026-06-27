package com.example.estatemap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllProperties extends AppCompatActivity {

    private PropertyAdapter1 propertyAdapter;
    private final List<Apartment> propertyList = new ArrayList<>();
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_properties);

        //recyclerViewProperties = findViewById(R.id.recyclerView);
        recyclerView=findViewById(R.id.recyclerView);
        //recyclerViewProperties.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        propertyAdapter = new PropertyAdapter1(propertyList);
        recyclerView.setAdapter(propertyAdapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(AllProperties.this, SignIn.class);
            Toast.makeText(this, "The error User is not logged ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "The User is logged ", Toast.LENGTH_SHORT).show();
            fetchDataFromFirestore();
        }
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Apartment")
                .limit(9)
                .get()
                .addOnSuccessListener(this::onSuccess)
                .addOnFailureListener(e -> Log.w("HomePage", "Error fetching data", e));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        propertyList.clear();
        for (DocumentSnapshot document : queryDocumentSnapshots) {
            String imageURL = document.getString("imageURL");
            double price = document.getDouble("price") != null ? document.getDouble("price") : 0.0;
            double rate = document.getDouble("rate") != null ? document.getDouble("rate") : 0.0; // Add rate
            String location = document.getString("location");

            Apartment property = new Apartment(imageURL, price, location, rate);// Pass rate to constructor
            propertyList.add(property);
        }
        propertyAdapter.notifyDataSetChanged();
    }
}