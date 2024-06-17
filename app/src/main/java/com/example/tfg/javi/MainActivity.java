package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Agregar animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        ImageView imagenLogo = findViewById(R.id.imagenLogo);
        TextView textoDesde = findViewById(R.id.textoDesde);
        TextView textoJJJFIT = findViewById(R.id.textoJJJFIT);


        textoDesde.setAnimation(animacion2);
        textoJJJFIT.setAnimation(animacion2);
        imagenLogo.setAnimation(animacion1);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_DELAY);
    }
}