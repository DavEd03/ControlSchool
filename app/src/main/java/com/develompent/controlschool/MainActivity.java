package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText matric;
    EditText pass;
    Button Access;
    private String uid;
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
    FirebaseAuth auth= FirebaseAuth.getInstance();
    @Override
    protected void onStart() {
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


    public void Acceder (View view){
        try{
            mAuth=FirebaseAuth.getInstance();
            String User= matric.getText().toString().trim();
            String Password= pass.getText().toString().trim();
            if (User.isEmpty()&&Password.isEmpty()){
                Toast.makeText(MainActivity.this, "Por favor vuelve a ingersar los datos", Toast.LENGTH_LONG).show();
            }else{
                mAuth.signInWithEmailAndPassword(User,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent intent = new Intent(MainActivity.this, Menu.class);
                        FirebaseUser currentUser = auth.getCurrentUser();
                        uid= currentUser.getUid();
                        intent.putExtra("nUsuario",uid);
                        startActivity(intent);

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Ocurrió un error en: "+e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Ocurrió un error en: "+e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

    }
}