package com.example.tfg.jc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;
import com.example.tfg.javi.TiendaActivity;
import com.example.tfg.jesus.NutricionActivity;
import com.example.tfg.jesus.PerfilActivity;


import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    private static final String ENTRENAMIENTO = "entrenamiento";
    private static final String NUTRICION = "nutricion";

    private DatabaseHelper databaseHelper;

    private enum Objetivo {
        VOLUMEN,
        DEFINICION
    }

    private final Map<Integer, String> buttonMap = new HashMap<Integer, String>() {{
        put(R.id.btnEntrenamiento, ENTRENAMIENTO);
        put(R.id.btnNutricion, NUTRICION);
        put(R.id.btnPerfil, "perfil");
        put(R.id.btnTienda, "tienda");
    }};

    private ViewPager2 viewPager2;
    private final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseHelper = new DatabaseHelper(this);

        // Imprimir los nombres de las columnas
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userData LIMIT 1", null);
        String[] columnNames = cursor.getColumnNames();
        for (String name : columnNames) {
            Log.d("MenuActivity", "Column name: " + name); // Cambiado System.out.println por Log.d
        }

        // Cierra el cursor después de usarlo
        cursor.close();

        for (Map.Entry<Integer, String> entry : buttonMap.entrySet()) {
            setupButton(entry.getKey(), entry.getValue());
        }

        int[] images = {R.drawable.foto_jc, R.drawable.jesusmenu, R.drawable.javimenu};
        viewPager2 = findViewById(R.id.imageCarousel);
        viewPager2.setAdapter(new ImageAdapter(images, this));

        runnable = new Runnable() {
            public void run() {
                if (viewPager2.getCurrentItem() == images.length - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                handler.postDelayed(runnable, 3000); // Cambia las imágenes cada 3 segundos
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void setupButton(int buttonId, String opcionSeleccionada) {
        LinearLayout linearLayout = findViewById(buttonId);
        linearLayout.setOnClickListener(v -> abrirSeleccionActivity(opcionSeleccionada));
    }

    private void abrirSeleccionActivity(String opcionSeleccionada) {
        Intent intent;
        if (opcionSeleccionada.equals(NUTRICION)) {
            intent = new Intent(this, NutricionActivity.class);
        } else if (opcionSeleccionada.equals("perfil")) {
            intent = new Intent(this, PerfilActivity.class);
        } else if (opcionSeleccionada.equals("tienda")) {
            intent = new Intent(this, TiendaActivity.class);
        } else {
            intent = getRelevantIntent(opcionSeleccionada);
        }

        intent.putExtra(CATEGORIA_SELECCIONADA, opcionSeleccionada);
        startActivity(intent);
    }

    private Intent getRelevantIntent(String opcionSeleccionada) {
        if (!opcionSeleccionada.equals(ENTRENAMIENTO)) {
            throw new IllegalArgumentException("Opción seleccionada inválida");
        }

        SharedPreferences sharedPref = getSharedPreferences("tfg_preferences", MODE_PRIVATE);
        int userId = sharedPref.getInt("userId", -1); // Obtén el ID del usuario que ha iniciado sesión

        if (userId == -1) {
            String userEmail = databaseHelper.getUserEmail();
            userId = databaseHelper.getUserId(userEmail);
            if (userId == -1) {
                throw new IllegalArgumentException("No se ha iniciado sesión");
            }
        }

        String objetivoStr = databaseHelper.getUserGoal(userId);
        Objetivo objetivo = Objetivo.valueOf(objetivoStr.toUpperCase());

        Class<?> activityClass;
        switch (objetivo) {
            case VOLUMEN:
                activityClass = RutinasVolumenActivity.class;
                break;
            case DEFINICION:
                activityClass = RutinasDefinicionActivity.class;
                break;
            default:
                throw new IllegalArgumentException("Objetivo de usuario inválido");
        }

        return new Intent(this, activityClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Detiene el cambio automático de imágenes cuando la actividad es destruida
    }
}