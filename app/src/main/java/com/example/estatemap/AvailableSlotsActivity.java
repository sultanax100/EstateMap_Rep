package com.example.estatemap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AvailableSlotsActivity extends AppCompatActivity {
    private TextView selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_slots);

        // Initialize views
        selectedDate = findViewById(R.id.selectedDate);

        // Get selected date from Intent
        String date = getIntent().getStringExtra("SELECTED_DATE");
        selectedDate.setText(date);

        // Implement further logic (e.g., fetch available slots)


        ImageView backButton = findViewById(R.id.backButton);

        // Back button click listener
        backButton.setOnClickListener(view -> {
            // Close the current activity
            finish();
        });

        Button confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(view -> {
            // Navigate to SuccessActivity
            Intent intent = new Intent(AvailableSlotsActivity.this, SuccessActivity.class);
            startActivity(intent);
        });
    }
}