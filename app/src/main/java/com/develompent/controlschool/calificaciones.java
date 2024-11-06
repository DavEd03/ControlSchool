package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class calificaciones extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView lstCalificaciones;
    private TextView Carreras, CUATRIS, Nombre;
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
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("nUsuario");


    }FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference cuentasref = database.getReference("Cuentas");
    DatabaseReference alumnosref = database.getReference("Alumnos");

    @Override
    protected void onStart() {
        super.onStart();
        obtenerCalificacion();
    }
    public void getUserData( String userId  ){
        cuentasref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String carrera = snapshot.child("Carrera").getValue(String.class);
                    String cuatrimestre = snapshot.child("Cuatri").getValue(String.class);
                    String matricula = snapshot.child("Matricula").getValue(String.class);

                    getAlumnoData(carrera,cuatrimestre,matricula);
                }else {
                    Log.e("Firebase","No se encontro el usuario");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase","Error al recuperar los datos",error.toException());
            }
        });
    }
    public void getAlumnoData(String carrera,String cuatrimestre,String matricula){
        alumnosref.child(carrera).child(cuatrimestre).child(matricula).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Map<String,Object> alumno = (Map<String,Object>)snapshot.getValue();
                    Log.d("Firebase","Datos de Alumno: "+alumno);
                }else {
                    Log.e("Firebase","No se encontro el alumno");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase","Error en la consulta",error.toException());
            }
        });

    }

    private void obtenerCalificacion(){
        try{
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference ruta= database.getReference("Alumnos").child("DGS").child("10");
            ruta.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();for (DataSnapshot variables : snapshot.getChildren()) {
                        // Obtener el valor como un Object
                        Object value = variables.getValue();

                        // Verificar si el valor es un HashMap
                        if (value instanceof HashMap) {
                            HashMap calif = (HashMap) value; // Conversión segura a HashMap
                            String Materia = String.valueOf(calif.get("Calificaciones")); // No es necesario toString() aquí

                            arrayList.add("Materia: " + Materia + ", calificacion: ");
                        }else if (value instanceof String) {
                            // Manejar el caso donde elvalor es un String
                            String materia = (String) value; // Conversión segura a String
                            arrayList.add("Materia: " + materia + ", calificacion: ");
                        } else {
                            // Manejar otros tipos de datos o registrar un error
                            Log.e("Firebase", "Tipo de dato inesperado: " + value.getClass().getSimpleName());
                        }
                    }

                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(getBaseContext(),
                            android.R.layout.simple_expandable_list_item_1, arrayList);
                    lstCalificaciones.setAdapter(adaptador);
                }

                        @Override
                public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(calificaciones.this,"Error al recuperar los datos",Toast.LENGTH_LONG).show();


                        }
            });

        }catch(Exception e){
            Toast.makeText(calificaciones.this, "Error en :"+e.getMessage().toString(),Toast.LENGTH_LONG).show();
    }
}

public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

}