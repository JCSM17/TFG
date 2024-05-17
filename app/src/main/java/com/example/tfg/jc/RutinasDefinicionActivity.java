package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tfg.R;
import com.example.tfg.jesus.DefinicionLunesFragment;
import com.example.tfg.jesus.DefinicionMartesFragment;
;

public class RutinasDefinicionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_definicion);

        // Definition routines
        setupCardView(R.id.cardLunesDefinicion, DefinicionLunesFragment.class);
        setupCardView(R.id.cardMartesDefinicion, DefinicionMartesFragment.class);
        setupCardView(R.id.cardMiercolesDefinicion, DefinicionLunesFragment.class);
        setupCardView(R.id.cardJuevesDefinicion, DefinicionMartesFragment.class);
        setupCardView(R.id.cardViernesDefinicion, DefinicionMartesFragment.class);

        setupImageButton(R.id.btnMainMenuDefinicion, MenuActivity.class);

    }


    private void setupCardView(int cardViewId, Class<?> activityClass) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasDefinicionActivity.this, activityClass);
            startActivity(intent);
        });
    }

    private void setupImageButton(int imageButtonId, Class<?> activityClass) {
        ImageButton imageButton = findViewById(imageButtonId);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasDefinicionActivity.this, activityClass);
            startActivity(intent);
        });
    }
}