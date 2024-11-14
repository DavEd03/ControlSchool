package com.develompent.controlschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Enquestas extends AppCompatActivity {
    private WebView enquest;
    private WebSettings webEnques;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enquestas);
        enquest = findViewById(R.id.encuestas);
        webEnques = enquest.getSettings();
        webEnques.setJavaScriptEnabled(true);
        webEnques.setDomStorageEnabled(true);
        enquest.loadUrl("https://docs.google.com/forms/u/0/d/e/1FAIpQLSfdTePV0rEQIv7KAdeBB6aPjYDtCpleeg92tPV7tJSsvJ6u5Q/viewform?pli=1");
        enquest.setWebViewClient(new WebViewClient());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }
    public void MenuBack(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }



}