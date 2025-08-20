/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models;

import java.time.LocalDateTime;

/**
 *
 * @author donpe
 */
public class DatosToma {

    String dni, peso, imc, dirFoto, grasac, proteina, metabolismoV, grasaV;
    
    private LocalDateTime fechaToma, proximaCita;

    public DatosToma() {
    }

    
    public DatosToma(String dni, String peso, String imc, String dirFoto, String grasac, String proteina, String metabolismoV, String grasaV, LocalDateTime fechaToma, LocalDateTime proximaCita) {
        this.dni = dni;
        this.peso = peso;
        this.imc = imc;
        this.dirFoto = dirFoto;
        this.grasac = grasac;
        this.proteina = proteina;
        this.metabolismoV = metabolismoV;
        this.grasaV = grasaV;
       
        this.fechaToma = fechaToma;
        this.proximaCita = proximaCita;
    }

    
    
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

  

    public LocalDateTime getFechaToma() {
        return fechaToma;
    }

    public void setFechaToma(LocalDateTime fechaToma) {
        this.fechaToma = fechaToma;
    }

    public LocalDateTime getProximaCita() {
        return proximaCita;
    }

    public void setProximaCita(LocalDateTime proximaCita) {
        this.proximaCita = proximaCita;
    }

    @Override
    public String toString() {
        return "DatosToma{" + "dni=" + dni + ", peso=" + peso + ", imc=" + imc + ", dirFoto=" + dirFoto + ", grasac=" + grasac + ", proteina=" + proteina + ", metabolismoV=" + metabolismoV + ", grasaV=" + grasaV + ", fechaToma=" + fechaToma + ", proximaCita=" + proximaCita + '}';
    }
    
    
}
