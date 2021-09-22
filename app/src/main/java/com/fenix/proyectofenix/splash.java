package com.fenix.proyectofenix;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    // Declaración de atributos o variables
    ProgressBar pb;
    int contador=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Método para transición automatica entre activity

        prog();

        TimerTask entrada = new TimerTask() {
            @Override
            public void run() {
                Intent cambio = new Intent(splash.this, MainActivity.class);
                startActivity(cambio);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(entrada,4000);
    }

    //Método para activar y visualizar el ProgressBar
    public void prog()
    {
        pb= (ProgressBar) findViewById(R.id.barra);
        final Timer ft = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                contador++;
                pb.setProgress(contador);
                if(contador ==100)
                    ft.cancel();
            }
        };
        ft.schedule(tt,0,40);
    }
}

