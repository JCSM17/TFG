package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

        setupFragmentView(R.id.cardLunesVolumen, new VolumenLunesFragment());
        setupFragmentView(R.id.cardMartesVolumen, new VolumenMartesFragment());
        setupFragmentView(R.id.cardMiercolesVolumen, new AbdominalesFragment());
        setupFragmentView(R.id.cardJuevesVolumen, new VolumenJuevesFragment());
        setupFragmentView(R.id.cardViernesVolumen, new VolumenViernesFragment());

        setupActivityView(R.id.btnMainMenuVolumen);
    }

    private void setupFragmentView(int viewId, Fragment fragment) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        });
    }

    private void setupActivityView(int viewId) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasVolumenActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}