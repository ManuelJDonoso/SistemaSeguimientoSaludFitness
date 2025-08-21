/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAOimpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.DatosTomaDAOImpl;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class DatabaseHelper {

    private static String rutaStr; // ruta de carpeta
    private static String nombreBD;     // nombre del fichero
    private static Connection sharedConnection;

    /**
     * Obtiene la ruta de la base de datos
     *
     * @return String con la ruta de la base de datos
     */
    public static String getRutaStr() {
        return rutaStr;
    }

    /**
     * Establece la ruta de la base de datos
     *
     * @param rutaStr String con la ruta a establecer
     */
    public static void setRutaStr(String rutaStr) {
        DatabaseHelper.rutaStr = rutaStr;
    }

    /**
     * Obtiene el nombre de la base de datos
     *
     * @return String con el nombre de la base de datos
     */
    public static String getNombreBD() {
        return nombreBD;
    }

    /**
     * Establece el nombre de la base de datos
     *
     * @param nombreBD String con el nombre a establecer
     */
    public static void setNombreBD(String nombreBD) {
        DatabaseHelper.nombreBD = nombreBD;
    }

    /**
     * Inicializa la base de datos SQLite en la ruta especificada
     *
     * @param rutaString Ruta donde se almacenará la base de datos
     * @param ficheroString Nombre del fichero de la base de datos
     * @throws IllegalArgumentException si la ruta o nombre son nulos/vacíos
     * @throws RuntimeException si no se puede crear la carpeta de destino
     */
    public static void InitBaseDatosSQLite(String rutaString, String ficheroString) {
        rutaStr = rutaString;
        nombreBD = ficheroString;

        if (rutaString == null || rutaString.trim().isEmpty()
                || ficheroString == null || ficheroString.trim().isEmpty()) {
            throw new IllegalArgumentException("Ruta y nombre de fichero no pueden ser nulos o vacíos");
        }

        if (!ComprobarRuta(rutaStr)) {
            if (!CrearCarpetaRuta(rutaStr)) {
                throw new RuntimeException("No se pudo crear la carpeta: " + rutaStr);
            }
        }

        String url = "jdbc:sqlite:" + Paths.get(rutaStr, nombreBD).toString();
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Base de datos creada: " + url);
                sharedConnection = conn;
            }
        } catch (SQLException e) {
            System.err.println("Error creando base de datos: " + e.getMessage());
        }
    }

    /**
     * Comprueba si existe una ruta determinada
     *
     * @param rutaStr Ruta a comprobar
     * @return true si la ruta existe y es un directorio, false en caso
     * contrario
     */
    private static boolean ComprobarRuta(String rutaStr) {
        File carpeta = new File(rutaStr);
        return carpeta.exists() && carpeta.isDirectory();
    }

    /**
     * Crea una carpeta en la ruta especificada
     *
     * @param rutaStr Ruta donde crear la carpeta
     * @return true si se creó correctamente o ya existía, false en caso
     * contrario
     */
    private static boolean CrearCarpetaRuta(String rutaStr) {
        File carpeta = new File(rutaStr);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("Carpeta creada: " + rutaStr);
                return true;
            } else {
                System.err.println("No se pudo crear la carpeta: " + rutaStr);
                return false;
            }
        }
        System.out.println("La carpeta/Ruta ya existe.");
        return true;
    }

    /**
     * Obtiene una conexión a la base de datos
     *
     * @return Connection objeto de conexión
     * @throws SQLException si ocurre un error al conectar
     * @throws IllegalStateException si no se ha inicializado el DatabaseHelper
     */
    public static Connection getConnection() throws SQLException {
        if (rutaStr == null || nombreBD == null) {
            throw new IllegalStateException("DatabaseHelper no inicializado. Llama a init() primero.");
        }
        String url = "jdbc:sqlite:" + getDatabasePath();
        return DriverManager.getConnection(url);
    }

    /**
     * Obtiene la ruta completa de la base de datos
     *
     * @return String con la ruta completa (ruta + nombre)
     */
    private static String getDatabasePath() {
        return Paths.get(rutaStr, nombreBD).toString();
    }

    /**
     * Crea las tablas por defecto en la base de datos
     *
     * @param Connection Conexión a la base de datos donde crear las tablas
     */
    public static void crearTablasdefault(Connection connection) {
        String sqllogin = " CREATE TABLE IF NOT EXISTS login("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "usuario TEXT NOT NULL, "
                + "pass TEXT NOT NULL)";

        String sqlUsuario = " CREATE TABLE IF NOT EXISTS clientes ("
                + "dni TEXT PRIMARY KEY, nombre TEXT, apellido1 TEXT, apellido2 TEXT, sexo TEXT, fnacimiento TEXT, altura REAL, "
                + "dirFoto TEXT, direccion TEXT,  poblacion TEXT, cp TEXT, tel TEXT,"
                + " pesoIdeal REAL,anotaciones TEXT, fAlta TEXT,email TEXT); ";
        String sqldietaSemana = " CREATE TABLE IF NOT EXISTS dietaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT );";

        String sqlrutinaSemana = " CREATE TABLE IF NOT EXISTS rutinaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT  ); ";

        String sqlDatoToma = "  CREATE TABLE IF NOT EXISTS datosToma ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, fkCliente TEXT, fechaToma TEXT, fechaProximaCita TEXT,"
                + "peso TEXT,imc TEXT,dirFoto TEXT, grasac TEXT, proteina TEXT, metabolismoV TEXT, grasaV TEXT,"
                + " FOREIGN KEY (fkCliente) REFERENCES  clientes(dni))";

        String sqlDietaDia = " CREATE TABLE IF NOT EXISTS dietaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkDietaSemanal INTEGER,  desayuno TEXT,  mediaManana TEXT, comida TEXT, merienda TEXT,  cena TEXT, FOREIGN KEY (fkDietaSemanal) REFERENCES dietaSemanal(id) ); ";

        String sqlRutinaDia = " CREATE TABLE IF NOT EXISTS rutinaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkRutinaSemanal INTEGER, nombreEjercicio TEXT, repeticiones INTEGER, series INTEGER, peso REAL, tiempo TEXT,  urlImagen TEXT, FOREIGN KEY (fkRutinaSemanal) REFERENCES rutinaSemanal(id)  ); ";

        try (Connection conn = connection; Statement stmt = conn.createStatement()) {

            stmt.execute(sqllogin);
            stmt.execute(sqlUsuario);
            stmt.execute(sqldietaSemana);
            stmt.execute(sqlrutinaSemana);
            stmt.execute(sqlDatoToma);
            stmt.execute(sqlDietaDia);
            stmt.execute(sqlRutinaDia);

            System.out.println("creada las tablas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existeTabla(String nombreTabla, Connection connection) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, nombreTabla, null)) {
          
            return rs.next();
        }
    }


    public static void insertar10Clientes(Connection conn){
    
        ClienteDAOImpl DAOCliente = new ClienteDAOImpl(conn);
        List <Cliente>  clientes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clientes.add(new Cliente(
                    "000000" + i + "X",
                    "nombre" + i,
                    "Apellido1_" + i,
                    "Apellido2_" + i,
                    i % 2 == 0 ? "Hombre" : "Mujer",
                    "17" + i,
                    "imagen" + i + ".jpg",
                    "direccion" + i,
                    "ciudad " + i,
                    "1011" + i,
                    "65535431" + i,
                    "7" + 1,
                    "anotaciones",
                    LocalDate.of(1990, 1, i + 1),
                    LocalDateTime.now(),
                    "email" + i + "@example.com"));
        }
        for (Cliente c : clientes) {
            DAOCliente.insertar(c);
        }
        System.out.println("Se han insertado 10 Clientes");
  
    }
    
     public static void insertar10Clientes30Tomas(Connection conn){
    
        ClienteDAOImpl DAOCliente = new ClienteDAOImpl(conn);
        DatosTomaDAOImpl DAODatosToma= new DatosTomaDAOImpl(conn);
        List <Cliente>  clientes = new ArrayList<>();
        List <DatosToma> DatoTomas= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clientes.add(new Cliente(
                    "000000" + i + "X",
                    "nombre" + i,
                    "Apellido1_" + i,
                    "Apellido2_" + i,
                    i % 2 == 0 ? "Hombre" : "Mujer",
                    "17" + i,
                    "imagen" + i + ".jpg",
                    "direccion" + i,
                    "ciudad " + i,
                    "1011" + i,
                    "65535431" + i,
                    "7" + 1,
                    "anotaciones",
                    LocalDate.of(1990, 1, i + 1),
                    LocalDateTime.now(),
                    "email" + i + "@example.com"));
            
            DatoTomas.add(
                    new DatosToma(
                "000000" + i + "X", //dni
                "32", //Peso
                "30", //imc
                "foto"+LocalDateTime.now().toString()+".jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                LocalDateTime.now().minusWeeks(1), //fecha toma
                LocalDateTime.now())//Fecha proxima cita
            
            );
            
            DatoTomas.add(
                    new DatosToma(
                "000000" + i + "X", //dni
                "32", //Peso
                "30", //imc
                "foto"+LocalDateTime.now().toString()+".jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                LocalDateTime.now(), //fecha toma
                LocalDateTime.now().plusWeeks(1))//Fecha proxima cita
            
            );
             DatoTomas.add(
                    new DatosToma(
                "000000" + i + "X", //dni
                "32", //Peso
                "30", //imc
                "foto"+LocalDateTime.now().toString()+".jpg", //dirFoto
                "12", //grasac
                "14", //proteina
                "2000", //metavolismov
                "13", //grasav
                LocalDateTime.now().plusWeeks(1), //fecha toma
                LocalDateTime.now().plusWeeks(2))//Fecha proxima cita
            
            );
            
        }
        for (Cliente c : clientes) {
            DAOCliente.insertar(c);
        }
        
        for (DatosToma dt : DatoTomas){
            System.out.println(dt.toString());
            DAODatosToma.insertarDatosToma(dt);
        }
        System.out.println("Se han insertado 10 Clientes y 30 tomas ");
  
    }
}
