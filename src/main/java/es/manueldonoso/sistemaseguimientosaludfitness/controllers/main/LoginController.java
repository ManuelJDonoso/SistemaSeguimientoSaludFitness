/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package es.manueldonoso.sistemaseguimientosaludfitness.controllers.main;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import es.manueldonoso.sistemaseguimientosaludfitness.util.StageShow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class LoginController {


    @FXML // fx:id="btn_ingresar"
    private Button btn_ingresar; // Value injected by FXMLLoader

    @FXML // fx:id="btn_menu_help"
    private MenuItem btn_menu_help; // Value injected by FXMLLoader

    @FXML // fx:id="btn_menu_salir"
    private MenuItem btn_menu_salir; // Value injected by FXMLLoader

    @FXML // fx:id="tf_usuario"
    private JFXTextField tf_usuario; // Value injected by FXMLLoader

    @FXML // fx:id="tfp_pass"
    private JFXPasswordField tfp_pass; // Value injected by FXMLLoader
    @FXML
    private Label lbMensajeError;

    void initialize() {
        assert btn_ingresar != null : "fx:id=\"btn_ingresar\" was not injected: check your FXML file 'Login.fxml'.";
        assert btn_menu_help != null : "fx:id=\"btn_menu_help\" was not injected: check your FXML file 'Login.fxml'.";
        assert btn_menu_salir != null : "fx:id=\"btn_menu_salir\" was not injected: check your FXML file 'Login.fxml'.";
        assert tf_usuario != null : "fx:id=\"tf_usuario\" was not injected: check your FXML file 'Login.fxml'.";
        assert tfp_pass != null : "fx:id=\"tfp_pass\" was not injected: check your FXML file 'Login.fxml'.";

    }

    @FXML
    private void OA_btn_iniciarSesion(ActionEvent event) {
        System.out.println("iniciar sesion");
        boolean inicioCorrecto=DatabaseHelper.verificarLogin(tf_usuario.getText(), tfp_pass.getText());
        if(tf_usuario.getText().isBlank()||tfp_pass.getText().isBlank()){
        lbMensajeError.setText("Los campos usuario y contraseña son requeridos");}else if(!inicioCorrecto){
        lbMensajeError.setText("Error en el usuario o contraseña");}else{StageShow.MostrarDashboard();
        Node source=(Node)event.getSource();
        Stage stage =(Stage)source.getScene().getWindow();
        stage.close();
        }
    }

}
