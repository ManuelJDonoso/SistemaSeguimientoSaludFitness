/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
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
    
}
