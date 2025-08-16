/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package base;

import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import es.manueldonoso.sistemaseguimientosaludfitness.util.UtilHelper;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public abstract class BaseTest {

    protected static String ruta = "data/databaseTest";
    protected static String archivo = "datos.db";
    protected static Connection conn;

    public BaseTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        DatabaseHelper.InitBaseDatosSQLite(ruta, archivo);
        conn = DatabaseHelper.getConnection();
    }

    @AfterAll
    public static void tearDownClass() throws SQLException {
        conn.close();
        UtilHelper.EliminarFichero(ruta, archivo);
        UtilHelper.EliminarRutaVacia(ruta);
        UtilHelper.EliminarRutaVacia("data");
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

}
