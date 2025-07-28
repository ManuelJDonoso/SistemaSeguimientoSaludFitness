/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.browniebytes.javafx.control.DateTimePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class FichaClientesController implements Initializable {

    @FXML
    private AnchorPane root;

    private Cliente cliente;
    
    private String dni;
    @FXML
    private JFXTextField tf_cliente;
    @FXML
    private JFXTextField tf_dni;
    @FXML
    private JFXTextField tf_altura;
    @FXML
    private JFXTextField tf_ultimoPeso;
    @FXML
    private JFXTextField tf_pesoActual;
    @FXML
    private JFXTextField tf_grasaCorporal;
    @FXML
    private JFXTextField tf_proteina;
    @FXML
    private JFXTextField tf_metabolismoBasal;
    @FXML
    private JFXTextField tfGrasaVisceral;
    @FXML
    private JFXTextArea ta_Anotaciones;
    @FXML
    private DateTimePicker tf_fechaHoraTomasDatos;
    @FXML
    private JFXTextField tf_pesoIdeal;


    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //to do
    }    

    private void setDni(String dni) {
        this.dni = dni;
       
    }
    
    public void cargarFicha(String dni){
        setDni(dni);
        System.out.println(dni);
    }

    @FXML
    private void OA_btnDatosContactos(ActionEvent event) {
    }

    @FXML
    private void oa_btnActualizarDatos(ActionEvent event) {
    }

    @FXML
    private void OA_AsignarCita(ActionEvent event) {
    }

    @FXML
    private void OA_Cancelar(ActionEvent event) {
    }

    @FXML
    private void OA_camara(ActionEvent event) {
    }

    @FXML
    private void OA_subirImagen(ActionEvent event) {
    }

    @FXML
    private void OA_historialDieta(ActionEvent event) {
    }

    @FXML
    private void OA_establecerDieta(ActionEvent event) {
    }

    @FXML
    private void OA_historialEjercicios(ActionEvent event) {
    }

    @FXML
    private void OA_ejercicioSemanal(ActionEvent event) {
    }

    @FXML
    private void OA_pdf(ActionEvent event) {
    }
    
}
