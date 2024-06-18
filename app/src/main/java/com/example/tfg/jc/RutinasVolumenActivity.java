package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.FragmentContainerActivity;
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

        setupFragmentView(R.id.cardLunesVolumen, VolumenLunesFragment.class.getName());
        setupFragmentView(R.id.cardMartesVolumen, VolumenMartesFragment.class.getName());
        setupFragmentView(R.id.cardMiercolesVolumen, AbdominalesFragment.class.getName());
        setupFragmentView(R.id.cardJuevesVolumen, VolumenJuevesFragment.class.getName());
        setupFragmentView(R.id.cardViernesVolumen, VolumenViernesFragment.class.getName());

        setupActivityView(R.id.btnMainMenuVolumen);
    }

    private void setupFragmentView(int viewId, String fragmentClassName) {
        View view = findViewById(viewId);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(RutinasVolumenActivity.this, FragmentContainerActivity.class);
            intent.putExtra("fragmentName", fragmentClassName);
            startActivity(intent);
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