package es.manueldonoso.sistemaseguimientosaludfitness;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAOimpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import es.manueldonoso.sistemaseguimientosaludfitness.util.StageShow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private LoginDAOimpl DAO;  //solo en desarrollo
    private Login login =new Login("admin", "admin");//solo en desarrollo

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        String ruta = "data/databases", db = "datos.db";

        DatabaseHelper.InitBaseDatosSQLite(ruta, db);

        Connection conn = DatabaseHelper.getConnection();
        if (conn != null) {
            System.out.println("se creo bien la conexion");
            DatabaseHelper.crearTablasdefault(conn);
            conn = DatabaseHelper.getConnection();
            DAO = new LoginDAOimpl(conn); //solo en desarrollo
            if(!DAO.usuarioExiste("admin")){DAO.insertar(login);} //solo en desarrollo
            StageShow.MostrarLogin(conn);
        } else {
            System.out.println("error en la conexion");
        }

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        launch();

    }

}
