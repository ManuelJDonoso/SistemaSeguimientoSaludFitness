/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package util;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAOimpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
public class LoginDAOimplTest {

    private static final String DB_FOLDER = "data/databases";
    private static final String DB_NAME = "test_temp.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;
    private static String TEST_DB_URL = "jdbc:sqlite:" + DB_PATH;

    private static final String TEST_USER = "test_user";
    private static final String TEST_PASS = "test_pass";

    private static LoginDAOimpl loginDAO;
    private static Login testLogin;

    
    
    
    public LoginDAOimplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        DatabaseHelper.crearCarpetaSiNoExite();
        DatabaseHelper.setUrl(TEST_DB_URL);
        LoginDAOimpl.seturl(TEST_DB_URL);
        DatabaseHelper.crearBaseDatos();
        DatabaseHelper.crearTablasdefault();

        // Inicializar el DAO
        loginDAO = new LoginDAOimpl();
        loginDAO.setDB_NAME(DB_NAME);

        // Crear objeto Login para pruebas
        testLogin = new Login();
        testLogin.setUsuario(TEST_USER);
        testLogin.setPass(TEST_PASS);

    }

    @AfterAll
    public static void tearDownClass() {
        try {
            // 1. Forzar el cierre de TODAS las conexiones
            DatabaseHelper.forceCloseAllConnections();

            // 2. Espera más larga para sistemas Windows
            Thread.sleep(1000);

            // 3. Llamar al recolector de basura para liberar recursos
            System.gc();
            Thread.sleep(200);

            // 4. Eliminar con múltiples intentos
            Path dbFile = Paths.get(DB_PATH);
            int maxAttempts = 10;  // Aumentamos los intentos
            for (int i = 0; i < maxAttempts; i++) {
                try {
                    Files.deleteIfExists(dbFile);
                    System.out.println("Base de datos eliminada con éxito en intento " + (i + 1));
                    return;  // Salir si tiene éxito
                } catch (IOException e) {
                    if (i == maxAttempts - 1) {
                        System.err.println("Fallo definitivo al eliminar BD: " + e.getMessage());
                        // Último recurso: renombrar el archivo para eliminación posterior
                        try {
                            Path tempFile = Paths.get(DB_PATH + ".old_" + System.currentTimeMillis());
                            Files.move(dbFile, tempFile, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Archivo renombrado para eliminación posterior: " + tempFile);
                        } catch (IOException ex) {
                            System.err.println("También falló el renombrado: " + ex.getMessage());
                        }
                    }
                    Thread.sleep(300);  // Espera más larga entre intentos
                    System.gc();  // Llamar al GC en cada intento
                }
            }
        } catch (Exception e) {
            System.err.println("Error crítico en tearDown: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
       System.out.println("eliminando usuario: " + testLogin);
        // Act
    //    loginDAO.eleminar(testLogin);  
    }

    @Test
    public void testInsertar() {
        System.out.println("insertando usuario: " + testLogin);
        // Act
        loginDAO.insertar(testLogin);

        System.out.println("comprobando que " + testLogin + " exite despues de insertar");
        // Assert
        assertTrue(loginDAO.usuarioExiste(TEST_USER));
    }

    @Test
    public void testVerificarLogin() {
        loginDAO.insertar(testLogin);
        System.out.println("-----usuario insertado :"+ testLogin);
        assertTrue(loginDAO.usuarioExiste(TEST_USER));
        loginDAO.verificarLogin(testLogin);
        

    }

    @Test
    public void testCambiarPass() {
        System.out.println("------CAMBIO DE PASS----");
        loginDAO.insertar(testLogin);
         System.out.println("-----usuario insertado :"+ testLogin);
          assertTrue(loginDAO.usuarioExiste(TEST_USER));
            System.out.println(testLogin);
          loginDAO.cambiarPass(testLogin, "HOLA");
          testLogin.setPass("HOLA");
          loginDAO.verificarLogin(testLogin);
          System.out.println(testLogin);
          
 
    }

    @Test
    public void testEliminar() {
        loginDAO.insertar(testLogin);
        assertTrue(loginDAO.usuarioExiste(TEST_USER));
        System.out.println("eliminando usuario: " + testLogin);
        // Act
        loginDAO.eleminar(testLogin);

        System.out.println("comprobando que " + testLogin + " NO exite despues de Borrar");
        // Assert
        assertFalse(loginDAO.usuarioExiste(TEST_USER));
    }
}
