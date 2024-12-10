package com.gio.myhabitrack;

import static com.gio.myhabitrack.R.id.action_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity_inicio extends AppCompatActivity {

    private Button btCrearHabito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inicio);


        btCrearHabito = findViewById(R.id.btnCrearHabito);
        btCrearHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity_inicio.this, MainActivity_addhabito.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navbar, menu); // Inflar el archivo de menú
        return true;
    }


}