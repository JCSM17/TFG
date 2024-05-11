package com.example.tfg.jesus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tfg.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutinasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutinasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RutinasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutinasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutinasFragment newInstance(String param1, String param2) {
        RutinasFragment fragment = new RutinasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutina_volumen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Map of button IDs to YouTube URLs
        Map<Integer, String> buttonToUrlMap = new HashMap<>();
        buttonToUrlMap.put(R.id.botonVideoPressBanca, "https://www.youtube.com/shorts/i14IBMNQDQQ");
        buttonToUrlMap.put(R.id.botonVideoFondos, "https://www.youtube.com/shorts/lC7lLkjDZ_k");
        buttonToUrlMap.put(R.id.botonVideoPolea, "https://www.youtube.com/shorts/wHKYSABOpGY");
        buttonToUrlMap.put(R.id.buttonFrancesMancuerna, "https://www.youtube.com/shorts/DFFD1LU_iXw");
        buttonToUrlMap.put(R.id.botonVideoExtTriceps, "https://www.youtube.com/shorts/JVc1KAB_HLY");
        buttonToUrlMap.put(R.id.botonVideoPressPalof, "https://www.youtube.com/shorts/r3fM413P3W0");
        // Add more button IDs and URLs as needed

        // Set up click listeners for each button
        for (Map.Entry<Integer, String> entry : buttonToUrlMap.entrySet()) {
            Button button = view.findViewById(entry.getKey());
            button.setOnClickListener(v -> openYoutubeVideo(entry.getValue()));
        }
    }

    private void openYoutubeVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}