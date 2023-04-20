package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivityNivel1 extends AppCompatActivity {

    private TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nivel1);

        tv1 = findViewById(R.id.txt_usuario);

        String texto = getIntent().getStringExtra("texto");
        tv1.setText(texto);

    }
}