package com.example.estatemap;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

import com.example.estatemap.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseFirestore db;
    private static final LatLng JEDDAH_ALBASATEEN = new LatLng(21.6222, 39.1568); // Fixed location coordinates for Jeddah Albasateen
    private static final int RADIUS = 50000; // Radius in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding and Firestore instance
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        // Initialize the map fragment and set the map ready callback
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("MapsActivity", "Map fragment is null.");
            Toast.makeText(this, "Map fragment initialization failed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapLocation();

        // Fetch properties near Jeddah Albasateen
        fetchNearbyProperties();
    }

    private void setMapLocation() {
        if (mMap != null) {
            // Move camera to Jeddah Albasateen location
            mMap.addMarker(new MarkerOptions().position(JEDDAH_ALBASATEEN).title("Your location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JEDDAH_ALBASATEEN, 15));  // Adjust zoom level
        }
    }

    private void fetchNearbyProperties() {
        // Query Firestore for properties
        db.collection("Apartment") // Replace with your actual Firestore collection name
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    boolean propertiesFound = false;

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Extract property details from Firestore document
                        Double latitude = document.getDouble("latitude");
                        Double longitude = document.getDouble("longitude");
                        String type = document.getString("type");
                        Double price = document.getDouble("price");

                        // Check if the property has valid coordinates
                        if (latitude != null && longitude != null) {
                            LatLng propertyLatLng = new LatLng(latitude, longitude);

                            // Check if the property is within the defined radius
                            if (isWithinRadius(JEDDAH_ALBASATEEN, propertyLatLng, RADIUS)) {
                                propertiesFound = true;

                                // Add marker for the property
                                mMap.addMarker(new MarkerOptions()
                                        .position(propertyLatLng)
                                        .title(type)
                                        .snippet("Price: " + price));
                            }
                        }
                    }

                    // Notify user if no properties were found
                    if (!propertiesFound) {
                        Toast.makeText(this, "No nearby properties found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MapsActivity", "Error fetching properties", e);
                    Toast.makeText(this, "Failed to load properties", Toast.LENGTH_SHORT).show();
                });
    }

    private boolean isWithinRadius(LatLng centerLatLng, LatLng targetLatLng, int radius) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(centerLatLng.latitude, centerLatLng.longitude,
                targetLatLng.latitude, targetLatLng.longitude, results);
        return results[0] <= radius;
    }
}