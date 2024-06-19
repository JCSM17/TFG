package com.example.tfg.javi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tfg.R;
import com.example.tfg.databinding.ActivityTiendaBinding;

public class ProductDisplayActivity extends AppCompatActivity {
    ActivityTiendaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTiendaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        statusBarColor();
        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.CarritoBtn.setOnClickListener(v -> startActivity(new Intent(ProductDisplayActivity.this, CarritoActivity.class)));
    }

    private void statusBarColor() {
        Window window = ProductDisplayActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProductDisplayActivity.this, R.color.purple_Dark));
    }
}
