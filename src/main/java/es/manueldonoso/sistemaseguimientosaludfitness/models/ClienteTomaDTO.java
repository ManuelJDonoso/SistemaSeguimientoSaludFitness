/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * DTO (Data Transfer Object) que representa un cliente para la vista de toma de datos.
 * Utiliza propiedades de JavaFX para permitir el enlace directo con controles de la interfaz.
 * 
 * Contiene información básica del cliente, incluyendo:
 * - DNI
 * - Nombre
 * - Apellidos
 * - Población
 * - Próxima cita (LocalDateTime)
 * 
 * Además, expone un getter calculado para mostrar el nombre completo en la vista.
 * 
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class ClienteTomaDTO {

    /** DNI del cliente */
    private final StringProperty dni;
    /** Nombre del cliente */
    private final StringProperty nombre;
    /** Apellidos del cliente */
    private final StringProperty apellidos;
    /** Población del cliente */
    private final StringProperty poblacion;
     /** Fecha y hora de la próxima cita */
    private final ObjectProperty<LocalDateTime> proximaCita;

    /**
     * Constructor por defecto. 
     * Inicializa las propiedades con valores vacíos o null seguros.
     */
    public ClienteTomaDTO() {
        this.dni = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.apellidos = new SimpleStringProperty("");
        this.poblacion = new SimpleStringProperty("");
        this.proximaCita = new SimpleObjectProperty<>(null);
    }
    /**
     * Constructor con parámetros.
     * 
     * @param dni DNI del cliente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param poblacion Población del cliente
     * @param proximaCita Fecha y hora de la próxima cita
     */
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

     /** @return DNI como String */
    public String getDni() {
        return dni.get();
    }

      /** @return propiedad DNI (para bindings en la vista) */
    public StringProperty dniProperty() {
        return dni;
    }

     /** @return nombre como String */
    public String getNombre() {
        return nombre.get();
    }

    /** @return propiedad nombre */
    public StringProperty nombreProperty() {
        return nombre;
    }
    /** @return apellidos como String */
    public String getApellidos() {
        return apellidos.get();
    }

    /** @return propiedad apellidos */
    public StringProperty apellidosProperty() {
        return apellidos;
    }

    /** @return población como String */
    public String getPoblacion() {
        return poblacion.get();
    }

    /** @return propiedad población */
    public StringProperty poblacionProperty() {
        return poblacion;
    }

    /** @return próxima cita como LocalDateTime */
    public LocalDateTime getProximaCita() {
        return proximaCita.get();
    }
    
    /** @return propiedad próxima cita */
    public ObjectProperty<LocalDateTime> proximaCitaProperty() {
        return proximaCita;
    }

    /**
     * Devuelve el nombre completo del cliente (nombre + apellidos).
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return getNombre() + " " + getApellidos();
    }

    /**
     * Devuelve una propiedad de solo lectura con el nombre completo.
     * Útil para enlazarlo directamente con columnas de TableView.
     * @return propiedad nombre completo
     */
    public ReadOnlyStringWrapper nombreCompletoProperty() {
        return new ReadOnlyStringWrapper(getNombreCompleto());
    }
}
