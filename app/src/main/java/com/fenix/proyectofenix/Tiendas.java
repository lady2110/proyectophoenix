package com.fenix.proyectofenix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenix.proyectofenix.model.Tienda;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tiendas extends AppCompatActivity implements View.OnClickListener{

    private List<Tienda> ListTienda = new ArrayList<Tienda>();
    ArrayAdapter<Tienda> arrayAdapterTienda;

    private EditText Nombre,Nit,Direccion,Telefono,Ciudad,Correo,buscar;
    private Button add,update,delete,clear;
    private ListView listV_tiendas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Tienda TiendaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendas);
        Nombre = findViewById(R.id.Tname);
        Nit = findViewById(R.id.Tnit);
        Direccion = findViewById(R.id.Tdireccion);
        Telefono= findViewById(R.id.Ttelefono);
        Ciudad= findViewById(R.id.Tciudad);
        Correo= findViewById(R.id.Temail);
        buscar= findViewById(R.id.txtfiltro);
        listV_tiendas = findViewById(R.id.datosTiendas);
        inicializarFirebase();
        ListarDatos();

        listV_tiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TiendaSelected = (Tienda) parent.getItemAtPosition(position);
                Nombre.setText(TiendaSelected.getNombre());
                Nit.setText(TiendaSelected.getNit());
                Direccion.setText(TiendaSelected.getDireccion());
                Telefono.setText(TiendaSelected.getTelefono());
                Ciudad.setText(TiendaSelected.getCiudad());
                Correo.setText(TiendaSelected.getCorreo());
            }
        });
    }

    private void ListarDatos() {
        databaseReference.child("Tienda").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListTienda.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Tienda tienda = objSnapshot.getValue(Tienda.class);
                    ListTienda.add(tienda);

                    arrayAdapterTienda = new ArrayAdapter<Tienda>(Tiendas.this, R.layout.list_item_tiendas, ListTienda);
                    listV_tiendas.setAdapter(arrayAdapterTienda);

                    buscar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            arrayAdapterTienda.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        String nombret = Nombre.getText().toString();
        String nitt = Nit.getText().toString();
        String direcciont = Direccion.getText().toString();
        String telefonot = Telefono.getText().toString();
        String ciudadt = Ciudad.getText().toString();
        String correot = Correo.getText().toString();
        switch (view.getId()) {
            case R.id.btnadd:
                if(nombret.equals("")||nitt.equals("")||direcciont.equals("")||telefonot.equals("")
                ||ciudadt.equals("")||correot.equals("")){
                    Validation();
                }else {
                    Tienda tienda = new Tienda();
                    tienda.setId(UUID.randomUUID().toString());
                    tienda.setNombre(nombret);
                    tienda.setNit(nitt);
                    tienda.setDireccion(direcciont);
                    tienda.setTelefono(telefonot);
                    tienda.setCiudad(ciudadt);
                    tienda.setCorreo(correot);
                    databaseReference.child("Tienda").child(tienda.getId()).setValue(tienda);
                    Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_LONG).show();
                    limpiarcampos();
                }
                break;
                    case R.id.btnupdate:
                        if(nombret.equals("")||nitt.equals("")||direcciont.equals("")||telefonot.equals("")
                                ||ciudadt.equals("")||correot.equals("")){
                            Validation();
                        }else {
                            Tienda tienda = new Tienda();
                            tienda.setId(TiendaSelected.getId());
                            tienda.setNombre(Nombre.getText().toString().trim());
                            tienda.setNit(Nit.getText().toString().trim());
                            tienda.setDireccion(Direccion.getText().toString().trim());
                            tienda.setTelefono(Telefono.getText().toString().trim());
                            tienda.setCiudad(Ciudad.getText().toString().trim());
                            tienda.setCorreo(Correo.getText().toString().trim());
                            databaseReference.child("Tienda").child(tienda.getId()).setValue(tienda);
                            Toast.makeText(this, "Actualizado", Toast.LENGTH_LONG).show();
                            limpiarcampos();
                        }
                        break;
                    case R.id.btndelete:
                        if(nombret.equals("")||nitt.equals("")||direcciont.equals("")||telefonot.equals("")
                                ||ciudadt.equals("")||correot.equals("")){
                            Validation();
                        }else {
                            Tienda tienda1 = new Tienda();
                            tienda1.setId(TiendaSelected.getId());
                            databaseReference.child("Tienda").child(tienda1.getId()).removeValue();
                            Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
                            limpiarcampos();
                        }
                        break;
                    case R.id.btnclear:
                        limpiarcampos();
                        break;
                        }
    }

    private void mostraralerta() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta")
                .setMessage("¿Desea Actulizar la tienda?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("mensaje"," se cancelo");
            }
        });
    }

    private void limpiarcampos() {
        Nombre.setText("");
        Nit.setText("");
        Direccion.setText("");
        Telefono.setText("");
        Ciudad.setText("");
        Correo.setText("");

    }

    private void Validation() {
        String nombret = Nombre.getText().toString();
        String nitt = Nit.getText().toString();
        String direcciont = Direccion.getText().toString();
        String telefonot = Telefono.getText().toString();
        String ciudadt = Ciudad.getText().toString();
        String correot = Correo.getText().toString();

        if(nombret.equals("")){
            Nombre.setError("Requerido");
        }else if(nitt.equals("")){
           Nit.setError("Requerido");
        }else if(direcciont.equals("")){
            Direccion.setError("Requerido");
        }else if(telefonot.equals("")){
            Telefono.setError("Requerido");
        }else if(ciudadt.equals("")){
            Ciudad.setError("Requerido");
        }else if(correot.equals("")){
            Correo.setError("Requerido");
        }
    }
}