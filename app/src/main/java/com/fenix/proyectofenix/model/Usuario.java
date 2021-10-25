package com.fenix.proyectofenix.model;

public class Usuario {

    public String Idusuario;
    public String Name;
    public String Email;
    public String Password;
    public int Phone;

    public Usuario() {

    }

    public String getIdusuario() {
        return Idusuario;
    }

    public void setIdusuario(String idusuario) {
        Idusuario = idusuario;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return Name;
    }
}
