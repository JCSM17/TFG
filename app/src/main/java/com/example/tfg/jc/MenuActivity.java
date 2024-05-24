package com.example.tfg.jc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

public class MenuActivity extends AppCompatActivity {

    private static final String OBJETIVO_KEY = "objetivo";
    private static final String PREFERENCES_NAME = "com.example.tfg.preferences";

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseHelper = new DatabaseHelper(this);

        setupButton(R.id.btnEntrenamiento, "entrenamiento");
        setupButton(R.id.btnNutricion, "nutricion");
        setupButton(R.id.btnPerfil, "perfil");
        setupButton(R.id.btnTienda, "tienda");
    }

    private void setupButton(int buttonId, String opcionSeleccionada) {
        Button button = findViewById(buttonId);
        adjustIconSize(button);
        button.setOnClickListener(v -> abrirRutinasActivity(opcionSeleccionada));
    }

    private void abrirRutinasActivity(String opcionSeleccionada) {
        String objetivo = databaseHelper.getUserGoal();
        Intent intent;

        switch (objetivo) {
            case "volumen":
                intent = new Intent(this, RutinasVolumenActivity.class);
                break;
            case "definicion":
                intent = new Intent(this, RutinasDefinicionActivity.class);
                break;
            default:
                throw new IllegalArgumentException("Objetivo desconocido: " + objetivo);
        }

        intent.putExtra("categoria_seleccionada", opcionSeleccionada);
        startActivity(intent);
    }

    private void adjustIconSize(Button button) {
        int iconSize = Math.round(getResources().getDimension(R.dimen.icon_size));
        Drawable[] drawables = button.getCompoundDrawables();

        if (drawables[1] != null) {
            Drawable resizedDrawable = drawables[1].mutate();
            resizedDrawable.setBounds(0, 0, iconSize, iconSize);
            button.setCompoundDrawables(null, resizedDrawable, null, null);
        }
    }
}