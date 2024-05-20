package com.example.tfg.jc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class HeaderFragment extends Fragment {

    private final Map<Integer, Runnable> actions = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.optionsButton);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Mapea los IDs de los elementos del menú a las acciones correspondientes
        actions.put(R.id.nav_home, () -> showToast("Inicio seleccionado"));
        actions.put(R.id.nav_message, () -> showToast("Mensaje seleccionado"));
        actions.put(R.id.synch, () -> showToast("Sincronizar seleccionado"));
        actions.put(R.id.trash, () -> showToast("Papelera seleccionada"));
        actions.put(R.id.settings, () -> showToast("Configuración seleccionada"));
        actions.put(R.id.nav_login, () -> showToast("Inicio de sesión seleccionado"));
        actions.put(R.id.nav_share, () -> showToast("Compartir seleccionado"));
        actions.put(R.id.nav_rate, () -> showToast("Califícanos seleccionado"));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            // Ejecuta la acción correspondiente al elemento del menú seleccionado
            actions.getOrDefault(id, () -> {
            }).run();
            return true;
        });

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}