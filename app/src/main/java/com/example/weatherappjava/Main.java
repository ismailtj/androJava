package com.example.weatherappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Main extends AppCompatActivity {

    private FloatingActionButton addPositionFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lyout);

        addPositionFAB = findViewById(R.id.addPositionFAB);

        addPositionFAB.setOnClickListener(
                view -> {
                    Intent intent = new Intent(Main.this, AddPostionForm.class);
                    startActivity(intent);
                });
    }

}
