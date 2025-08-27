/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.ClienteTomaDTO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteTomaDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteTomaDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.DatosTomaDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.DatosTomaDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import es.manueldonoso.sistemaseguimientosaludfitness.util.StageShow;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.TableCell;
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
    private TableView<ClienteTomaDTO> tabla_clientes;
    @FXML
    private TableColumn<ClienteTomaDTO, String> cl_dni;
    @FXML
    private TableColumn<ClienteTomaDTO, String> cl_clientes;
    @FXML
    private TableColumn<ClienteTomaDTO, String> cl_poblacion;
    @FXML
    private TableColumn<ClienteTomaDTO, LocalDateTime> cl_cita;

    private ObservableList<ClienteTomaDTO> clientesList = FXCollections.observableArrayList();
    private FilteredList<ClienteTomaDTO> filteredData = new FilteredList<>(clientesList, p -> true);
    @FXML
    private AnchorPane root;
    Connection conn;

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

    /**
     * Configura las columnas de la tabla de clientes para mostrar los datos
     * contenidos en {@link ClienteTomaDTO}.
     *
     * <ul>
     * <li><b>DNI:</b> enlazado directamente a
     * {@link ClienteTomaDTO#dniProperty()}.</li>
     * <li><b>Nombre completo:</b> combina
     * {@link ClienteTomaDTO#nombreProperty()} y
     * {@link ClienteTomaDTO#apellidosProperty()} a través de
     * {@link ClienteTomaDTO#nombreCompletoProperty()}.</li>
     * <li><b>Población:</b> enlazado a
     * {@link ClienteTomaDTO#poblacionProperty()}.</li>
     * <li><b>Próxima cita:</b> enlazado a
     * {@link ClienteTomaDTO#proximaCitaProperty()} y formateado en el estilo
     * <code>dd/MM/yyyy HH:mm</code>.</li>
     * </ul>
     *
     * Este método debe llamarse en la inicialización del controlador (por
     * ejemplo, en el método {@code initialize}).
     */
    private void configurarColumnas() {
        // Columna DNI
        cl_dni.setCellValueFactory(cellData -> cellData.getValue().dniProperty());

        // Columna Nombre completo (nombre + apellidos)
        cl_clientes.setCellValueFactory(cellData -> cellData.getValue().nombreCompletoProperty());

        // Columna Población
        cl_poblacion.setCellValueFactory(cellData -> cellData.getValue().poblacionProperty());

        // Columna Fecha de la próxima cita con formato dd/MM/yyyy HH:mm
        cl_cita.setCellValueFactory(cellData -> cellData.getValue().proximaCitaProperty());

        cl_cita.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
    }

    /**
     * Carga todos los registros de clientes y sus próximas citas usando
     * {@link ClienteTomaDAO}. Los datos se vuelcan en la lista observable que
     * alimenta la tabla de clientes.
     */
    private void cargarDatos() {
        try {
            conn = DatabaseHelper.getConnection();
        } catch (SQLException ex) {
            System.getLogger(SeguimientoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        clientesList.clear();

        // Crear DAO
        ClienteTomaDAO clienteTomaDAO = new ClienteTomaDAOImpl(conn);

        // Obtener todos los clientes con sus datos de cita
        List<ClienteTomaDTO> clientesDTO = clienteTomaDAO.findAll();

        // Volcar la lista en la ObservableList usada por la tabla
        clientesList.setAll(clientesDTO);

    }

    private void cargarPoblaciones() {

    }

    private void añadirListenersFiltro() {

    }

    private void aplicarFiltros() {

    }

    private void selecionarCliente() {

    }
}
