package com.fenix.proyectofenix;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    TextView log, sing;
    EditText name, email, password, phone;
    Button enter;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);

        //poner el icono  en action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        name = findViewById(R.id.txtname);
        email = findViewById(R.id.txtemail2);
        password = findViewById(R.id.txtpassword2);
        log = findViewById(R.id.btnlogin);
        sing = findViewById(R.id.btnsing);
        phone = findViewById(R.id.txtphone);
        enter = findViewById(R.id.btnenter);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                log = (TextView) findViewById(R.id.btnlogin);
                log.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Siguiente = new Intent(Registro.this, MainActivity.class);
                        startActivity(Siguiente);
                    }
                });
                break;
            case R.id.btnenter:
                String nameField = name.getText().toString().trim();
                String emailField = email.getText().toString().trim();
                String pwdField = password.getText().toString().trim();
                String phoneFiedl = phone.getText().toString().trim();


                if (validate(nameField, emailField, pwdField, phoneFiedl)) {
                    Button enter;
                    enter = (Button) findViewById(R.id.btnenter);

                    mAuth.createUserWithEmailAndPassword(emailField, pwdField)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Registro existoso", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent Siguiente = new Intent(Registro.this, Menu.class);
                            startActivity(Siguiente);
                        }
                    });
                }

                break;
        }
    }
    public boolean valideatename(String name){
        if(name.isEmpty() || name == ""){
            Toast.makeText(this, "Nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validacion(String email) {
        Pattern partten = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        Matcher mather = partten.matcher(email);
        if (!mather.find()) {
            Toast.makeText(this, "Correo con caracteres no permitidos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean email(String fieldEmail){
        if(fieldEmail.isEmpty() || fieldEmail == ""){
            Toast.makeText(this, "Email no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!validacion(fieldEmail)){
            return false;
        }
        return true;
    }

    public boolean validacionpas(String pas){
        Pattern partten = Pattern.compile("^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$");
        Matcher mather = partten.matcher(pas);
        if (!mather.find()) {
            Toast.makeText(this, "Contraseña con caracteres no permitidos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean password(String cont){
        if(cont.isEmpty() || cont == ""){
            Toast.makeText(this,"La contraseña no puede estar vacia", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!validacionpas(cont)){
            return false;
        }
        return true;
    }
    public boolean telephone(String phone){
        if(phone.isEmpty() || phone==" "){
            Toast.makeText(this, "Teléfono no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validate(String name, String email, String password, String phone){
        if(!valideatename(name) || !email(email) || !password(password) || !telephone(phone)) {
            return false;
        }
        return true;
    }
}