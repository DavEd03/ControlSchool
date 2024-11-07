package com.develompent.controlschool;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class calificaciones extends AppCompatActivity {
    FirebaseAuth mAuth;
    String userId, apellidos,carrera,matricula,cuatri, nombre;
    private TextView txtCuatri, Carrer,name;
    private DatabaseReference databaseReference,databaseReference2;
    public TextView mat1,mat2,mat3,mat4, mat5, mat6,mat7;
    public TextView cal1, cal2, cal3, cal4, cal5, cal6, cal7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calificaciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth=FirebaseAuth.getInstance();
        Bundle datos= getIntent().getExtras();
        userId= datos.getString("nUsuario");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Cuentas").child(userId);
        txtCuatri=findViewById(R.id.cuatris);
        Carrer=findViewById(R.id.carrer);
        name=findViewById(R.id.name);
        //TXT de los métodos
        mat1=findViewById(R.id.materia1);
        mat2=findViewById(R.id.materia2);
        mat3=findViewById(R.id.materia3);
        mat4=findViewById(R.id.materia4);
        mat5=findViewById(R.id.materia5);
        mat6=findViewById(R.id.materia6);
        mat7=findViewById(R.id.materia7);
        //TXT de las calificaciones
        cal1=findViewById(R.id.cal1);
        cal2=findViewById(R.id.cal2);
        cal3=findViewById(R.id.cal3);
        cal4=findViewById(R.id.cal4);
        cal5=findViewById(R.id.cal5);
        cal6=findViewById(R.id.cal6);
        cal7=findViewById(R.id.cal7);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (databaseReference2 != null) {
            // Escuchar los datos de Firebase
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Leer los datos básicos
                     apellidos = dataSnapshot.child("Apellidos").getValue(String.class);
                     carrera = dataSnapshot.child("Carrera").getValue(String.class);
                     cuatri = dataSnapshot.child("Cuatri").getValue(String.class);
                     matricula = dataSnapshot.child("Matricula").getValue(String.class);
                     nombre=dataSnapshot.child("Nombre").getValue(String.class);

                     txtCuatri.setText(cuatri);
                     Carrer.setText(carrera);
                     name.setText(nombre);
                     obtener_datos();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(calificaciones.this, "Error al obtener la información del usuario, intente más tarde", Toast.LENGTH_SHORT).show();
                }
            }
            );
            }
        }

        private void obtener_datos(){
            databaseReference=FirebaseDatabase.getInstance().getReference("Alumnos").child(carrera).child(cuatri).child("Calificaciones").child(matricula).child("U1");
            //Toast.makeText(calificaciones.this,": "+databaseReference, Toast.LENGTH_LONG).show();
            if(databaseReference != null){
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            int index = 1; // Para llevar un conteo de los EditText
                            // Iteramos sobre cada hijo de U1
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String nombreElemento = childSnapshot.getKey(); // Nombre del elemento
                                String valorElemento = childSnapshot.getValue(String.class); // Valor del elemento

                                // Dependiendo del índice, llenamos los EditText correspondientes
                                switch (index) {
                                    case 1:
                                        mat1.setText(nombreElemento);
                                        cal1.setText(valorElemento);
                                        break;
                                    case 2:
                                        mat2.setText(nombreElemento);
                                        cal2.setText(valorElemento);
                                        break;
                                    case 3:
                                        mat3.setText(nombreElemento);
                                        cal3.setText(valorElemento);
                                        break;
                                    case 4:
                                        mat4.setText(nombreElemento);
                                        cal4.setText(valorElemento);
                                        break;
                                    case 5:
                                        mat5.setText(nombreElemento);
                                        cal5.setText(valorElemento);
                                        break;
                                    case 6:
                                        mat6.setText(nombreElemento);
                                        cal6.setText(valorElemento);
                                        break;
                                    case 7:
                                        mat7.setText(nombreElemento);
                                        cal7.setText(valorElemento);
                                        break;
                                }
                                index++;
                            }
                        } else{
                            Toast.makeText(calificaciones.this, "Error al obtener la información de calificaciones, intente más tarde", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(calificaciones.this, "Error al obtener la información del usuario, intente más tarde", Toast.LENGTH_SHORT).show();
                    }
                }) ;
            }
    }

        public void MenuBack(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    }
