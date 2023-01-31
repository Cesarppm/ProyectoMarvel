package com.example.proyectomarvel;

import static android.Manifest.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ResultadoActivity extends AppCompatActivity {
    String nomP, nomR, descripcion, url;

    ImageView img;
    TextView Personaje, Nombre, Descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Personaje=findViewById(R.id.tvNombre);
        Nombre=findViewById(R.id.tvNombreR);
        Descripcion=findViewById(R.id.tvDescrp);
        img=findViewById(R.id.imageView);

        nomP= getIntent().getExtras().getString("nombreH");
        nomR= getIntent().getExtras().getString("nombreR");
        descripcion= getIntent().getExtras().getString("descripcion");
        url= getIntent().getExtras().getString("foto");


        Personaje.setText(nomP);
        Nombre.setText(nomR);
        Descripcion.setText(descripcion);

        Picasso.get().load(""+url).into(img);


    }
}