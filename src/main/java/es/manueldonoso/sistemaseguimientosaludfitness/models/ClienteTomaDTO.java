/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class ClienteTomaDTO {

    private final StringProperty dni;
    private final StringProperty nombre;
    private final StringProperty apellidos;
    private final StringProperty poblacion;
    private final ObjectProperty<LocalDateTime> proximaCita;

    public ClienteTomaDTO() {
        this.dni = null;
        this.nombre = null;
        this.apellidos = null;
        this.poblacion = null;
        this.proximaCita = null;
    }

    public ClienteTomaDTO(StringProperty dni, StringProperty nombre, StringProperty apellidos, StringProperty poblacion, ObjectProperty<LocalDateTime> proximaCita) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.poblacion = poblacion;
        this.proximaCita = proximaCita;
    }

        public ClienteTomaDTO(String dni, String nombre, String apellidos, String poblacion, LocalDateTime proximaCita) {
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.poblacion = new SimpleStringProperty(poblacion);
        this.proximaCita = new SimpleObjectProperty<>(proximaCita);
    }

    // getters/setters/property...
    public String getDni() {
        return dni.get();
    }

    public StringProperty dniProperty() {
        return dni;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public String getPoblacion() {
        return poblacion.get();
    }

    public StringProperty poblacionProperty() {
        return poblacion;
    }

    public LocalDateTime getProximaCita() {
        return proximaCita.get();
    }

    public ObjectProperty<LocalDateTime> proximaCitaProperty() {
        return proximaCita;
    }

}
