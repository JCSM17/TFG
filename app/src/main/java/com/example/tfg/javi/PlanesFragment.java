package com.example.tfg.javi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;

public class PlanesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planes, container, false);

        CardView cardPlanMensual = view.findViewById(R.id.cardPlanMensual);
        CardView cardPlanAnual = view.findViewById(R.id.cardPlanAnual);

        cardPlanMensual.setOnClickListener(v -> replaceFragment(new SuscripcionPlanMensualFragment()));
        cardPlanAnual.setOnClickListener(v -> replaceFragment(new SuscripcionPlanAnualFragment()));

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_planes, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}