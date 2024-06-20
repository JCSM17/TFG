package com.example.tfg.javi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.jc.IntroObjetivoFragment;

public class SuscripcionConfirmFragment extends Fragment {

    private static final String CONFIRMATION_MESSAGE = "¡Gracias por suscribirte!";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suscripcion_confirm, container, false);

        // Aquí puedes realizar cualquier inicialización adicional que necesites

        // Por ejemplo, si deseas mostrar un mensaje de confirmación
        TextView confirmationMessageTextView = view.findViewById(R.id.mensajeConfirmacion);
        confirmationMessageTextView.setText(CONFIRMATION_MESSAGE);

        // Obtener referencia al botón "Ir a la pantalla principal"
        Button irObjetivoBoton = view.findViewById(R.id.botonIrObjetivo);

        // Configurar el OnClickListener para el botón
        irObjetivoBoton.setOnClickListener(v -> startIntroObjetivoFragment());

        return view;
    }

    private void startIntroObjetivoFragment() {
        // Crea una instancia del fragmento IntroObjetivoFragment
        IntroObjetivoFragment introObjetivoFragment = new IntroObjetivoFragment();

        // Obtiene el FragmentManager desde la actividad requerida
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Verifica que el FragmentManager no sea nulo antes de comenzar la transacción.
        if (fragmentManager != null) {
            // Inicia una transacción para reemplazar el fragmento actual con IntroObjetivoFragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, introObjetivoFragment); // Reemplaza el fragmento actual
            fragmentTransaction.commit(); // Aplica los cambios en la transacción
        }
    }
}