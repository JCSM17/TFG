package com.example.tfg.jc;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.jesus.VolumenJuevesFragment;

import java.util.HashMap;
import java.util.Map;

public class RutinasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Recibir el identificador de la rutina seleccionada (definición o volumen) del Intent o de cualquier otra fuente
        // Supongamos que recibimos este valor en una variable llamada "rutinaSeleccionada"

        String rutinaSeleccionada = "volumen_jueves"; // Solo para fines de ejemplo, debería recibir este valor de alguna manera

        int layoutId;
        Map<Integer, String> buttonToUrlMap;

        // Determina la rutina seleccionada
        switch (rutinaSeleccionada) {
            case "volumen_jueves":
                layoutId = R.layout.fragment_volumen_jueves;
                buttonToUrlMap = getButtonToUrlMapFromResources(R.array.urls_volumen_jueves);
                break;
            case "volumen_viernes":
                layoutId = R.layout.fragment_volumen_viernes;
                buttonToUrlMap = getButtonToUrlMapFromResources(R.array.urls_volumen_viernes);
                break;
            // Agrega más casos según sea necesario para otras rutinas
            default:
                // Si no coincide con ninguna rutina conocida, establece un valor predeterminado
                layoutId = R.layout.activity_menu; // Puedes cambiar esto según tus necesidades
                buttonToUrlMap = getDefaultButtonToUrlMap();
                break;
        }

        VolumenJuevesFragment volumenJuevesFragment = new VolumenJuevesFragment(buttonToUrlMap);
        Bundle args = new Bundle();
        args.putInt("layout_id", layoutId);
        volumenJuevesFragment.setArguments(args);

        // Añade el fragmento al contenedor correspondiente
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, volumenJuevesFragment); // Reemplaza el contenido del contenedor con el fragmento seleccionado
        transaction.commit();
    }

    // Método para obtener el mapeo de botones a URL desde los recursos definidos en arrays.xml
    private Map<Integer, String> getButtonToUrlMapFromResources(int arrayResourceId) {
        Map<Integer, String> buttonToUrlMap = new HashMap<>();
        Resources resources = getResources();
        String[] urlsArray = resources.getStringArray(arrayResourceId);
        int[] buttonIds = resources.getIntArray(R.array.button_ids);
        for (int i = 0; i < Math.min(buttonIds.length, urlsArray.length); i++) {
            buttonToUrlMap.put(buttonIds[i], urlsArray[i]);
        }
        return buttonToUrlMap;
    }

    // Método para obtener el mapeo predeterminado de botones a URL en caso de error o rutina no reconocida

    private Map<Integer, String> getDefaultButtonToUrlMap() {
        Map<Integer, String> defaultButtonToUrlMap = new HashMap<>();
        // Configurar los URLs para los botones de una rutina predeterminada o manejar el caso de error
        return defaultButtonToUrlMap;
    }
}
