package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doc extends AppCompatActivity {
    String userId,matricula;
    private CheckBox ch1,ch2,ch3,ch4,ch5;
    private DatabaseReference databaseReference,databaseReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle datos= getIntent().getExtras();
        userId= datos.getString("nUsuario");
        ch1=findViewById(R.id.DocBox1);
        ch2=findViewById(R.id.DocBox2);
        //Checkbox
        ch3=findViewById(R.id.DocBox3);
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Cuentas").child(userId);
        if (databaseReference2 != null) {
            // Escuchar los datos de Firebase
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Leer los datos básicos
                    matricula = dataSnapshot.child("Matricula").getValue(String.class);
                    obtener_docs();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Doc.this, "Error al obtener la información del usuario, intente más tarde", Toast.LENGTH_SHORT).show();
                }
            }
            );
        }
    }
    public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    private void obtener_docs(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Expedientes").child(matricula);
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        int index = 1; // Para llevar un conteo de los EditText
                        // Iteramos sobre cada hijo de U1
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String valorElemento = childSnapshot.getValue(String.class); // Valor del elemento

                            // Dependiendo del índice, marcamos los CheckBox correspondientes
                            switch (index) {
                                case 1:
                                    ch1.setChecked("SI".equals(valorElemento)); // Marca el CheckBox si el valor es "SI"
                                    break;
                                case 2:
                                    ch2.setChecked("SI".equals(valorElemento)); // Marca el CheckBox si el valor es "SI"
                                    break;
                                case 3:
                                    ch3.setChecked("SI".equals(valorElemento)); // Marca el CheckBox si el valor es "SI"
                                    break;

                            } index++;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Doc.this,"Error en la leída de la base de datos",Toast.LENGTH_LONG).show();
                    }
                });

    }}
}