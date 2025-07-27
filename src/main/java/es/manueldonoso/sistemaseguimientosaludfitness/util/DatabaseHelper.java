/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAOimpl;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.ClienteDAOImpl;
import java.io.File;
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

    //ruta completa donde se guardara la base de datos
    private static final String DB_FOLDER = "data/databases";
    private static final String DB_NAME = "datos.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;
    private static String url = "jdbc:sqlite:" + DB_PATH;
    private static Connection sharedConnection;

    public static void main(String[] args) {
        crearCarpetaSiNoExite();
        crearBaseDatos();
        LoginDAOimpl DAO = new LoginDAOimpl();

        if (!DAO.usuarioExiste("admin")) {
            Login login = new Login("admin", "admin");
            DAO.insertar(login);
        }

        crearTablasdefault();
        //borrar en produccion
        Connection conn = conectarddbb();
        if (esTablaUsuariosVacia(conn)) {

            insertarUsuariosPorDefecto(conn);
        }

    }

    /**
     * crea la carperta Data si no existe
     */
    private static void crearCarpetaSiNoExite() {
        File carpeta = new File(DB_FOLDER);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println(" Carpeta creada: " + DB_FOLDER);

            } else {
                System.err.println("No se pudo crear la carpeta:" + DB_FOLDER);
            }
        }

    }

    /**
     * crea la base de datos y su tabla login si no existe
     */
    public static void crearBaseDatos() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                stmt.execute(" CREATE TABLE IF NOT EXISTS login("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "usuario TEXT NOT NULL, "
                        + "pass TEXT NOT NULL)");

                System.out.println("Base de datos creada correctamente en : " + url.substring(12));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Connection conectarddbb() {

        try {
            sharedConnection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.getLogger(DatabaseHelper.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            sharedConnection = null;
        }

        return sharedConnection;

    }

    public static void addUserLogin(String usuario, String pass) {

        String sql = "INSERT INTO login(usuario,pass) VALUES (?,?)";

        //cifrar la contraseña
        String hashpass = Seguridad.hashSHA256(pass);

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, hashpass);

            pstmt.executeUpdate();
            System.out.println("Usuario " + usuario + "  creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verificarLogin(String Usuario, String pass) {

        String hashpass = Seguridad.hashSHA256(pass);
        String sql = "SELECT usuario, pass  FROM  login WHERE usuario = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Usuario);
            pstmt.setString(2, hashpass);

            return pstmt.executeQuery().next(); //true si existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verificarUsuarioExiste(String Usuario) {

        String sql = "SELECT usuario, pass  FROM  login WHERE usuario = ? ";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Usuario);

            return pstmt.executeQuery().next(); //true si existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void crearTablasdefault() {
        String sqlUsuario = " CREATE TABLE IF NOT EXISTS clientes ("
                + "dni TEXT PRIMARY KEY, nombre TEXT, apellido1 TEXT, apellido2 TEXT, sexo TEXT, fnacimiento TEXT, altura REAL, peso REAL, imc REAL,"
                + "dirFoto TEXT, direccion TEXT,  poblacion TEXT, cp TEXT, tel TEXT, grasac REAL, proteina REAL, metabolismoV REAL,"
                + " grasaVis REAL, pesoIdeal REAL,anotaciones TEXT, fAlta TEXT); ";
        String sqldietaSemana = " CREATE TABLE IF NOT EXISTS dietaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT );";

        String sqlrutinaSemana = " CREATE TABLE IF NOT EXISTS rutinaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT  ); ";

        String sqlSeguimiento = "  CREATE TABLE IF NOT EXISTS seguimiento ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkUsuario TEXT, fechaSeguimiento TEXT, nuevoPeso REAL, nuevoImc REAL, fk_dietaSemanal INTEGER, fk_rutinaSemanal INTEGER, FOREIGN KEY (fkUsuario) REFERENCES usuarios(dni), FOREIGN KEY (fk_dietaSemanal) REFERENCES dietaSemanal(id), FOREIGN KEY (fk_rutinaSemanal) REFERENCES rutinaSemanal(id) ); ";

        String sqlDietaDia = " CREATE TABLE IF NOT EXISTS dietaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkDietaSemanal INTEGER,  desayuno TEXT,  mediaManana TEXT, comida TEXT, merienda TEXT,  cena TEXT, FOREIGN KEY (fkDietaSemanal) REFERENCES dietaSemanal(id) ); ";

        String sqlRutinaDia = " CREATE TABLE IF NOT EXISTS rutinaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkRutinaSemanal INTEGER, nombreEjercicio TEXT, repeticiones INTEGER, series INTEGER, peso REAL, tiempo TEXT,  urlImagen TEXT, FOREIGN KEY (fkRutinaSemanal) REFERENCES rutinaSemanal(id)  ); ";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuario);
            stmt.execute(sqldietaSemana);
            stmt.execute(sqlrutinaSemana);
            stmt.execute(sqlSeguimiento);
            stmt.execute(sqlDietaDia);
            stmt.execute(sqlRutinaDia);

            System.out.println("creada las tablas correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean esTablaUsuariosVacia(Connection conn) {
        String sql = "SELECT COUNT(*) FROM clientes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // En caso de error o tabla no encontrada
    }

    public static void insertarUsuariosPorDefecto(Connection conn) {
        ClienteDAOImpl dao = new ClienteDAOImpl(conn);
        List<Cliente> usuarios = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            usuarios.add(new Cliente(
                    "0000000" + i + "X",
                    "Nombre" + i,
                    "Apellido1_" + i,
                    "Apellido2_" + i,
                    i % 2 == 0 ? "Hombre" : "Mujer",
                    "1.75", "75", "24.5",
                    "foto" + i + ".jpg",
                    "Calle Falsa " + i,
                    "Ciudad" + i,
                    "2800" + i,
                    "60000000" + i,
                    "15", "20", "1600", "12",
                    "70", "Sin anotaciones",
                    LocalDate.of(1990, 1, i),
                    LocalDateTime.now()
            ));
        }

        for (Cliente u : usuarios) {
            dao.insertar(u);
        }

        System.out.println("9 usuarios insertados con éxito.");
    }

    public static void setUrl(String url) {
        DatabaseHelper.url = url;
    }

    public static String getUrl() {
        return url;
    }

    public static Connection getSharedConnection() {
        return sharedConnection;
    }

    public static void setSharedConnection(Connection sharedConnection) {
        DatabaseHelper.sharedConnection = sharedConnection;
    }

    public static boolean sharedConnectionIsClose() throws SQLException {
        return sharedConnection.isClosed();
    }

    public static void SharedConnectionClose() throws SQLException {
        sharedConnection.close();
    }

    public static void forceCloseAllConnections() {
        try {
            // Cierra la conexión compartida si existe
            if (sharedConnection != null && !sharedConnection.isClosed()) {
                sharedConnection.close();
            }

        } catch (SQLException e) {
            System.err.println("Error forzando cierre de conexiones: " + e.getMessage());
        }
    }

    public static ResultSet resultSet(String query) throws SQLException {
        PreparedStatement stmt = sharedConnection.prepareStatement(query);
        return stmt.executeQuery();
    }

}
