package com.example.tfg.javi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class FragmentContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        // Obtener el nombre del fragmento desde los extras del intent.
        String fragmentName = getIntent().getStringExtra("fragmentName");
        Log.d("FragmentContainerActivity", "Fragment name: " + fragmentName); // Agrega esta línea para registrar el nombre del fragmento

        try {
            // Crear una instancia del fragmento utilizando reflexión
            Fragment fragment = (Fragment) Class.forName(fragmentName).newInstance();
            // Agregar el fragmento al contenedor utilizando el FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // Manejar la excepción aquí, por ejemplo, registrar la excepción:
            Log.e("FragmentContainerActivity", "Error al iniciar el fragmento", e);
        }
    }
}