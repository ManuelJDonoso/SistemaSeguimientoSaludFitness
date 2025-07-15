/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import es.manueldonoso.sistemaseguimientosaludfitness.util.StageShow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author donpe
 */
public class DashboardController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView ivImagenFondo;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        
    }    

    @FXML
    private void OABtnAlta(ActionEvent event) {
        StageShow.cargarVistaDashboard("AltaClientes.fxml", contentPane);
    }

    @FXML
    private void OABtnSeguimiento(ActionEvent event) {
        StageShow.cargarVistaDashboard("Seguimiento.fxml", contentPane);
    }

    @FXML
    private void OABtnRecetas(ActionEvent event) {
        StageShow.cargarVistaDashboard("Recetas.fxml", contentPane);
    }

    @FXML
    private void OABtnEjercicio(ActionEvent event) {
        StageShow.cargarVistaDashboard("Ejercicios.fxml", contentPane);
    }

    @FXML
    private void OABtnCitas(ActionEvent event) {
        StageShow.cargarVistaDashboard("Citas.fxml", contentPane);
    }
    
}
