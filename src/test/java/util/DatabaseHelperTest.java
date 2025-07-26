package util;

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author donpe
 */
public class DatabaseHelperTest {

    private static final String DB_FOLDER = "data/databases";
    private static final String DB_NAME = "test_temp.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;
    private static String TEST_DB_URL = "jdbc:sqlite:" + DB_PATH;

    private static final String TEST_USER = "test_user";
    private static final String TEST_PASS = "test_pass";

    @BeforeAll
    public static void setUpClass() throws SQLException {
        DatabaseHelper.setUrl(TEST_DB_URL);
        DatabaseHelper.crearBaseDatos();
        DatabaseHelper.crearTablasdefault();

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
        // Nada por ahora
    }

    @Test
    public void testConectarDB() {
        System.out.println("conectar con la base de datos temporal");
        Connection conn = DatabaseHelper.conectarddbb();
        assertNotNull(conn, "La conexión a la base de datos debe ser válida");
    }

    @Test
    public void testComprobarTablas() throws SQLException {

        try (Connection conn = DatabaseHelper.conectarddbb()) {
            // Verifica primero que la conexión es válida
            assertFalse(conn.isClosed(), "La conexión debería estar abierta");

            // Verifica que hay tablas (cualquier tabla)
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet allTables = meta.getTables(null, null, "%", null);
            assertTrue(allTables.next(), "La base de datos no tiene ninguna tabla");

            // Lista específica de tablas
            String[] tablasRequeridas = {"login", "clientes", "dietaSemanal", "rutinaSemanal",
                "seguimiento", "dietaDia", "rutinaDia"};

            for (String tabla : tablasRequeridas) {
                ResultSet rs = meta.getTables(null, null, tabla, null);
                assertTrue(rs.next(), "La tabla " + tabla + " no fue creada");
            }
        } catch (SQLException e) {
            fail("Error al comprobar tablas: " + e.getMessage());
        }
    }

    @Test
    public void testAddUserLoginYVerificarLogin() {
        System.out.println("añadiendo el usuario test_user");
        DatabaseHelper.addUserLogin(TEST_USER, TEST_PASS);
        boolean loginCorrecto = DatabaseHelper.verificarLogin(TEST_USER, TEST_PASS);
        assertTrue(loginCorrecto, "El login debe funcionar para el usuario creado");
    }

    @Test
    public void testEsTablaUsuariosVacia() {
        Connection conn = DatabaseHelper.conectarddbb();
        assertNotNull(conn);
        boolean vacia = DatabaseHelper.esTablaUsuariosVacia(conn);
        // Al ejecutar DatabaseHelper.main(), los usuarios se insertan si la tabla está vacía
        assertTrue(vacia, "La tabla de usuarios  debe estar vacía  ");
        DatabaseHelper.insertarUsuariosPorDefecto(conn);
        vacia = DatabaseHelper.esTablaUsuariosVacia(conn);
        assertFalse(vacia, "La tabla de usuarios no debe estar vacía después de inicializar");
    }
}
