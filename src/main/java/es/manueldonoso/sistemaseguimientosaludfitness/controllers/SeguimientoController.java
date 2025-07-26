/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author donpe
 */
public class SeguimientoController implements Initializable {

    @FXML
    private JFXTextField tf_dni;
    @FXML
    private JFXTextField tf_nombre;
    @FXML
    private JFXComboBox<String> cb_poblacion;
    @FXML
    private JFXTextField tf_apellidos;
    @FXML
    private DatePicker fp_fecha_cita;
    @FXML
    private TableView<Cliente> tabla_clientes;
    @FXML
    private TableColumn<Cliente, String> cl_dni;
    @FXML
    private TableColumn<Cliente, String> cl_clientes;
    @FXML
    private TableColumn<Cliente, String> cl_poblacion;
    @FXML
    private TableColumn<Cliente, String> cl_cita;

    private ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
    private FilteredList<Cliente> filteredData = new FilteredList<>(clientesList, p -> true);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configurar las columnas de la tabla
        cl_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        cl_clientes.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNombreCompleto()));
        cl_poblacion.setCellValueFactory(new PropertyValueFactory<>("poblacion"));
        cl_cita.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getProximaCita() != null
                        ? cellData.getValue().getProximaCita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        : "Sin cita"
                ));

        // Cargar datos de la base de datos
        cargarDatos();
    }

    private void cargarDatos() {
        try {

            String query = "SELECT dni, nombre, apellido1, apellido2, poblacion FROM clientes";

            ResultSet rs = DatabaseHelper.resultSet(query);

            clientesList.clear();
            // Asignar los datos a la tabla
            tabla_clientes.setItems(clientesList);
    
            while (rs.next()) {
                // Parsear la fecha de la cita si existe
                LocalDateTime proximaCita = null;
                String citaStr = null; //rs.getString("proximaCita");
                if (citaStr != null && !citaStr.isEmpty()) {
                    proximaCita = LocalDateTime.parse(citaStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }

                Cliente cliente = new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        null, // sexo
                        null, // altura
                        null, // peso
                        null, // imc
                        null, // dirFoto
                        null, // direccion
                        rs.getString("poblacion"),
                        null, // cp
                        null, // telefono
                        null, // grasac
                        null, // proteina
                        null, // metabolismoV
                        null, // grasaV
                        null, // PesoIdeal
                        null, // Anotaciones
                        null, // fechaNacimiento
                        null // fechaAlta
                );
                System.out.println(cliente);
                cliente.setProximaCita(proximaCita);
                clientesList.add(cliente);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText("No se pudieron cargar los clientes");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        
            System.out.println("Items en tabla: " + tabla_clientes.getItems().size());

    }

    
}
