package com.example.estatemap;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        // Reference to the checkbox and button
        CheckBox checkboxAgree = findViewById(R.id.checkbox_agree_terms);
        Button btnNext = findViewById(R.id.btn_next);

        // Disable the button initially
        btnNext.setEnabled(false);

        // Enable the button only when the checkbox is checked
        checkboxAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnNext.setEnabled(isChecked);
        });

        // Set OnClickListener for the "Next" button
        btnNext.setOnClickListener(view -> {
            // Navigate to the Lease Agreement Activity
            Intent intent = new Intent(TermsConditionsActivity.this, LeaseAgreementActivity.class);
            startActivity(intent);
        });
    }
}

