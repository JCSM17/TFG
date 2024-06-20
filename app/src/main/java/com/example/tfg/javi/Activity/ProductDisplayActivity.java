package com.example.tfg.javi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tfg.R;
import com.example.tfg.databinding.ActivityTiendaBinding;

public class ProductDisplayActivity extends AppCompatActivity {
    ActivityTiendaBinding binding; // Binding para la actividad.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTiendaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        statusBarColor(); // Configura el color de la barra de estado
        bottomNavigation(); // Configura la navegación del botón "Carrito"
    }

    private void bottomNavigation() {
        // Configura el listener para el botón "Carrito" que abre la actividad CarritoActivity
        binding.CarritoBtn.setOnClickListener(v -> startActivity(new Intent(ProductDisplayActivity.this, CarritoActivity.class)));
    }

    private void statusBarColor() {
        Window window = ProductDisplayActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProductDisplayActivity.this, R.color.purple_Dark));
        // Configura el color de la barra de estado en morado oscuro
    }
}

