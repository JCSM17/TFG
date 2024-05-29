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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planes, container, false);

        String email = getArguments().getString("email"); // Obtén el correo electrónico del usuario del Bundle

        CardView cardPlanMensual = view.findViewById(R.id.cardPlanMensual);
        CardView cardPlanAnual = view.findViewById(R.id.cardPlanAnual);

        cardPlanMensual.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getContext());
            boolean isUpdated = db.updateSubscriptionType(email, "mensual");
            if (isUpdated) {
                replaceFragment(new PasarelaFragment());
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        cardPlanAnual.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getContext());
            boolean isUpdated = db.updateSubscriptionType(email, "anual");
            if (isUpdated) {
                replaceFragment(new PasarelaFragment());
            } else {
                Toast.makeText(getContext(), "Error al actualizar el tipo de suscripción", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void replaceFragment(Fragment pasarelaFragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_planes, pasarelaFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}