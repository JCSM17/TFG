package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tfg.R;
import com.example.tfg.jesus.AbdominalesFragment;
import com.example.tfg.jesus.VolumenJuevesFragment;
import com.example.tfg.jesus.VolumenLunesFragment;
import com.example.tfg.jesus.VolumenViernesFragment;

public class RutinasVolumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_volumen);

        setupCardView(R.id.cardLunesVolumen, VolumenLunesFragment.class);
        setupCardView(R.id.cardMartesVolumen, VolumenMartesFragment.class);
        setupCardView(R.id.cardMiercolesVolumen, AbdominalesFragment.class);
        setupCardView(R.id.cardJuevesVolumen, VolumenJuevesFragment.class);
        setupCardView(R.id.cardViernesVolumen, VolumenViernesFragment.class);

        setupImageButton(R.id.btnMainMenuVolumen, MenuActivity.class);
    }

    private void setupCardView(int cardViewId, Class<?> activityClass) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasVolumenActivity.this, activityClass);
            startActivity(intent);
        });
    }

    private void setupImageButton(int imageButtonId, Class<?> activityClass) {
        ImageButton imageButton = findViewById(imageButtonId);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasVolumenActivity.this, activityClass);
            startActivity(intent);
        });
    }
}