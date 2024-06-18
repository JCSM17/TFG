package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.FragmentContainerActivity;
import com.example.tfg.jesus.AbdominalesFragment;
import com.example.tfg.jesus.DefinicionJuevesFragment;
import com.example.tfg.jesus.DefinicionLunesFragment;
import com.example.tfg.jesus.DefinicionMartesFragment;
import com.example.tfg.jesus.DefinicionViernesFragment;

public class RutinasDefinicionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas_definicion);

        // Definition routines
        setupFragmentView(R.id.cardLunesDefinicion, DefinicionLunesFragment.class.getName());
        setupFragmentView(R.id.cardMartesDefinicion, DefinicionMartesFragment.class.getName());
        setupFragmentView(R.id.cardMiercolesDefinicion, AbdominalesFragment.class.getName());
        setupFragmentView(R.id.cardJuevesDefinicion, DefinicionJuevesFragment.class.getName());
        setupFragmentView(R.id.cardViernesDefinicion, DefinicionViernesFragment.class.getName());

        setupActivityView(R.id.btnMainMenuDefinicion);
    }

    private void setupFragmentView(int viewId, String fragmentClassName) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasDefinicionActivity.this, FragmentContainerActivity.class);
            intent.putExtra("fragmentName", fragmentClassName);
            startActivity(intent);
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