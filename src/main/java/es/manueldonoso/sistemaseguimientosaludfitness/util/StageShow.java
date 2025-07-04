/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import static es.manueldonoso.sistemaseguimientosaludfitness.App.main;
import es.manueldonoso.sistemaseguimientosaludfitness.controllers.main.LoginController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * La clase `Stage_show` se encarga de gestionar la visualización de diferentes
 * ventanas (Stages) en la aplicación, incluyendo ventanas de login, dashboards,
 * y paneles de gestión de datos. Utiliza archivos FXML para cargar las
 * interfaces de usuario y configurar el comportamiento de las ventanas.
 *
 * La clase también maneja la creación de escenas, el estilo visual de las
 * ventanas y la gestión de efectos visuales. Cada método corresponde a una
 * ventana o panel específico de la aplicación.
 *
 * @author "Manuel Jesús Donoso Pérez";
 */
public class StageShow {
    public static void MostrarLogin(){
     Stage primaryStage = new Stage();
        try {
              FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginController.class.getResource("/es/manueldonoso/sistemaseguimientosaludfitness/vistas/main/Login.fxml"));

            // Ventana a cargar
            VBox ventana = (VBox) loader.load();

            // Creo la escena
            Scene scene = new Scene(ventana);

            // Modifico el stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
          //  primaryStage.initStyle(StageStyle.UNDECORATED);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        primaryStage.show();
    }
}
