package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.jesus.DefinicionLunesFragment;
import com.example.tfg.jesus.DefinicionMartesFragment;

public class RutinasDefinicionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_definicion);

        // Definition routines
        setupFragmentView(R.id.cardLunesDefinicion, new DefinicionLunesFragment());
        setupFragmentView(R.id.cardMartesDefinicion, new DefinicionMartesFragment());
        setupFragmentView(R.id.cardMiercolesDefinicion, new DefinicionLunesFragment());
        setupFragmentView(R.id.cardJuevesDefinicion, new DefinicionMartesFragment());
        setupFragmentView(R.id.cardViernesDefinicion, new DefinicionMartesFragment());

        setupActivityView(R.id.btnMainMenuDefinicion);
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
            Intent intent = new Intent(RutinasDefinicionActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}