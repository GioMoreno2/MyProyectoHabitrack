package com.gio.myhabitrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_addhabito extends AppCompatActivity {

    private EditText etHabitName;
    private EditText etHabitDescription;
    private Button btnSaveHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_addhabito);

        etHabitName = findViewById(R.id.etHabitName);
        etHabitDescription = findViewById(R.id.etHabitDescription);
        btnSaveHabit = findViewById(R.id.btnSaveHabit);

        btnSaveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String habitName = etHabitName.getText().toString().trim();
                String habitDescription = etHabitDescription.getText().toString().trim();

                if (habitName.isEmpty() || habitDescription.isEmpty()) {
                    Toast.makeText(MainActivity_addhabito.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity_addhabito.this, "Hábito guardado con éxito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}