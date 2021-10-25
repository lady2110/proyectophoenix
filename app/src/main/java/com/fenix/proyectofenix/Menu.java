package com.fenix.proyectofenix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



    }

    public void haciaCrearProducto(View view){
        Intent intent =new Intent(this,agregarProductoActivity.class);
        startActivity(intent);
    }
}