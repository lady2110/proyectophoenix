package com.fenix.proyectofenix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fenix.proyectofenix.modProducto.Productos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivityCarrito extends AppCompatActivity {

    private FirebaseAuth auth;
    private String CurrentUserId;
    private DatabaseReference UserRef, ProductoRef;




    private RecyclerView recyclerView_listar;
    RecyclerView.LayoutManager layoutManager;
    private Button siguiente;
    private TextView totalPrecio, mensaje;

    private double precioTotalID = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrito);


        auth = FirebaseAuth.getInstance();
        CurrentUserId = auth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        ProductoRef = FirebaseDatabase.getInstance().getReference().child("Productos");


        recyclerView_listar=findViewById(R.id.carrito_lista);
        recyclerView_listar.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView_listar.setLayoutManager(layoutManager);

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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        FirebaseRecyclerOptions<Productos> options = new FirebaseRecyclerOptions.Builder<Productos>().setQuery(ProductoRef, Productos.class).build();

        FirebaseRecyclerAdapter<Productos, ProductoViewHolder> adapter = new FirebaseRecyclerAdapter<Productos, ProductoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductoViewHolder productoViewHolder, int i, @NonNull Productos productos) {

                productoViewHolder.productoNom.setText(productos.getNombre());
                productoViewHolder.productoDesc.setText("Descri: "+productos.getDescripcion());
                productoViewHolder.productoCant.setText("Cantidad: "+productos.getCantidad());
                productoViewHolder.productoPrecio.setText("Precio: "+productos.getPrecio_venta());

                Picasso.get().load(productos.getImagen()).into(productoViewHolder.productoImg);
                productoViewHolder.productoImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityCarrito.this,ProductoViewHolder.class);
                        intent.putExtra("pid",productos.getId());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_menub,parent,false);
                ProductoViewHolder productoViewHolder = new ProductoViewHolder(view);
                return productoViewHolder;
            }
        };
        recyclerView_listar.setAdapter(adapter);
        adapter.startListening();
    }
}