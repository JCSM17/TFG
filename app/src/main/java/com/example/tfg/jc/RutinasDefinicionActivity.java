package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tfg.R;
import com.example.tfg.jesus.DefinicionLunesFragment;
import com.example.tfg.jesus.DefinicionMartesFragment;

public class RutinasDefinicionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_definicion);

        // Definition routines
        setupView(R.id.cardLunesDefinicion, DefinicionLunesFragment.class);
        setupView(R.id.cardMartesDefinicion, DefinicionMartesFragment.class);
        setupView(R.id.cardMiercolesDefinicion, DefinicionLunesFragment.class);
        setupView(R.id.cardJuevesDefinicion, DefinicionMartesFragment.class);
        setupView(R.id.cardViernesDefinicion, DefinicionMartesFragment.class);

        setupView(R.id.btnMainMenuDefinicion, MenuActivity.class);
    }

    private void setupView(int viewId, Class<?> activityClass) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasDefinicionActivity.this, activityClass);
            startActivity(intent);
        });
    }
}