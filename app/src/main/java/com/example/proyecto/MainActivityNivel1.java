package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityNivel1 extends AppCompatActivity {
    private TextView tv_nombre, tv_score;
    private ImageView iv_Auno, iv_Ados, iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;

    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas = 3;
    String nombre_jugador, string_score, string_vidas;
    String NombreBaseDatos = "administracion";

    String numero [] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nivel1);

        tv_nombre = findViewById(R.id.txt_usuario);
        tv_score = findViewById(R.id.txt_puntuacion);
        et_respuesta = findViewById(R.id.et_numero);

        String texto = getIntent().getStringExtra("texto");
        tv_nombre.setText("Jugador: " + texto);

    }

    public void comprobar (View view){
        String respuesta = et_respuesta.getText().toString();

        if (!respuesta.equals("")){
            //musica cuando se tenga
            int respuesta_jugador = Integer.parseInt(respuesta);
            if (resultado == respuesta_jugador){
                score++;
                tv_score.setText("Score: "+ score);
                BaseDatos();
            }else {
                //musica cuando la tengamos
                vidas--;
                BaseDatos();

                switch (vidas){
                    case 3:
                        // iv_vidas.setImageResoucer(R.drawable."nombre de la foto de 3 vida");
                        break;
                    case 2:
                        Toast.makeText(this, "te quedan dos coches", Toast.LENGTH_SHORT).show();
                        // iv_vidas.setImageResoucer(R.drawable."nombre de la foto de 2 vida");
                        break;
                    case 1:
                        Toast.makeText(this, "Te queda un coche", Toast.LENGTH_SHORT).show();
                        // iv_vidas.setImageResoucer(R.drawable."nombre de la foto de 1 vida");
                        break;
                    case 0:
                        Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        // mp.stop();
                        // mp.release();
                        break;
                }
                tv_score.setText(score);
            }
            NumAleatorio();
        }
    }

    public void NumAleatorio(){
        if(score <= 9){

            numAleatorio_uno = (int) (Math.random() * 10);
            numAleatorio_dos = (int) (Math.random() * 10);

            resultado = numAleatorio_uno + numAleatorio_dos;

            if(resultado <= 10){

                for (int i = 0; i < numero.length; i++){
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if(numAleatorio_uno == i){
                        iv_Auno.setImageResource(id);
                    }if(numAleatorio_dos == i){
                        iv_Ados.setImageResource(id);
                    }
                }

            } else {
                NumAleatorio();
            }

        }else {
            Intent intent = new Intent(this, MainActivityNivel2.class);

            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);

            startActivity(intent);
            finish();
            // parar musica mp.stop();
            // mp.release();
        }
    }

    public void BaseDatos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos, null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor consulta = BaseDeDatos.rawQuery("select * from lista  where puntuacion = (select max(puntuacion) from lista)", null);

        if (consulta.moveToFirst()){
            String tem_nombre = consulta.getString(0);
            String tem_score = consulta.getString(1);

            int bestScore = Integer.parseInt(tem_score);

            if (score > bestScore){
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("puntuacion ", score);

                BaseDeDatos.update("lista", modificacion, "puntuacion=" + bestScore, null);
            }
            BaseDeDatos.close();

        }else {
            ContentValues insertar = new ContentValues();

            insertar.put("nombre", nombre_jugador);
            insertar.put("puntuacion", score);

            BaseDeDatos.insert("lista", null, insertar);
        }
    }
}