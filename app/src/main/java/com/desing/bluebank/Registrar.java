package com.desing.bluebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.Users;

public class Registrar extends AppCompatActivity {
    // INNSTANCIA DE LOS RECURSOS GRAFICOS Y VARIABLES GLOBALES USADAS PARA CREAR LA CUENTA
    TextView cuenta;
    Button generar,register;
    EditText nombre,valorinicial;
    String resultado;
    DatabaseReference mDatabase;
    ImageView limpiar;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        // COMUNICACIÓN CON LA PARTE GRAFICA
        cuenta=findViewById(R.id.cuenta);
        generar=findViewById(R.id.generar);
        nombre=findViewById(R.id.nombre);
        valorinicial=findViewById(R.id.valori);
        register=findViewById(R.id.crear);
        limpiar=findViewById(R.id.limpiar);
        ///////////////////////////////////////////
        // COMUNICACIÓN CON EL NODO PRINCIPAL CREADA EN FIREBASE DATA BASE
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //////////////////////////////////////////////

        // EVENTO DE CLICK PARA BORRAR EL TEXTO DE LOS EDIT TEXT Y TEXT VIEW
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setText("");
                valorinicial.setText("");
                cuenta.setText("");
            }
        });
        // EVENTO CLICK PARA EJECUTAR LA FUNCION REGISTRAR
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

        // EVENTO CLICK PARA GENERAR UNA CUENTA ENTRE UN MILLON Y DIEZ MILLONES
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aleatorio=0;
                aleatorio= (int) (Math.random()*(10000000-1000000)+1000000);
                resultado=String.valueOf(aleatorio);
                cuenta.setText(resultado);

            }
        });
    }

    private void registeruser() {
        String name=nombre.getText().toString();
        String valorcuenta=valorinicial.getText().toString();
        // SI LOS CAMPOS TE TEXTO NO ESTAN VACIOS EJECUTAMOS LA FUNCION GUARDAR USUARIO Y SI ESTAN VACIOS NO SE EJECUTARA NINGUNA FUNCIÓN
        if(!name.isEmpty()&&!valorcuenta.isEmpty()&&!resultado.isEmpty()){
                saveUser(name,valorcuenta,resultado);
        }else{
            Toast.makeText(Registrar.this,"DEBE INGRESAR TODOS LOS DATOS PARA PODER CREAR UN CUENTA",Toast.LENGTH_SHORT).show();

        }

    }

    private void saveUser(String name, String valorcuenta, String resultado) {
        // SE DECLARA UN NUEVO OBJETO DE LA CLASE USERS DONDE SE GUARDAR LOS DATOS DEL USUARIO Y SERAN SUBIDOS A FIREBASE DATA BASE
        Users users=new Users();
        users.setNombre(name);
        users.setValor(valorcuenta);
        users.setNumeroCuenta(resultado);
        // LLAMADO LA BASE DE DATOS DONDE SE DECLARA QUE LOS VALORES SEAN GUARDARDOS EN UN NODO LLAMADA USERS Y DENTRO DE EL ESTARAN LAS CUENTAS CREADAS
        mDatabase.child("Users").child(resultado).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // SI SE COMPLETA CORRECTAMENTE LA CREACCION SE DATOS SE INFORMA AL USUARIO MEDIANTE UN TOAST, SI NO SE COMPLETA SE INFORMA  DE IGUAL MANERA
                if(task.isSuccessful()){
                    Toast.makeText(Registrar.this,"CUENTA CREADA EXITOSAMENTE",Toast.LENGTH_SHORT).show();
                   // Intent i=new Intent(Registrar.this,MainActivity.class);
                    //startActivity(i);
                }else{
                    Toast.makeText(Registrar.this,"NO SE LOGRO CREAR LA CUENTA",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}