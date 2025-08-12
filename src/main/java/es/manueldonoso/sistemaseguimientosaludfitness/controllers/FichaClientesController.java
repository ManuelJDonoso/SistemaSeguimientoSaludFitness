/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.browniebytes.javafx.control.DateTimePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.net.URL;
import java.sql.SQLException;
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
    private ClienteDAOImpl DAO;
    
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
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private JFXTextField tfPoblacion;
    @FXML
    private JFXTextField tfCP;
    @FXML
    private JFXTextField tfTel;
    @FXML
    private JFXTextField tf_Email;


    
    
    
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
        try {
            setDni(dni);
            DAO=new ClienteDAOImpl(DatabaseHelper.getConnection());
            
            cliente =DAO.obtenerPorDni(dni);
            tf_cliente.setText(cliente.getNombreCompleto());
            tf_dni.setText(cliente.getDni());
            tfDireccion.setText(cliente.getDireccion());
            tfPoblacion.setText(cliente.getPoblacion());
            tfCP.setText(cliente.getCp());
            tfTel.setText(cliente.getTelefono());
            tf_Email.setText(cliente.getEmail());
            tf_altura.setText(cliente.getAltura());
            tf_pesoIdeal.setText(cliente.getPesoIdeal());
            ta_Anotaciones.setText(cliente.getAnotaciones());
        } catch (SQLException ex) {
            System.getLogger(FichaClientesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
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
