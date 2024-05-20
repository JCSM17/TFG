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

    private static final String INICIO_SELECTED = "Inicio seleccionado";
    private static final String MENSAJE_SELECTED = "Mensaje seleccionado";
    private static final String SINCRONIZAR_SELECTED = "Sincronizar seleccionado";
    private static final String PAPELERA_SELECTED = "Papelera seleccionada";
    private static final String CONFIGURACION_SELECTED = "Configuración seleccionada";
    private static final String INICIO_SESION_SELECTED = "Inicio de sesión seleccionado";
    private static final String COMPARTIR_SELECTED = "Compartir seleccionado";
    private static final String CALIFICANOS_SELECTED = "Califícanos seleccionado";

    private final Map<Integer, Runnable> actions = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.optionsButton);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Mapea los IDs de los elementos del menú a las acciones correspondientes
        actions.put(R.id.nav_home, this::showInicioSelected);
        actions.put(R.id.nav_message, this::showMensajeSelected);
        actions.put(R.id.synch, this::showSincronizarSelected);
        actions.put(R.id.trash, this::showPapeleraSelected);
        actions.put(R.id.settings, this::showConfiguracionSelected);
        actions.put(R.id.nav_login, this::showInicioSesionSelected);
        actions.put(R.id.nav_share, this::showCompartirSelected);
        actions.put(R.id.nav_rate, this::showCalificanosSelected);

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

    private void showInicioSelected() {
        showToast(INICIO_SELECTED);
    }

    private void showMensajeSelected() {
        showToast(MENSAJE_SELECTED);
    }

    private void showSincronizarSelected() {
        showToast(SINCRONIZAR_SELECTED);
    }

    private void showPapeleraSelected() {
        showToast(PAPELERA_SELECTED);
    }

    private void showConfiguracionSelected() {
        showToast(CONFIGURACION_SELECTED);
    }

    private void showInicioSesionSelected() {
        showToast(INICIO_SESION_SELECTED);
    }

    private void showCompartirSelected() {
        showToast(COMPARTIR_SELECTED);
    }

    private void showCalificanosSelected() {
        showToast(CALIFICANOS_SELECTED);
    }
}