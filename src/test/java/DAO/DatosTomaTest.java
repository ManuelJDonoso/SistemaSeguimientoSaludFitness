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
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
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
        LocalDateTime ahora = LocalDateTime.now(), semanaSiguiente = ahora.plusWeeks(1);
        String ahoraString = ahora.toString();
        String semanaSiguienteString = semanaSiguiente.toString();

        DatosToma dt = new DatosToma(
                "00000001X", //dni
                "32", //Peso
                "30", //imc
                "foto.jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                semanaSiguiente);//Fecha proxima cita
        System.out.println("Insertando datos de toma");
        DAODatosToma.insertarDatosToma(dt);

        System.out.println("Comprobando que se ha insertado correctamente");

        assertNotNull(DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString));
        System.out.println("comporbado por fecha de toma");
        DatosToma recdt2 = DAODatosToma.buscarUsuarioProximaCita("00000001X", semanaSiguienteString);

        assertNotNull(DAODatosToma.buscarUsuarioProximaCita("00000001X", semanaSiguienteString));
        System.out.println("comporbado por fecha de proxima cita");
    }

    @Test
    public void ListarTodas() {
        System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        List lista = DAODatosToma.ListarTodosDatos();
        
        System.out.println("Comprobando si la lista tiene un tamaño de 30" );
        assertEquals(30, lista.size());
    }

    @Test
    public void ListarFechaUsuario() {
        String dni="0000000X";
         System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        List lista = DAODatosToma.ListarDatosTomaCliente(dni);
        
        System.out.println("Comprobando si la lista obtenida del usuario "+dni+" tiene un tamaño de 3" );
        assertEquals(3, lista.size());
    }

    @Test
    public void ListarPorFechaToma(){
        String fecha = LocalDate.now().toString();
        System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        System.out.println(fecha);
        List lista = DAODatosToma.ListarDatosFecha(fecha);
        assertEquals(10, lista.size());
        
    }
    
    @Test
    public void modificar() {

        System.out.println("Crando Datos De Toma");
        LocalDateTime ahora = LocalDateTime.now(), semanaSiguiente = ahora.plusWeeks(1);
        String ahoraString = ahora.toString();
        String semanaSiguienteString = semanaSiguiente.toString();
        String datosmodString;

        DatosToma old = new DatosToma(
                "00000001X", //dni
                "32", //Peso
                "30", //imc
                "foto.jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                semanaSiguiente);//Fecha proxima cita

        DatosToma mod = new DatosToma(
                "00000001X", //dni
                "42", //Peso
                "32", //imc
                "foto4.jpg", //dirFoto
                "121", //grasac
                "141", //proteina
                "1800", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                semanaSiguiente);//Fecha proxima cita

        datosmodString = mod.toString();
        System.out.println("Insertando datos de toma");

        DAODatosToma.insertarDatosToma(old);

        System.out.println("Comprobando que se ha insertado");
        assertNotNull(DAODatosToma.buscarUsuarioFechaToma(old.getDni(), ahoraString));
        System.out.println("Modificando Datos");
        DAODatosToma.modificarDatosToma(old, mod);
        System.out.println("Comprobando que que han modificado correctamente");
        assertEquals(mod.toString(), datosmodString);

    }

    @Test
    public void EliminarDatosToma() {

        System.out.println("Crando Datos De Toma");
        LocalDateTime ahora = LocalDateTime.now(), semanaSiguiente = ahora.plusWeeks(1);
        String ahoraString = ahora.toString();
        String semanaSiguienteString = semanaSiguiente.toString();

        DatosToma dt = new DatosToma(
                "00000001X", //dni
                "32", //Peso
                "30", //imc
                "foto.jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                semanaSiguiente);//Fecha proxima cita
        System.out.println("Insertando datos de toma");
        DAODatosToma.insertarDatosToma(dt);

        System.out.println("Comprobando que se ha insertado correctamente");
        assertNotNull(DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString));
        System.out.println("Eliminando Datos de la toma");
        DAODatosToma.EliminarDatosToma(dt);
        System.out.println("Comprobando que se ha eliminado correctamente");
        assertNull(DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString));

    }

    @Test
    public void EliminarCita() {

        System.out.println("Crando Datos De Toma");
        LocalDateTime ahora = LocalDateTime.now(), semanaSiguiente = ahora.plusWeeks(1);
        String ahoraString = ahora.toString();
        String semanaSiguienteString = semanaSiguiente.toString();

        DatosToma dt = new DatosToma(
                "00000001X", //dni
                "32", //Peso
                "30", //imc
                "foto.jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                ahora, //fecha toma
                semanaSiguiente);//Fecha proxima cita
        System.out.println("Insertando datos de toma");
        DAODatosToma.insertarDatosToma(dt);

        System.out.println("Comprobando que se ha insertado correctamente");

        assertNotNull(DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString));

        DAODatosToma.EliminarProximaCita(dt);

        System.out.println("Comprobando que se ha eliminado la cita");

        DatosToma dtrec = DAODatosToma.buscarUsuarioFechaToma("00000001X", ahoraString);
        assertNull(dtrec.getProximaCita());
    }
}
