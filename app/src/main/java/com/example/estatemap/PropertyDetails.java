package com.example.estatemap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PropertyDetails extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView priceTextView;
    private TextView roomsTextView;
    private TextView locationTextView;
    private TextView areaTextView;
    private TextView ageTextView;
    private TextView typeTextView;
    private TextView classification;
    private ImageView propertyImageView;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        // Initialize TextViews
        propertyImageView = findViewById(R.id.image);
        priceTextView = findViewById(R.id.price);
        roomsTextView = findViewById(R.id.rooms);
        locationTextView = findViewById(R.id.location);
        areaTextView = findViewById(R.id.area);
        ageTextView = findViewById(R.id.age);
        typeTextView = findViewById(R.id.type);
        classification = findViewById(R.id.classification);


        // استرجاع imageURL من Intent
        String imageURL = getIntent().getStringExtra("imageURL");

        // استخدام imageURL لجلب بيانات العقار من Firestore
        if (imageURL != null) {
            db.collection("Apartment")
                    .whereEqualTo("imageURL", imageURL) // جلب البيانات بناءً على imageURL
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            Apartment apartment = document.toObject(Apartment.class);
                            if (apartment != null) {
                                displayPropertyDetails(apartment);
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("PropertyDetails", "Error fetching property details", e));
        }
    }

    private void displayPropertyDetails(Apartment apartment) {
        // Display image and other information
        Glide.with(this).load(apartment.getImageURL()).into(propertyImageView);
        priceTextView.setText("Price: " + apartment.getPrice() + " SAR");
        roomsTextView.setText("Rooms: " + apartment.getRooms());
        locationTextView.setText("Location: " + apartment.getLocation());
        areaTextView.setText("Area: " + apartment.getArea() + " m^2");
        ageTextView.setText("Age: " + apartment.getAge() + " years");
        typeTextView.setText("Type: " + apartment.getType());
        classification.setText("classification : " + apartment.getClassification());


        ImageButton favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AppointmentActivity
                Intent intent = new Intent(PropertyDetails.this, Appointment.class);
                startActivity(intent);
            }
        });
    }
}