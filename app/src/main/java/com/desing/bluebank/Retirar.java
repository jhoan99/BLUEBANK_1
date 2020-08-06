package com.desing.bluebank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Retirar extends AppCompatActivity {
    // INNSTANCIA DE LOS RECURSOS GRAFICOS Y VARIABLES GLOBALES USADAS PARA CREAR LA CUENTA
    EditText cuenta,valorR;
    Button confirmar;
    DatabaseReference mDatabase;
    String valorfinal;
    String cuentaUser,Nombre,numerocuenta;
    String valor1;
    ImageView limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirar);
        // COMUNICACIÓN CON LA PARTE GRAFICA
        cuenta=findViewById(R.id.numerodecuenta);
        valorR=findViewById(R.id.valorR);
        confirmar=findViewById(R.id.retirar);
        limpiar=findViewById(R.id.limpiar);
        ///////////////////////////////////////////
        // EVENTO DE CLICK PARA BORRAR EL TEXTO DE LOS EDIT TEXT
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuenta.setText("");
                valorR.setText("");
            }
        });
        // EVENTO CLICK PARA EJECUTAR LA FUNCIÓN VALOR A RETIRAR, QUE RESTARA EL VALOR ACTUAL EN LA CUENTA CON EL VALOR RETIRADO
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Valoraretirar();
            }
        });
        // COMUNICACIÓN CON EL NODO PRINCIPAL CREADA EN FIREBASE DATA BASE
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }
    private void Valoraretirar(){
        // SE SOLITICA EL NUMERO CUENTA AL QUE SE DESEA RETIRAR
        cuentaUser=cuenta.getText().toString();
        // SI LA CUENTA INGRESADA EN DIFERENTE DE VACIO SE EJECUTARA LA SIGUIENTE ACCIÓN
        if(!cuentaUser.isEmpty()){
            // SE COMUNICARA CON LA BASE DATOS Y TRAERA LA INFORMACION DE LA CUENTA SOLICITADA ANTERIORMENTE
        mDatabase.child("Users").child(cuentaUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // SI LA CUENTA EXISTE SE SEGUIRA CON EL PROCESO
                if(snapshot.exists()) {
                    //RECOLECCION DE DATOS EN FIREBASE CON LA CLASE VALOR CUENTA
                    ValorCuenta valorCuenta = snapshot.getValue(ValorCuenta.class);
                    valor1 = valorCuenta.getValor();
                    Nombre = valorCuenta.getNombre();
                    numerocuenta = valorCuenta.getNumeroCuenta();

                    //CONVERSIÓN DE STRING A ENTERO PARA RESTAR EL VALOR A RETIRAR
                    // VALOR INPUT ES EL VALOR QUE DESEA RETIRAR
                    String ValorInput = valorR.getText().toString();
                    int valor3 = Integer.parseInt(ValorInput);
                    int valor2 = Integer.parseInt(valor1);

                    //VALIDACION PARA VERIFICAR QUE HAYA SALDO DISPONIBLE
                    if(valor3<valor2){
                    int resultado = valor2 - valor3;
                    valorfinal = String.valueOf(resultado);
                        //ACTUALIZACIÓN DE VALOR DE LA CUENTA
                        Users users=new Users();
                        users.setValor(valorfinal);
                        users.setNombre(Nombre);
                        users.setNumeroCuenta(numerocuenta);
                        mDatabase.child("Users").child(numerocuenta).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // SI EL RETIRO FUE EXITOSO SE INFORMARA MEDIANTE UN TOAST Y SI NO ES EXITOSO SE INFORMARA DE IGUAL MANERA
                                if(task.isSuccessful()){
                                    Toast.makeText(Retirar.this,"RETIRO EXITOSO",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Retirar.this,"NO SE LOGRO RETIRAR EL DINERO",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }else{
                        Toast.makeText(Retirar.this,"NO HAY SALDO EN LA CUENTA",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(Retirar.this,"LA CUENTA NO EXISTE",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }else{
            Toast.makeText(Retirar.this,"DEBE INGRESAR UN NUMERO DE CUENTA",Toast.LENGTH_SHORT).show();
        }

    }
}