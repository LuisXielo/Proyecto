package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private Button btn_jugar;
    String NombreBaseDatos = "administracion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = findViewById(R.id.et_nombre);
        btn_jugar = findViewById(R.id.btn_jugar);
    }

    // metodo jugar
    public void Jugar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos, null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String nombre = et_nombre.getText().toString();

        if(!nombre.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);

            BaseDeDatos.insert("Lista", null, registro);

            BaseDeDatos.close();
            String texto = nombre;
            Intent i = new Intent(this, MainActivityNivel1.class);
            i.putExtra("texto", texto);
            startActivity(i);
            Toast.makeText(this, "Nivel 1", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Introduce un nombre para continuar", Toast.LENGTH_SHORT).show();
        }
    }
}