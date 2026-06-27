package com.example.estatemap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Appointment extends AppCompatActivity {
    private CalendarView gregorianCalendar;
    private GridView hijriCalendar;
    private TextView gregorianTab, hijriTab, cancelButton, showLessButton;
    private ImageView backButton;
    private Button nextButton;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize Views
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        gregorianTab = findViewById(R.id.gregorianTab);
        hijriTab = findViewById(R.id.hijriTab);
        cancelButton = findViewById(R.id.cancelButton);
        showLessButton = findViewById(R.id.showLessButton);
        gregorianCalendar = findViewById(R.id.gregorianCalendar);
        hijriCalendar = findViewById(R.id.hijriCalendar);

        // Default View
        gregorianCalendar.setVisibility(View.VISIBLE);
        hijriCalendar.setVisibility(View.GONE);

        // Back Button
        backButton.setOnClickListener(view -> finish());

        // Tab Selection
        gregorianTab.setOnClickListener(view -> selectTab(gregorianTab, hijriTab));
        hijriTab.setOnClickListener(view -> selectTab(hijriTab, gregorianTab));

        // Button Listeners
        nextButton.setOnClickListener(view -> startActivity(new Intent(this, AvailableSlotsActivity.class)));
        cancelButton.setOnClickListener(view -> showToast("Appointment canceled"));
        showLessButton.setOnClickListener(view -> showToast("Show less clicked"));

        // Calendar Date Change Listener
        gregorianCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            showToast("Selected date: " + selectedDate);
        });
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void selectTab(TextView selectedTab, TextView unselectedTab) {
        selectedTab.setBackgroundResource(R.drawable.selected_tab_background);
        selectedTab.setTextColor(getResources().getColor(android.R.color.white));
        unselectedTab.setBackgroundResource(R.drawable.unselected_tab_background);
        unselectedTab.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }
}
