package com.nadla777;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton login = findViewById(R.id.letsgo);
        login.setOnClickListener(view -> startNewActivity());
    }

    //Log in Activity
    private void startNewActivity() {
        Intent in = new Intent(this, LoggedinActivity.class);
        startActivity(in);
    }
}