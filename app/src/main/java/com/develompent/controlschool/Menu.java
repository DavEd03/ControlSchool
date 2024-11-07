package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu extends AppCompatActivity {
    FirebaseAuth mAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth= FirebaseAuth.getInstance();
        Bundle datos= getIntent().getExtras();

        if(datos != null) {
            userId= datos.getString("nUsuario");

            if (userId == null) {
                // "nUsuario" no existe en el Bundle
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    userId = user.getUid();
                } else {
                    // No se puede obtener el UID, manejar el error
                    showErrorAndRedirect();
                }
            }
        }else{
            FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null) {
                userId = user.getUid();
            }else{
                showErrorAndRedirect();
            }
        }
    }

    public void Acceder(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    public void Calificaciones(View view) {
        Intent intent = new Intent(this, calificaciones.class);
        intent.putExtra("nUsuario",userId);
        startActivity(intent);
    }
    public void Eventos(View view) {
        Intent intent = new Intent(this, Eventos.class);
        startActivity(intent);
    }
    public void Documentacion(View view) {
        Intent intent = new Intent(this, Doc.class);
        intent.putExtra("nUsuario",userId);
        startActivity(intent);
    }

    private void showErrorAndRedirect() {
        // Mostrar un mensaje de error al usuario
        Toast.makeText(this, "Error al obtener la información del usuario. Por favor, intenta iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
        // Redirigir al usuario a la pantalla de inicio de sesión
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Cerrar la actividad actual
    }
    public void cerrarSesion(View v){
        mAuth.signOut();
        Intent ini= new Intent(this,MainActivity.class);
        startActivity(ini);
        finish();
    }

}