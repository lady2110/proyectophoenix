package com.fenix.proyectofenix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView log, sing;
    private EditText email, password;
    private Button enter;
    boolean resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //poner el icono  en action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        email = findViewById(R.id.txtemail);
        password = findViewById(R.id.txtpassword);
        log = findViewById(R.id.btnlogin);
        sing = findViewById(R.id.btnsing);
        enter = findViewById(R.id.btnenter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsing:
                sing = (TextView) findViewById(R.id.btnsing);
                sing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Siguiente = new Intent(MainActivity.this, Registro.class);
                        startActivity(Siguiente);
                    }
                });
                break;
            case R.id.btnenter:
                if(validate(email.getText().toString(), password.getText().toString())){
                    Button enter;
                    enter = (Button) findViewById(R.id.btnenter);
                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent Siguiente = new Intent(MainActivity.this, Menu.class);
                            startActivity(Siguiente);
                        }
                    });
                }

                break;
        }
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

    public boolean passwordValidate(String pwd){
        if(pwd.isEmpty() || pwd == ""){
            Toast.makeText(this, "Password no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public boolean validate(String email, String password){
        if(!email(email) || !passwordValidate(password)) {
            return false;
        }
        return true;
    }
}