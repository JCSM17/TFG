package com.example.tfg.jc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Definir los botones para cada opción
        Button btnEntrenamiento = findViewById(R.id.btnEntrenamiento);
        Button btnNutricion = findViewById(R.id.btnNutricion);
        Button btnPerfil = findViewById(R.id.btnPerfil);
        Button btnProximamente = findViewById(R.id.btnProximamente);

        // Ajusta el tamaño del icono
        adjustIconSize(btnEntrenamiento);
        adjustIconSize(btnNutricion);
        adjustIconSize(btnPerfil);
        adjustIconSize(btnProximamente);

        // Establecer OnClickListener para cada botón
        btnEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("entrenamiento");
            }
        });

        btnNutricion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("nutricion");
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("perfil");
            }
        });

        btnProximamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("proximamente");
            }
        });
    }

    // Método para abrir RutinasVolumenActivity con la opción seleccionada como un extra
    private void abrirRutinasActivity(String opcionSeleccionada) {
        Intent intent = new Intent(this, RutinasVolumenActivity.class);
        intent.putExtra("categoria_seleccionada", opcionSeleccionada);
        startActivity(intent);
    }

    private void adjustIconSize(Button button) {
        // Define el tamaño deseado para el icono
        int iconSize = Math.round(getResources().getDimension(R.dimen.icon_size));

        // Obtén los drawables
        Drawable[] drawables = button.getCompoundDrawables();

        // Asegúrate de que el drawable superior (índice 1) no es nulo
        if (drawables[1] != null) {
            // Crea un nuevo drawable con el tamaño deseado
            Drawable resizedDrawable = drawables[1].mutate();
            resizedDrawable.setBounds(0, 0, iconSize, iconSize);

            // Establece el drawable en el botón
            button.setCompoundDrawables(null, resizedDrawable, null, null);
        }
    }
}

