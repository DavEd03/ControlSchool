package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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

import java.util.ArrayList;

public class calificaciones extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView lstCalificaciones;
    String userId;
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
        arrayList=new ArrayList<>();
        lstCalificaciones=findViewById(R.id.LstCalificaciones);

    }
    public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
}
private void obtenerCalificacion(){
        try{
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference ruta= database.getReference("Alumnos").child("DGS").child("10").child("");
            ruta.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    m
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch(Exception e){
            Toast.makeText(calificaciones.this, "Error en :"+e.getMessage().toString(),Toast.LENGTH_LONG).show();
    }
}

}