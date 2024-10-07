package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText matric;
    EditText pass;
    Button Access;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            matric=(EditText)findViewById(R.id.matricula);
            pass=(EditText)findViewById(R.id.password);
            Access=(Button) findViewById(R.id.acceder);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;

            });
        }
    /* String uid;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    /*protectedid onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            uid= currentUser.getUid();
            Intent i = new Intent(MainActivity.this, Menu.class);
            i.putExtra("nUsuario",uid);
            startActivity(i);
        } else {
            Toast.makeText(this, "Por favor inicie sesión con su cuenta", Toast.LENGTH_LONG).show();
        }
    }
    }
    public void Acceder (View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}