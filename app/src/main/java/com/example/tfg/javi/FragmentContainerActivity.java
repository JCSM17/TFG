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

        String fragmentName = getIntent().getStringExtra("fragmentName");
        Log.d("FragmentContainerActivity", "Fragment name: " + fragmentName); // Agrega esta línea
        try {
            Fragment fragment = (Fragment) Class.forName(fragmentName).newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // Manejar la excepción aquí, por ejemplo, registrar la excepción:
            Log.e("FragmentContainerActivity", "Error al iniciar el fragmento", e);
        }
    }
}