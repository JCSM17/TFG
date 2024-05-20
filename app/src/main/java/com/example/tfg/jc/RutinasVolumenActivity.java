package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tfg.R;
import com.example.tfg.jesus.AbdominalesFragment;
import com.example.tfg.jesus.VolumenJuevesFragment;
import com.example.tfg.jesus.VolumenLunesFragment;
import com.example.tfg.jesus.VolumenMartesFragment;
import com.example.tfg.jesus.VolumenViernesFragment;

public class RutinasVolumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_volumen);

        setupView(R.id.cardLunesVolumen, VolumenLunesFragment.class);
        setupView(R.id.cardMartesVolumen, VolumenMartesFragment.class);
        setupView(R.id.cardMiercolesVolumen, AbdominalesFragment.class);
        setupView(R.id.cardJuevesVolumen, VolumenJuevesFragment.class);
        setupView(R.id.cardViernesVolumen, VolumenViernesFragment.class);

        setupView(R.id.btnMainMenuVolumen, MenuActivity.class);
    }

    private void setupView(int viewId, Class<?> activityClass) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasVolumenActivity.this, activityClass);
            startActivity(intent);
        });
    }
}