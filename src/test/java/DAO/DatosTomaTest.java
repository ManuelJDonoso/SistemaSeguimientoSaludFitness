/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import base.BaseTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.DatosTomaDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author donpe
 */
public class DatosTomaTest extends BaseTest {

    private ClienteDAOImpl DAOCliente;
    private List<Cliente> clientes;
    private DatosTomaDAOImpl DAODatosToma;
    private List<DatosToma> tomas;

    public DatosTomaTest() {
    }

    @BeforeEach
    public void setUp() {
        try {
            DatabaseHelper.crearTablasdefault(conn);
            conn = DatabaseHelper.getConnection();
            DAOCliente = new ClienteDAOImpl(conn);

            // DatabaseHelper.insertar10Clientes(conn);
            DAODatosToma = new DatosTomaDAOImpl(conn);
            tomas = new ArrayList<>();
         

      

  
        } catch (SQLException ex) {
            System.getLogger(DatosTomaTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @AfterEach
    public void tearDown() {
        DAOCliente.eliminarTodosUsuarios();
        DAODatosToma.EliminarTodosDatosTomas();
        
    }

    @Test
    public void insertar() {
        
        System.out.println("Crando Datos De Toma");
        LocalDateTime ahora =LocalDateTime.now();
        String ahoraString=ahora.toString();
        
        DatosToma dt=new DatosToma(
                "00000001X", //dni
                "32", //Peso
                "30", //imc
                "foto.jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                ahora.plusWeeks(1));//Fecha proxima cita
        System.out.println("Insertando datos de tomo");
        DAODatosToma.insertarDatosToma(dt);
        
        System.out.println("Comprobando que se ha insertado correctamente");
        
        DatosToma recdt= DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString);
        System.out.println(recdt.toString());
        assertNotNull(DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString));
        
    }

    @Test
    public void ListarTodas() {
        DatabaseHelper.insertar10Clientes30Tomas(conn);
    }

    @Test
    public void ListarFechaUsuario() {
    }

    @Test
    public void modificar() {
    }

    @Test
    public void Eliminar() {
    }

}
