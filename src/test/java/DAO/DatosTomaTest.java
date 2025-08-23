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
 * Clase de pruebas unitarias para {@link DatosTomaDAOImpl}, que gestiona las
 * operaciones CRUD sobre la entidad {@link DatosToma}.
 * <p>
 * Se encarga de verificar la correcta inserción, modificación, eliminación y
 * consulta de registros relacionados con datos de toma de clientes.
 * </p>
 *
 * <p>
 * Los métodos de prueba utilizan una base de datos en memoria creada con
 * {@link DatabaseHelper} y gestionan clientes y datos de toma a través de
 * {@link ClienteDAOImpl} y {@link DatosTomaDAOImpl}.</p>
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class DatosTomaTest extends BaseTest {

    /**
     * DAO para gestión de clientes.
     */
    private ClienteDAOImpl DAOCliente;

    /**
     * Lista de clientes usada en las pruebas.
     */
    private List<Cliente> clientes;

    /**
     * DAO para gestión de datos de toma.
     */
    private DatosTomaDAOImpl DAODatosToma;

    /**
     * Lista de tomas usadas en las pruebas.
     */
    private List<DatosToma> tomas;

    public DatosTomaTest() {
    }

    /**
     * Configura el entorno antes de cada prueba:
     * <ul>
     * <li>Crea las tablas por defecto.</li>
     * <li>Inicializa la conexión a la base de datos.</li>
     * <li>Instancia los DAOs de Cliente y DatosToma.</li>
     * </ul>
     */
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

    /**
     * Limpia el entorno tras cada prueba eliminando todos los registros de
     * clientes y datos de toma.
     */
    @AfterEach
    public void tearDown() {
        DAOCliente.eliminarTodosUsuarios();
        DAODatosToma.EliminarTodosDatosTomas();

    }

    /**
     * Verifica que se pueden insertar datos de toma correctamente y
     * recuperarlos mediante consultas por fecha de toma y fecha de próxima
     * cita.
     */
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

    /**
     * Verifica que se listan todos los registros de datos de toma, comprobando
     * que se insertan 30 tomas para 10 clientes de prueba.
     */
    @Test
    public void ListarTodas() {
        System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        List lista = DAODatosToma.ListarTodosDatos();

        System.out.println("Comprobando si la lista tiene un tamaño de 30");
        assertEquals(30, lista.size());
    }

    /**
     * Verifica que se listan los datos de toma de un cliente en particular,
     * comprobando que un cliente específico tiene 3 registros.
     */
    @Test
    public void ListarFechaUsuario() {
        String dni = "0000000X";
        System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        List lista = DAODatosToma.ListarDatosTomaCliente(dni);

        System.out.println("Comprobando si la lista obtenida del usuario " + dni + " tiene un tamaño de 3");
        assertEquals(3, lista.size());
    }

    /**
     * Verifica que se pueden listar los datos de toma por fecha de toma,
     * comprobando que se obtienen las 10 entradas correspondientes al día
     * actual.
     */
    @Test
    public void ListarPorFechaToma() {
        String fecha = LocalDate.now().toString();
        System.out.println("Insertar 10 usuario con un total de 30 datos de Tomas");
        DatabaseHelper.insertar10Clientes30Tomas(conn);
        System.out.println(fecha);
        List lista = DAODatosToma.ListarDatosFecha(fecha);
        assertEquals(10, lista.size());

    }

    /**
     * Verifica la funcionalidad de modificación de un registro de datos de
     * toma, comprobando que los cambios realizados se almacenan correctamente.
     */
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

    /**
     * Verifica que se puede eliminar un registro completo de datos de toma,
     * asegurando que no se puede recuperar posteriormente.
     */
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

    /**
     * Verifica que se puede eliminar la fecha de la próxima cita de un registro
     * de datos de toma, manteniendo el resto de la información.
     */
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
