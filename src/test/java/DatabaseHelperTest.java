/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

/**
 *
 * @author donpe
 */
public class DatabaseHelperTest {

    private static final String TEST_USER = "test_user";
    private static final String TEST_PASS = "test_pass";

    @BeforeAll
    public static void setUpClass() {
        // Crear carpeta y base de datos si no existen
        DatabaseHelper.main(null); // Inicialización completa
    }

    @AfterAll
    public static void tearDownClass() {
        // Opcional: eliminar usuario de prueba si es necesario
    }

    @BeforeEach
    public void setUp() {
        // Eliminar usuario de test si ya existe (evitar conflicto)
        if (DatabaseHelper.verificarUsuarioExiste(TEST_USER)) {
            // Borrar manualmente con SQL si se desea
        }
    }

    @AfterEach
    public void tearDown() {
        // Nada por ahora
    }

    @Test
    public void testConectarDB() {
        Connection conn = DatabaseHelper.conectarddbb();
        assertNotNull(conn, "La conexión a la base de datos debe ser válida");
    }

    @Test
    public void testAddUserLoginYVerificarLogin() {
        DatabaseHelper.addUserLogin(TEST_USER, TEST_PASS);
        boolean loginCorrecto = DatabaseHelper.verificarLogin(TEST_USER, TEST_PASS);
        assertTrue(loginCorrecto, "El login debe funcionar para el usuario creado");
    }

    @Test
    public void testVerificarUsuarioExiste() {
        DatabaseHelper.addUserLogin(TEST_USER, TEST_PASS);
        boolean existe = DatabaseHelper.verificarUsuarioExiste(TEST_USER);
        assertTrue(existe, "El usuario debe existir después de ser insertado");
    }

    @Test
    public void testCrearBaseDeDatosYTablas() {
        File dbFile = new File("data/databases/datos.db");
        assertTrue(dbFile.exists(), "La base de datos debe existir en disco");
    }

    @Test
    public void testEsTablaUsuariosVacia() {
        Connection conn = DatabaseHelper.conectarddbb();
        assertNotNull(conn);
        boolean vacia = DatabaseHelper.esTablaUsuariosVacia(conn);
        // Al ejecutar DatabaseHelper.main(), los usuarios se insertan si la tabla está vacía
        assertFalse(vacia, "La tabla de usuarios no debe estar vacía después de inicializar");
    }
}
