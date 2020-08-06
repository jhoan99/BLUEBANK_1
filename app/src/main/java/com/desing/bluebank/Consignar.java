package com.desing.bluebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.Users;
import models.ValorCuenta;

public class Consignar extends AppCompatActivity {
    // INNSTANCIA DE LOS RECURSOS GRAFICOS Y VARIABLES GLOBALES USADAS PARA CREAR LA CUENTA
    EditText cuenta,valorc;
    Button confirmar;
    DatabaseReference mDatabase;
    String valorfinal;
    String cuentaUser,Nombre,numerocuenta;
    String valor1;
    ImageView limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignar);
        // COMUNICACIÓN CON LA PARTE GRAFICA
        cuenta=findViewById(R.id.numerodecuenta);
        valorc=findViewById(R.id.valorc);
        confirmar=findViewById(R.id.consignar);
        cuentaUser=cuenta.getText().toString();
        limpiar=findViewById(R.id.limpiar);
        ///////////////////////////////////////////
        // EVENTO DE CLICK PARA BORRAR EL TEXTO DE LOS EDIT TEXT
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuenta.setText("");
                valorc.setText("");
            }
        });
        // EVENTO CLICK PARA EJECUTAR LA FUNCIÓN VALOR A CONSGINAR, QUE SUMARA EL VALOR INICIAL CON EL VALOR CONSGINADO
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Valoraconsignar();
            }
        });
        // COMUNICACIÓN CON EL NODO PRINCIPAL CREADA EN FIREBASE DATA BASE
        mDatabase= FirebaseDatabase.getInstance().getReference();


    }
    private void Valoraconsignar(){
        // SE SOLITICA EL NUMERO CUENTA AL QUE SE DESEA CONSGINAR
        cuentaUser=cuenta.getText().toString();
        // SE COMINUCA CON LA BASE DE DATOS PARA PODER OBTENER EL SALDO ACTUAL DE LA CUENTA, SE INGRESA A LA CUENTA QUE SE SOLICITO ANTERIOMENTE
        mDatabase.child("Users").child(cuentaUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // SI LA CUENTA EXISTE SE EJECUTA LA SIGUIENTE ACCIÓN SI NO SE INFORMARA AL USUARIO QUE ESA CUENTA NO EXISTE
                if(snapshot.exists()) {
                    //RECOLECCION DE DATOS EN FIREBASE CON LA AYUDA DE LA CLASE VALOR CUENTA
                        ValorCuenta valorCuenta = snapshot.getValue(ValorCuenta.class);
                        valor1 = valorCuenta.getValor();
                        Nombre = valorCuenta.getNombre();
                        numerocuenta = valorCuenta.getNumeroCuenta();
                      //CONVERSION DE STRING A ENTERO PARA SUMAR EL VALOR CONSGINADO
                        //VALOR INPUT CORRESPONDE AL VALOR QUE SE DESEA CONSGINAR
                        String ValorInput = valorc.getText().toString();
                        int valor2 = Integer.parseInt(valor1);
                        int valor3 = Integer.parseInt(ValorInput);
                        int resultado = valor2 + valor3;
                        valorfinal = String.valueOf(resultado);
                        //ACTUALIZACIÓN DE VALOR DE LA CUENTA
                        Users users=new Users();
                        users.setValor(valorfinal);
                        users.setNombre(Nombre);
                        users.setNumeroCuenta(numerocuenta);
                        mDatabase.child("Users").child(numerocuenta).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // SI LA CONSGINACION SE REALIZO DE MANERA CORRECTA SE INFORMA AL USUARIO MEDIANTE UN TOAST Y SI NO SE LOGRO SE INFORMA DE IGUAL MANERA
                                if(task.isSuccessful()){
                                    Toast.makeText(Consignar.this,"CONSGINACION EXITOSA",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Consignar.this,"NO SE LOGRO LA CONSGINACIÓN",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                }else{
                    Toast.makeText(Consignar.this,"LA CUENTA NO EXISTE",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}