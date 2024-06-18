package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;
import com.example.tfg.javi.TiendaActivity;
import com.example.tfg.jesus.NutricionActivity;
import com.example.tfg.jesus.PerfilActivity;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    private static final String ERROR_OBJETIVO_NO_RECONOCIDO = "Error: Objetivo no reconocido";
    private static final String ENTRENAMIENTO = "entrenamiento";
    private static final String NUTRICION = "nutricion";

    private DatabaseHelper databaseHelper;

    private enum Objetivo {
        VOLUMEN,
        DEFINICION
    }

    private final Map<Integer, String> buttonMap = new HashMap<Integer, String>() {{
        put(R.id.btnEntrenamiento, ENTRENAMIENTO);
        put(R.id.btnNutricion, NUTRICION);
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
        LinearLayout linearLayout = findViewById(buttonId);
        linearLayout.setOnClickListener(v -> abrirSeleccionActivity(opcionSeleccionada));
    }

    private void abrirSeleccionActivity(String opcionSeleccionada) {
        Intent intent;
        if (opcionSeleccionada.equals(NUTRICION)) {
            intent = new Intent(this, NutricionActivity.class);
        } else if (opcionSeleccionada.equals("perfil")) {
            intent = new Intent(this, PerfilActivity.class);
        } else if (opcionSeleccionada.equals("tienda")) {
            intent = new Intent(this, TiendaActivity.class);
        } else {
            intent = getRelevantIntent(opcionSeleccionada);
        }

        intent.putExtra(CATEGORIA_SELECCIONADA, opcionSeleccionada);
        startActivity(intent);
    }

    private Intent getRelevantIntent(String opcionSeleccionada) {
        if (!opcionSeleccionada.equals(ENTRENAMIENTO)) {
            throw new IllegalArgumentException("Opción seleccionada inválida");
        }

        int userId = databaseHelper.getUserId();
        String objetivoStr = databaseHelper.getUserGoal(userId);
        Objetivo objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());

        Class<?> activityClass;
        switch (objetivo) {
            case VOLUMEN:
                activityClass = RutinasVolumenActivity.class;
                break;
            case DEFINICION:
                activityClass = RutinasDefinicionActivity.class;
                break;
            default:
                throw new IllegalArgumentException("Objetivo de usuario inválido");
        }

        return new Intent(this, activityClass);
    }
}