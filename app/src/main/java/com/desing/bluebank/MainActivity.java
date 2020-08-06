package com.desing.bluebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // INSTANCIA DE LOS BOTONES PARA IR A LA OPCION DESEADA
    Button registrar,consignar,retirar,consultar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // COMUNICACIÓN ENTRE LA PARTE LOGICA Y LA PARTE GRAFICA
        registrar=findViewById(R.id.registrar);
        consignar=findViewById(R.id.consignar);
        retirar=findViewById(R.id.retirar);
        consultar=findViewById(R.id.consultar);

        //INGRESO A LAS OPCIONES DEL BANCO

        //REGISTRO DE CUENTA DE AHORROS
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Registrar.class);
                startActivity(i);
            }
        });
        //CONSIGNAR A UNA CUENTA DE AHORROS
        consignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Consignar.class);
                startActivity(i);
            }
        });
        //RETIRAR DE UNA CUENTA DE AHORROS
        retirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Retirar.class);
                startActivity(i);
            }
        });
        //CONSULTAR SALDO DE UNA CUENTA DE AHORROS
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Consultar.class);
                startActivity(i);
            }
        });

    }
}