package com.desing.bluebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.ValorCuenta;

public class Consultar extends AppCompatActivity {
    // INNSTANCIA DE LOS RECURSOS GRAFICOS Y VARIABLES GLOBALES USADAS PARA CREAR LA CUENTA
    Button consulta;
    EditText cuenta;
    TextView saldo;
    DatabaseReference mDatabase;
    ImageView limpiar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        // COMUNICACIÓN CON LA PARTE GRAFICA
        cuenta=findViewById(R.id.numerodecuenta);
        consulta=findViewById(R.id.consultar);
        saldo=findViewById(R.id.consultasaldo);
        limpiar=findViewById(R.id.limpiar);
        ///////////////////////////////////////////
        // EVENTO DE CLICK PARA BORRAR EL TEXTO DE LOS EDIT TEXT
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuenta.setText("");
            }
        });
        // EVENTO CLICK PARA EJECUTAR LA FUNCIÓN CONSULTA SALDO, QUE MEDIANTE LA CUENTA SOLICITADA MOSTRARA EL VALOR ACTUAL DE LA CUENTA
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultasaldo();
            }
        });
        // COMUNICACIÓN CON EL NODO PRINCIPAL CREADA EN FIREBASE DATA BASE
        mDatabase= FirebaseDatabase.getInstance().getReference();


    }

    private void consultasaldo() {
        //IDENTIFICAMOS EL NUMERO DE CUENTA QUE SE SOLITICA INFORMACIÓN
        String numerocuenta=cuenta.getText().toString();
        //ENTRAMOS A LA BASE DE DATOS PARA SOLICITAR EL SALDO DE LA CUENTA
        mDatabase.child("Users").child(numerocuenta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // SI LA CUENTA EXISTE TRAEMOS EL SALDO DE LA CUENTA
                if(snapshot.exists()){
                ValorCuenta valorCuenta = snapshot.getValue(ValorCuenta.class);
                String valor1 = valorCuenta.getValor();
                // IMPRIMIMOS EL SALDO DE LA CUENTA SOLICITADA
                saldo.setVisibility(View.VISIBLE);
                saldo.setText("EL SALDO DISPONIBLE DE SU CUENTA ES:"+" "+valor1);
                }else{
                    Toast.makeText(Consultar.this,"ESTA CUENTA NO EXISTE",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}