package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "com.example.tfg.preferences";

    private DatabaseHelper databaseHelper;

    private enum Objetivo {
        VOLUMEN,
        DEFINICION
    }

    private final Map<Integer, String> buttonMap = new HashMap<Integer, String>() {{
        put(R.id.btnEntrenamiento, "entrenamiento");
        put(R.id.btnNutricion, "nutricion");
        put(R.id.btnPerfil, "perfil");
        put(R.id.btnTienda, "tienda");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseHelper = new DatabaseHelper(this);

        for (Map.Entry<Integer, String> entry : buttonMap.entrySet()) {
            setupButton(entry.getKey(), entry.getValue());
        }
    }

    private void setupButton(int buttonId, String opcionSeleccionada) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> abrirRutinasActivity(opcionSeleccionada));
    }

    private void abrirRutinasActivity(String opcionSeleccionada) {
        String userEmail = databaseHelper.getUserEmail();
        String objetivoStr = databaseHelper.getUserGoal(userEmail);        Objetivo objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());
        Intent intent = null; // Inicializa intent con un valor por defecto
        switch (objetivo) {
            case VOLUMEN:
                intent = new Intent(this, RutinasVolumenActivity.class);
                break;
            case DEFINICION:
                intent = new Intent(this, RutinasDefinicionActivity.class);
                break;
        }

        if (intent != null) {
            intent.putExtra("categoria_seleccionada", opcionSeleccionada);
            startActivity(intent);
        } else {
            // Maneja el caso en que intent sigue siendo null
        }
    }
}