package com.example.estatemap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method called by the button click, specified by android:onClick
    public void onGetStarted(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent); // Start the SignUp Activity
    }
}