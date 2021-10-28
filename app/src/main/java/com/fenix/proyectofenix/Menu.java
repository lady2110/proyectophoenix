package com.fenix.proyectofenix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels=new ArrayList<>();

        slideModels.add(new SlideModel("https://s1.eestatic.com/2020/09/30/omicrono/tecnologia/tecnologia-hardware-imagen_y_sonido_524709269_161351408_1706x960.jpg"));
        slideModels.add(new SlideModel("https://www.wilock.com/wp-content/uploads/2019/02/moto125wilock-2880x1800.jpg"));
        slideModels.add(new SlideModel("https://picsum.photos/id/237/200/300"));
        slideModels.add(new SlideModel("https://picsum.photos/id/0/367/267"));
        slideModels.add(new SlideModel("https://picsum.photos/id/1059/367/267"));

        imageSlider.setImageList(slideModels,true);


    }

    public void prodcuto(View view) {
        Intent Siguiente2 = new Intent(Menu.this, agregarProductoActivity.class);
        startActivity(Siguiente2);
    }

    public void tienda(View view) {
        Intent Siguiente2 = new Intent(Menu.this, Tiendas.class);
        startActivity(Siguiente2);
    }

    public void listar(View view) {
        Intent Siguiente2 = new Intent(Menu.this, MainActivityCarrito.class);
        startActivity(Siguiente2);
    }
}