package com.example.tfg.javi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tfg.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método para el clic del botón (definido en el XML)
    public void irAPrincipal(View view) {
        Intent intent = new Intent(this, PlanAnualActivity.class);
        startActivity(intent);
    }
}
