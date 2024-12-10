package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.HashMap;
public class Eventos extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> arraList;
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
        arraList = new ArrayList<>();
        lstEventos = findViewById(R.id.lstEventos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        obtenerEventos();
    }

    public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    private void obtenerEventos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ruta = database.getReference("Eventos");
        ruta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arraList.clear();
                for (DataSnapshot variables : snapshot.getChildren()) {
                    HashMap<String, String> deventos = (HashMap) variables.getValue();
                    String Titulo = String.valueOf(deventos.get("Titulo"));
                    String Descripcion = String.valueOf(deventos.get("Descripcion"));
                    String Fecha = String.valueOf(deventos.get("Fecha"));

                    // Crear un HashMap con los datos del evento
                    HashMap<String, String> evento = new HashMap<>();
                    evento.put("Titulo", Titulo);
                    evento.put("Descripcion", Descripcion);
                    evento.put("Fecha", Fecha);

                    // Agregar el evento a la lista
                    arraList.add(evento);
                }

                // Usar un ArrayAdapter personalizado con el nuevo layout
                // Usar getBaseContext() o this en una actividad
                CustomAdapter adaptador = new CustomAdapter(Eventos.this, arraList); // 'this' se refiere a la actividad
                lstEventos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });
    }


    // Adapter personalizado
    public class CustomAdapter extends ArrayAdapter<HashMap<String, String>> {
        // Modificar el constructor para recibir Context en lugar de ValueEventListener
        public CustomAdapter(Eventos context, ArrayList<HashMap<String, String>> eventos) {
            super(context, 0, eventos); // Aquí pasamos un contexto adecuado (la actividad)
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_evento, parent, false);
            }

            HashMap<String, String> evento = getItem(position);

            if (evento != null) {
                TextView txtTitulo = convertView.findViewById(R.id.txtTitulo);
                TextView txtDescripcion = convertView.findViewById(R.id.txtDescripcion);
                TextView txtFecha = convertView.findViewById(R.id.txtFecha);

                // Asignar los valores a los TextViews
                txtTitulo.setText(evento.get("Titulo"));
                txtDescripcion.setText(evento.get("Descripcion"));
                txtFecha.setText(evento.get("Fecha"));
            }

            return convertView;
        }
    }
}
