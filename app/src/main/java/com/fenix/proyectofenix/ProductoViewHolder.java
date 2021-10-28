package com.fenix.proyectofenix;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

public class ProductoViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productoNom, productoDesc, productoPrecio, productoCant;
    public ImageView productoImg;
    public  ItemClickListener listener;

    public ProductoViewHolder(@NonNull View itemView) {
        super(itemView);

        productoNom = (TextView) itemView.findViewById(R.id.producto_nombre);
        productoDesc = (TextView) itemView.findViewById(R.id.producto_descripcion);
        productoPrecio = (TextView) itemView.findViewById(R.id.producto_precio);
        productoCant = (TextView) itemView.findViewById(R.id.producto_cantidad);
        productoImg = (ImageView) itemView.findViewById(R.id.producto_imagen);

    }

    public void setIItemClickListener (ItemClickListener listener){

        this.listener =listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);
    }

}
