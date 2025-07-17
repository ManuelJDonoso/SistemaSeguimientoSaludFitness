/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models;

import java.time.LocalDate;

/**
 *
 * @author donpe
 */
public class Usuario {

    private String dni, nombre, apellido1, apellido2, sexo, altura, peso, imc, dirFoto,
            direccion, poblacion, cp, telefono, grasac, proteina, metabolismoV, grasaV, PesoIdeal, Anotaciones;
    private LocalDate fechaNacimiento;

    public Usuario() {
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

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
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

    public String getGrasac() {
        return grasac;
    }

    public void setGrasac(String grasac) {
        this.grasac = grasac;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getMetabolismoV() {
        return metabolismoV;
    }

    public void setMetabolismoV(String metabolismoV) {
        this.metabolismoV = metabolismoV;
    }

    public String getGrasaV() {
        return grasaV;
    }

    public void setGrasaV(String grasaV) {
        this.grasaV = grasaV;
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

    @Override
    public String toString() {
        return "usuario{" + "dni=" + dni + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", sexo=" + sexo + ", altura=" + altura + ", peso=" + peso + ", imc=" + imc + ", dirFoto=" + dirFoto + ", direccion=" + direccion + ", poblacion=" + poblacion + ", cp=" + cp + ", telefono=" + telefono + ", grasac=" + grasac + ", proteina=" + proteina + ", metabolismoV=" + metabolismoV + ", grasaV=" + grasaV + ", PesoIdeal=" + PesoIdeal + ", Anotaciones=" + Anotaciones + ", fechaNacimiento=" + fechaNacimiento + '}';
    }

}
