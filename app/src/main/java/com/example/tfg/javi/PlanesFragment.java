package com.example.tfg.javi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;

public class PlanesFragment extends Fragment {

    private DatabaseHelper db;
    private final double PRECIO_PLAN_MENSUAL = 29.99; // Define el precio del plan mensual
    private final double PRECIO_PLAN_ANUAL = 249.99; // Define el precio del plan anual

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planes, container, false);

        // Obtén el ID del usuario desde la URL profunda
        long userId = getArguments().getLong("userId");

        // Inicializa el helper de la base de datos
        db = new DatabaseHelper(getContext());

        // Obtiene los datos de registro del usuario actual
        RegistroData registroData = db.getRegistroPorId(userId);

        // Configura los listener para los CardView de los planes mensual y anual
        CardView cardPlanMensual = view.findViewById(R.id.cardPlanMensual);
        CardView cardPlanAnual = view.findViewById(R.id.cardPlanAnual);

        // Listener para el plan mensual
        cardPlanMensual.setOnClickListener(v -> {
            boolean isUpdated = db.updateSubscriptionType(userId, "mensual");
            if (isUpdated) {
                // Reemplaza el fragmento actual con PasarelaFragment y pasa el precio del plan mensual
                replaceFragment(new PasarelaFragment(), PRECIO_PLAN_MENSUAL);
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para el plan anual
        cardPlanAnual.setOnClickListener(v -> {
            boolean isUpdated = db.updateSubscriptionType(userId, "anual");
            if (isUpdated) {
                // Reemplaza el fragmento actual con PasarelaFragment y pasa el precio del plan anual
                replaceFragment(new PasarelaFragment(), PRECIO_PLAN_ANUAL);
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Método para reemplazar el fragmento actual por PasarelaFragment con el precio del plan
    private void replaceFragment(Fragment pasarelaFragment, double precioPlan) {
        Bundle bundle = new Bundle();
        bundle.putDouble("precioPlan", precioPlan); // Añade el precio del plan al Bundle
        pasarelaFragment.setArguments(bundle); // Pasa el Bundle al PasarelaFragment

        // Inicia la transacción para reemplazar el fragmento actual
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, pasarelaFragment);
        transaction.commit();
    }
}