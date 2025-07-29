/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import es.manueldonoso.sistemaseguimientosaludfitness.util.StageShow;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
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
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarColumnas();
        // Configurar el filtrado

        cargarDatos();

        tabla_clientes.setItems(filteredData);

        añadirListenersFiltro();

        selecionarCliente();

    }

    private void configurarColumnas() {
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
    }

    private void cargarDatos() {
        try {

            String query = "SELECT dni, nombre, apellido1, apellido2, poblacion FROM clientes";

            ResultSet rs = DatabaseHelper.resultSet(query);

            clientesList.clear();

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
                        null, // fechaAlta
                        null  // email
                );
                cliente.setProximaCita(proximaCita);
                clientesList.add(cliente);
                // Cargar las poblaciones después de tener los datos
                cargarPoblaciones();
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

    private void cargarPoblaciones() {
        Set<String> poblaciones = new TreeSet<>();
        for (Cliente cliente : clientesList) {
            if (cliente.getPoblacion() != null && !cliente.getPoblacion().isEmpty()) {
                poblaciones.add(cliente.getPoblacion());
            }
        }

        cb_poblacion.getItems().clear();
        cb_poblacion.getItems().add(""); // Opción vacía para no filtrar
        cb_poblacion.getItems().addAll(poblaciones);
    }

    private void añadirListenersFiltro() {
        tf_dni.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        tf_nombre.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        tf_apellidos.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cb_poblacion.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        fp_fecha_cita.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        filteredData.setPredicate(cliente -> {
            // Filtro por DNI
            String filtroDNI = tf_dni.getText();
            if (filtroDNI != null && !filtroDNI.isEmpty() && !cliente.getDni().toLowerCase().contains(filtroDNI.toLowerCase())) {
                return false;
            }

            // Filtro por nombre
            String filtroNombre = tf_nombre.getText();
            if (filtroNombre != null && !filtroNombre.isEmpty()
                    && !cliente.getNombreCompleto().toLowerCase().contains(filtroNombre.toLowerCase())) {
                return false;
            }

            // Filtro por apellidos
            String filtroApellidos = tf_apellidos.getText();
            if (filtroApellidos != null && !filtroApellidos.isEmpty()) {
                String apellidosCliente = (cliente.getApellido1() + " " + cliente.getApellido2()).toLowerCase();
                if (!apellidosCliente.contains(filtroApellidos.toLowerCase())) {
                    return false;
                }
            }

            // Filtro por población
            String filtroPoblacion = cb_poblacion.getValue();
            if (filtroPoblacion != null && !filtroPoblacion.isEmpty()
                    && (cliente.getPoblacion() == null || !cliente.getPoblacion().equalsIgnoreCase(filtroPoblacion))) {
                return false;
            }

            // Filtro por fecha de cita
            LocalDate filtroFecha = fp_fecha_cita.getValue();
            if (filtroFecha != null) {
                LocalDateTime cita = cliente.getProximaCita();
                if (cita == null || !cita.toLocalDate().equals(filtroFecha)) {
                    return false;
                }
            }

            return true;
        });
    }

    private void selecionarCliente() {
        tabla_clientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tabla_clientes.getSelectionModel().getSelectedItem() != null) {
                Cliente seleccionado = tabla_clientes.getSelectionModel().getSelectedItem();

                try {
                    StageShow.CargarDatosClientes(root, seleccionado.getDni());
                } catch (IOException ex) {
                    Logger.getLogger(SeguimientoController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            tabla_clientes.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER && tabla_clientes.getSelectionModel().getSelectedItem() != null) {
                    Cliente seleccionado = tabla_clientes.getSelectionModel().getSelectedItem();
                    try {
                        StageShow.CargarDatosClientes(root, seleccionado.getDni());
                    } catch (IOException ex) {
                        Logger.getLogger(SeguimientoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
    }
}
