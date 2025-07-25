/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private JFXComboBox<?> cb_poblacion;
    @FXML
    private JFXTextField tf_apellidos;
    @FXML
    private DatePicker fp_fecha_cita;
    @FXML
    private TableView<?> tabla_clientes;
    @FXML
    private TableColumn<?, ?> cl_dni;
    @FXML
    private TableColumn<?, ?> cl_clientes;
    @FXML
    private TableColumn<?, ?> cl_poblacion;
    @FXML
    private TableColumn<?, ?> cl_cita;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
