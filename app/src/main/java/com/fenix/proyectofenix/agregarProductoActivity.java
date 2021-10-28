package com.fenix.proyectofenix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class agregarProductoActivity extends AppCompatActivity {

    private ImageView imagenPro;
    private EditText nombrePro,descripcionPro,precioPro_compra, precioPro_venta, categoria, cantidad;
    private Button agregarPro;

    private static final int Gallery_Pick = 1;
    private Uri imagenUri;
    private String productoRndonKey, downloadUri;
    private StorageReference ProductoImagenRef;
    private DatabaseReference ProductoRef;
    private ProgressDialog dialog;
    private String Categoria, Nom, Desc, Pre_com,Pre_vent, cant, cate, CurrenDate, CurrenTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        ProductoImagenRef = FirebaseStorage.getInstance().getReference().child("Imagen Producto");
        ProductoRef = FirebaseDatabase.getInstance().getReference().child("Productos");
        imagenPro = findViewById(R.id.imageView5);
        nombrePro =findViewById(R.id.nompro);
        descripcionPro = findViewById(R.id.descripcion_pro);
        precioPro_compra = findViewById(R.id.precio_compra);
        precioPro_venta = findViewById(R.id.precio_venta);
        categoria = findViewById(R.id.categoria);
        cantidad = findViewById(R.id.cantidad);
        agregarPro = findViewById(R.id.btn_agregar_producto);
        dialog = new  ProgressDialog(this);

        imagenPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });
        agregarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarProducto();
            }
        });


    }

    private void ValidarProducto() {
        Nom = nombrePro.getText().toString();
        Desc = descripcionPro.getText().toString();
        Pre_com = precioPro_compra.getText().toString();
        Pre_vent = precioPro_venta.getText().toString();
        cant = cantidad.getText().toString();
        cate = categoria.getText().toString();

        if(imagenUri == null){
            Toast.makeText(this, "Primero agrege una imagen", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Nom)){
            Toast.makeText(this, "Debe ingresar el nombre del producto", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Desc)){
            Toast.makeText(this, "Debe ingresar la descripción del producto", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Pre_com)){
            Toast.makeText(this, "Debe ingresar el precio de compra", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pre_vent)){
            Toast.makeText(this, "Debe ingresar el precio de venta", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cant)){
            Toast.makeText(this, "Debe ingresar la cantidad del producto", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cate)){
            Toast.makeText(this, "Debe ingresar una categoria", Toast.LENGTH_SHORT).show();
        }
        else {
            GuardarInformacionProducto();
        }
    }

    private void GuardarInformacionProducto() {

        dialog.setTitle("Guardando Producto");
        dialog.setMessage("por favor espere mientrar guardamos el producto");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat curreDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        CurrenDate = curreDateFormat.format(calendar.getTime());

        SimpleDateFormat CurrenTimeFormat = new SimpleDateFormat("HH:mm:ss");
        CurrenTime = CurrenTimeFormat.format(calendar.getTime());

        productoRndonKey = CurrenDate + CurrenTime;
        final StorageReference filePach = ProductoImagenRef.child(imagenUri.getLastPathSegment() + productoRndonKey + ".jpg");
        final UploadTask uploadTask = filePach.putFile(imagenUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String mensaje = e.toString();
                Toast.makeText(agregarProductoActivity.this, "Error: "+mensaje, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(agregarProductoActivity.this, "Imagen guarda exitosamente", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadUri = filePach.getDownloadUrl().toString();
                        return filePach.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadUri = task.getResult().toString();
                            Toast.makeText(agregarProductoActivity.this, "Imagen guardada en Firebase", Toast.LENGTH_SHORT).show();
                            GuardarEnFirebase();
                        }else {
                            Toast.makeText(agregarProductoActivity.this,"Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void GuardarEnFirebase() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("pid", productoRndonKey);
        map.put("fecha", CurrenDate);
        map.put("hora", CurrenTime);
        map.put("descripcion", Desc);
        map.put("nombre", Nom);
        map.put("precioCompra", Pre_com);
        map.put("precio_venta", Pre_vent);
        map.put("cantidad",cant);
        map.put("imagen", downloadUri);
        map.put("categoria", Categoria);

        ProductoRef.child(productoRndonKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent =new Intent(agregarProductoActivity.this, Menu.class);
                    startActivity(intent);
                    dialog.dismiss();
                    Toast.makeText(agregarProductoActivity.this,"Solictiud Exitosa", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    String mensaje = task.getException().toString();
                    Toast.makeText(agregarProductoActivity.this,"Error"+mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void AbrirGaleria() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/");
        startActivityForResult(intent,Gallery_Pick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data !=null){
            imagenUri = data.getData();
            imagenPro.setImageURI(imagenUri);
        }
    }
}