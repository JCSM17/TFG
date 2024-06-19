package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tfg.R;

public class MainActivity extends AppCompatActivity {

    // Constante para el retardo en milisegundos del splash screen
    private static final int SPLASH_SCREEN_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura la actividad para ocupar toda la pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Cambia el color de fondo de la vista raíz a un color específico
        View root = findViewById(android.R.id.content);
        root.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_navy_blue));

        // Carga y aplica animaciones a los elementos de la interfaz
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        ImageView imagenLogo = findViewById(R.id.imagenLogo);
        TextView textoJJJFIT = findViewById(R.id.textoJJJFIT);

        textoJJJFIT.setAnimation(animacion2); // Asigna animación al texto
        imagenLogo.setAnimation(animacion1); // Asigna animación a la imagen del logo

        // Configura un retardo para pasar a la actividad de inicio de sesión
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_DELAY);
    }
}
