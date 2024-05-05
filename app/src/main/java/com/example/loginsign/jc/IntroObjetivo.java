package com.example.loginsign.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsign.R;

public class IntroObjetivo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText heightInput = findViewById(R.id.heightInput);
        EditText ageInput = findViewById(R.id.ageInput);
        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        Button nextButton = findViewById(R.id.nextButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String height = heightInput.getText().toString();
                String age = ageInput.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();

                // Validar las entradas del usuario
                if (selectedId == -1 || height.isEmpty() || age.isEmpty() || gender.equals("Género")) {
                    Toast.makeText(IntroObjetivo.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceder a la siguiente pantalla
                    Intent intent = new Intent(IntroObjetivo.this, TiposCuerpo.class);
                    intent.putExtra("selectedId", selectedId);
                    intent.putExtra("height", height);
                    intent.putExtra("age", age);
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                }
            }
        });
    }
}