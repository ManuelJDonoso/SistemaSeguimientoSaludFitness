/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class Cliente {

    private String dni, nombre, apellido1, apellido2, sexo, altura, dirFoto,
            direccion, poblacion, cp, telefono, PesoIdeal, Anotaciones,email;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaAlta;

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellido1, String apellido2, String sexo, String altura,  String dirFoto, String direccion, 
            String poblacion, String cp, String telefono, String PesoIdeal, 
            String Anotaciones, LocalDate fechaNacimiento, LocalDateTime fechaAlta,String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sexo = sexo;
        this.altura = altura;

        this.dirFoto = dirFoto;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.cp = cp;
        this.telefono = telefono;

        this.PesoIdeal = PesoIdeal;
        this.Anotaciones = Anotaciones;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.email=email;

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getApellidos(){
        return apellido1+" "+apellido2;
    }
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }




    public String getDirFoto() {
        return dirFoto;
    }

    public void setDirFoto(String dirFoto) {
        this.dirFoto = dirFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

   
    public String getPesoIdeal() {
        return PesoIdeal;
    }

    public void setPesoIdeal(String PesoIdeal) {
        this.PesoIdeal = PesoIdeal;
    }

    public String getAnotaciones() {
        return Anotaciones;
    }

    public void setAnotaciones(String Anotaciones) {
        this.Anotaciones = Anotaciones;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }



   

    public String getNombreCompleto() {
        return nombre + " " + apellido1 + " " + apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", sexo=" + sexo + ", altura=" + altura + ", dirFoto=" + dirFoto + ", direccion=" + direccion + ", poblacion=" + poblacion + ", cp=" + cp + ", telefono=" + telefono + ", PesoIdeal=" + PesoIdeal + ", Anotaciones=" + Anotaciones + ", email=" + email + ", fechaNacimiento=" + fechaNacimiento + ", fechaAlta=" + fechaAlta + '}';
    }



  

}
