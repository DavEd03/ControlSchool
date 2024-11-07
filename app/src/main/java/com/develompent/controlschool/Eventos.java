package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.HashMap;

public class Eventos extends AppCompatActivity {

    private ArrayList<String> arraList;
    private ListView lstEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eventos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        arraList=new ArrayList<>();
        lstEventos=findViewById(R.id.lstEventos);
    }
    @Override
    protected void onStart(){
        super.onStart();
        obtenerEventos();
    }
    public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    private void obtenerEventos(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ruta=database.getReference("Eventos");
        ruta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arraList.clear();
                for (DataSnapshot variables:snapshot.getChildren()){
                    HashMap deventos=(HashMap) variables.getValue();
                    String Titulo=String.valueOf(deventos.get("Titulo").toString());
                    String Descripcion=String.valueOf(deventos.get("Descripcion").toString());
                    String Fecha=String.valueOf(deventos.get("Fecha").toString());
                    arraList.add("Evento:"+Titulo+" Descripción: "+Descripcion+" Fecha: "+Fecha);

                }
                ArrayAdapter<String> adaptador=new ArrayAdapter<>(getBaseContext(),
                        android.R.layout.simple_expandable_list_item_1,arraList);
                lstEventos.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}