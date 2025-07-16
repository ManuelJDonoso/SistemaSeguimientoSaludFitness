/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;

import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author donpe
 */
public class AltaClientesController implements Initializable {

    @FXML
    private ToggleGroup sexo;
    @FXML
    private Spinner<Integer> sp_Altura;
    @FXML
    private Spinner<Double> sp_Peso;
    @FXML
    private JFXTextField tf_nombre;
    @FXML
    private JFXTextField tfPrimerApellido;
    @FXML
    private JFXTextField tfSegundoApellido;
    @FXML
    private JFXTextField tfDNI;
    @FXML
    private JFXTextField tfIMC;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private JFXTextField tfPoblacion;
    @FXML
    private JFXTextField tfCP;
    @FXML
    private JFXTextField tfTel;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private ImageView ivFoto;
    @FXML
    private JFXButton btnCamara;
    @FXML
    private JFXButton btnSubirFoto;
    @FXML
    private JFXTextArea TAAnotaciones;
    @FXML
    private Label lb_DNIError;
    @FXML
    private JFXButton btnResert;
    @FXML
    private AnchorPane tfMetabolismo;
    @FXML
    private JFXTextField tfGrasaCorporal;
    @FXML
    private JFXTextField tfProteina;
    @FXML
    private JFXTextField tfGrasaVisceral;
    @FXML
    private JFXTextField tfPesoIdeal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 250, 180, 1);

        // Personalizar el formato mostrado (ej. "180 cm") sin afectar el valor real
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value + " cm";
            }

            @Override
            public Integer fromString(String text) {
                // Eliminar "cm" si el usuario escribe manualmente
                try {
                    return Integer.parseInt(text.replace("cm", "").trim());
                } catch (NumberFormatException e) {
                    return 180; // Valor por defecto si hay error
                }
            }
        });

        sp_Altura.setValueFactory(valueFactory);

      SpinnerValueFactory.DoubleSpinnerValueFactory pesoFactory
    = new SpinnerValueFactory.DoubleSpinnerValueFactory(10.00, 300.00, 80.00, 0.01);

pesoFactory.setConverter(new StringConverter<Double>() {
    @Override
    public String toString(Double value) {
        return String.format("%.2f kg", value);
    }

    @Override
    public Double fromString(String text) {
        try {
            return Double.parseDouble(text.replace("kg", "").trim());
        } catch (NumberFormatException e) {
            return sp_Peso.getValue(); // ← mantener el valor actual si hay error
        }
    }
});

sp_Peso.setValueFactory(pesoFactory);
sp_Peso.setEditable(true); // ← asegúrate de que sea editable
sp_Peso.getEditor().setText(pesoFactory.getConverter().toString(sp_Peso.getValue()));
    }

    @FXML
    private void oitextChanged_DNI(InputMethodEvent event) {
    }

    @FXML
    private void OAbtnGuardar(ActionEvent event) {
    }

    @FXML
    private void OAbtnCamara(ActionEvent event) {
    }

    @FXML
    private void OAbtnResert(ActionEvent event) {
    }

}
