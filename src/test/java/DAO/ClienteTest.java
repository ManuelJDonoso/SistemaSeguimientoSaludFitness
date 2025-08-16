/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import base.BaseTest;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author donpe
 */
public class ClienteTest extends BaseTest {

    private Cliente cliente;
    private ClienteDAOImpl DAO;

    public ClienteTest() {
    }

    @BeforeEach
    public void setUp() {

        try {
            cliente = new Cliente(
                    "00000001X",
                    "Nombre",
                    "Apellido1_",
                    "Apellido2_",
                    "Hombre",
                    "1.75",
                    "foto.jpg",
                    "Calle Falsa 1",
                    "Ciudad 1",
                    "28001",
                    "600000001",
                    "70", "Sin anotaciones",
                    LocalDate.of(1986, 5, 6),
                    LocalDateTime.now(),
                    "email1@examplo.com"
            );

            DatabaseHelper.crearTablasdefault(conn);
            conn = DatabaseHelper.getConnection();
            DAO = new ClienteDAOImpl(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("---------------------------comienza la prueba ---------------------------");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Limpiando Datos");

        DAO.eliminarTodosUsuarios();
    }

    @Test
    public void Insertar() {
        String dni = cliente.getDni();

        System.out.println("Comprobando que no existe cliente en la base de datos");
        assertFalse(DAO.existeCliente(dni));

        System.out.println("Insertando Cliente");
        DAO.insertar(cliente);
        System.out.println("Comprobando que el Cliente se ha insertado");

        assertTrue(DAO.existeCliente(dni));

    }

    @Test
    public void eliminar() {
        String dni = cliente.getDni();

        System.out.println("Insertando Cliente");
        DAO.insertar(cliente);
        System.out.println("Comprobando que el Cliente se ha insertado");

        assertTrue(DAO.existeCliente(dni));

        System.out.println("Eliminando Cliente");

        DAO.eliminarUsuario(dni);
        System.out.println("Comprobando que no existe cliente en la base de datos");
        assertFalse(DAO.existeCliente(dni));
    }

    @Test
    public void BuscarUsuarioDni(){
        String dni = cliente.getDni();
        System.out.println("Insertando Cliente");
        DAO.insertar(cliente);

        Cliente c = DAO.obtenerPorDni(dni);
        System.out.println("Comprobando que se ha insertado correctamente");
        assertNotNull(c);
    
    }
    
    @Test
    public void ModificarUsuario() {
        Cliente c, modcliente, mod;
        modcliente = new Cliente(
                "00000001X",
                "Nombremod",
                "Apellido1_mod",
                "Apellido2_",
                "Hombre",
                "1.75",
                "foto.jpg",
                "Calle Falsa 1",
                "Ciudad 1",
                "28001",
                "600000001",
                "70.2", "nuevas mod",
                LocalDate.of(1986, 5, 10),
                LocalDateTime.now(),
                "email1mod@examplo.com"
        );
        String dni = cliente.getDni();
        System.out.println("Insertando Cliente");
        DAO.insertar(cliente);

        c = DAO.obtenerPorDni(dni);
        System.out.println("Comprobando que se ha insertado correctamente");
        assertNotNull(c);
        System.out.println("Modicar Cliente");
        DAO.actualizarCliente(cliente, modcliente);
        mod = DAO.obtenerPorDni(dni);
   

        assertEquals(mod.toString(), modcliente.toString());

    }
    
        @Test
    public void ModificarUsuarioDNI() {
        Cliente c, modcliente, mod;
        modcliente = new Cliente(
                "00000002z",
                "Nombremod",
                "Apellido1_mod",
                "Apellido2_",
                "Hombre",
                "1.75",
                "foto.jpg",
                "Calle Falsa 1",
                "Ciudad 1",
                "28001",
                "600000001",
                "70.2", "nuevas mod",
                LocalDate.of(1986, 5, 10),
                LocalDateTime.now(),
                "email1mod@examplo.com"
        );
        String dni = cliente.getDni();
        System.out.println("Insertando Cliente");
        DAO.insertar(cliente);

        c = DAO.obtenerPorDni(dni);
        System.out.println("Comprobando que se ha insertado correctamente");
        assertNotNull(c);
        System.out.println("Modicar Cliente");
        DAO.actualizarCliente(cliente, modcliente);
        mod = DAO.obtenerPorDni("00000002z");
   

        assertEquals(mod.toString(), modcliente.toString());

    }

}
