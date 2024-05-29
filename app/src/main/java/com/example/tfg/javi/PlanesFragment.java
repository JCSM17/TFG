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
    private final double PRECIO_PLAN_MENSUAL = 10.0; // Define el precio del plan mensual
    private final double PRECIO_PLAN_ANUAL = 100.0; // Define el precio del plan anual

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planes, container, false);

        String email = getArguments().getString("email");
        db = new DatabaseHelper(getContext());
        RegistroData registroData = db.getRegistroByEmail(email);
        long userId = registroData.getId();

        CardView cardPlanMensual = view.findViewById(R.id.cardPlanMensual);
        CardView cardPlanAnual = view.findViewById(R.id.cardPlanAnual);

        cardPlanMensual.setOnClickListener(v -> {
            boolean isUpdated = db.updateSubscriptionType(userId, "mensual");
            if (isUpdated) {
                replaceFragment(new PasarelaFragment(), PRECIO_PLAN_MENSUAL);
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        cardPlanAnual.setOnClickListener(v -> {
            boolean isUpdated = db.updateSubscriptionType(userId, "anual");
            if (isUpdated) {
                replaceFragment(new PasarelaFragment(), PRECIO_PLAN_ANUAL);
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void replaceFragment(Fragment pasarelaFragment, double precioPlan) {
        Bundle bundle = new Bundle();
        bundle.putDouble("precioPlan", precioPlan); // Añade el precio del plan al Bundle
        pasarelaFragment.setArguments(bundle); // Pasa el Bundle al PasarelaFragment

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_planes, pasarelaFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}