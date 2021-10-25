package com.fenix.proyectofenix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityCarrito extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button siguiente;
    private TextView totalPrecio, mensaje;

    private double precioTotalID = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrito);

        recyclerView=findViewById(R.id.carrito_lista);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        siguiente=findViewById(R.id.siguiente);
        totalPrecio=findViewById(R.id.precio_total);
        mensaje=findViewById(R.id.mensaje);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityCarrito.this,confirmaOrden.class);
                intent.putExtra("Total", String.valueOf(precioTotalID));
                startActivity(intent);
                finish();
            }
        });
    }
}