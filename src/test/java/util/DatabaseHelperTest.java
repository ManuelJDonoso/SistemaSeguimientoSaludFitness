/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package util;

import base.BaseTest;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class DatabaseHelperTest extends BaseTest {

    public DatabaseHelperTest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Verifica que se pueda establecer una conexión válida con la base de datos
     * temporal.
     *
     * @throws SQLException si ocurre un error al intentar conectar
     */
    @Test
    public void testConectarDB() throws SQLException {
        System.out.println("conectar con la base de datos temporal");
        try (Connection connection = DatabaseHelper.getConnection()) {
            assertNotNull(connection, "La conexión a la base de datos debe ser válida");
        }

    }

    /**
     * Verifica que el método
     * {@link DatabaseHelper#crearTablasdefault(Connection)} crea correctamente
     * todas las tablas por defecto.
     *
     * @throws SQLException si ocurre un error al crear o verificar las tablas
     */
    @Test
    public void testCrearTablas() throws SQLException {
        System.out.println("Creando Tablas en la base de datos...");
        DatabaseHelper.crearTablasdefault(conn);

        conn = DatabaseHelper.getConnection();
        // Verificar que las tablas se han creado
        System.out.println("Comprobando tabla login ");
        assertTrue(DatabaseHelper.existeTabla("login", conn));
        System.out.println("Comprobando tabla clientes ");
        assertTrue(DatabaseHelper.existeTabla("clientes", conn));
        System.out.println("Comprobando tabla dietaSemanal ");
        assertTrue(DatabaseHelper.existeTabla("dietaSemanal", conn));
        System.out.println("Comprobando tabla rutinaSemanal");
        assertTrue(DatabaseHelper.existeTabla("rutinaSemanal", conn));
        System.out.println("Comprobando tabla datosToma");
        assertTrue(DatabaseHelper.existeTabla("datosToma", conn));
        System.out.println("Comprobando tabla dietaDia ");
        assertTrue(DatabaseHelper.existeTabla("dietaDia", conn));
        System.out.println("Comprobando tabla rutinaDia");
        assertTrue(DatabaseHelper.existeTabla("rutinaDia", conn));
    }

}
