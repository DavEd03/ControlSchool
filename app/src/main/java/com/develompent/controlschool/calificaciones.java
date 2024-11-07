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
    String userId, usercorreo;
    private TextView edtMat1, edtMat2, edtMat3, edtMat4, edtMat5, edtMat6, edtMat7;
    private DatabaseReference databaseReference;
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
        edtMat1 = findViewById(R.id.materia1);
        edtMat2 = findViewById(R.id.materia2);
        edtMat3 = findViewById(R.id.materia3);
        edtMat4 = findViewById(R.id.materia4);
        edtMat5 = findViewById(R.id.materia5);
        edtMat6 = findViewById(R.id.materia6);
        edtMat7 = findViewById(R.id.materia7);


        mAuth=FirebaseAuth.getInstance();
        Bundle datos= getIntent().getExtras();
        userId= datos.getString("nUsuario");

        databaseReference = FirebaseDatabase.getInstance().getReference("Alumnos").child("DGS").child("10").child("");


    }
    @Override
    protected void onStart() {
        super.onStart();

        if (databaseReference != null) {
            // Escuchar los datos de Firebase
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Leer los datos básicos
                    String MAT1 = dataSnapshot.child("nombre").getValue(String.class);
                    String MAT2 = dataSnapshot.child("edad").getValue(String.class);
                    String MAT3 = dataSnapshot.child("imc").getValue(String.class);
                    String MAT4 = dataSnapshot.child("talla").getValue(String.class);
                    String MAT5 = dataSnapshot.child("enfermedades").getValue(String.class);
                    String MAT6 = dataSnapshot.child("peso").getValue(String.class);
                    String MAT7 = dataSnapshot.child("estatura").getValue(String.class);


                    // Rellenar los EditText con los datos obtenidos
                    // editTextNombre.setText(nombre);
                    edtMat1.setText(MAT1);
                    edtMat2.setText(MAT2);
                    edtMat3.setText(MAT3);
                    edtMat4.setText(MAT4);
                    edtMat5.setText(MAT5);
                    edtMat6.setText(MAT6);
                    edtMat7.setText(MAT7);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(calificaciones.this, "Error al obtener la información del usuario, intente más tarde", Toast.LENGTH_SHORT).show();

                }
            }
            );
            }
        }public void MenuBack(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    }
