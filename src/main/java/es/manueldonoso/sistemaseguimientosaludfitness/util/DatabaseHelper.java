/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author donpe
 */
public class DatabaseHelper {

    //ruta completa donde se guardara la base de datos
    private static final String DB_FOLDER = "data/databases";
    private static final String DB_NAME = "datos.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;

    public static void main(String[] args) {
        crearCarpetaSiNoExite();
        crearBaseDatos();
        if (!verificarUsuarioExiste("admin")) {
            addUserLogin("admin", "admin");
        }
        crearTablasdefault();

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
    private static void crearBaseDatos() {
        String url = "jdbc:sqlite:" + DB_PATH;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                stmt.execute(" CREATE TABLE IF NOT EXISTS login("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "usuario TEXT NOT NULL, "
                        + "pass TEXT NOT NULL)");

                System.out.println("Base de datos creada correctamente en : " + DB_PATH);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void addUserLogin(String usuario, String pass) {
        String url = "jdbc:sqlite:" + DB_PATH;
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
        String url = "jdbc:sqlite:" + DB_PATH;
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
        String url = "jdbc:sqlite:" + DB_PATH;

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
        String sqlUsuario = " CREATE TABLE IF NOT EXISTS usuarios ("
                + "dni TEXT PRIMARY KEY, nombreApellido1 TEXT, apellido2 TEXT, sexo TEXT, fnacimiento TEXT, altura REAL, peso REAL, imc REAL,"
                + "dirFoto TEXT, direccion TEXT,  poblacion TEXT, cp TEXT, tel TEXT, grasac REAL, proteina REAL, metabolismoV REAL,"
                + " grasaVis REAL, pesoIdeal REAL ); ";
        String sqldietaSemana = " CREATE TABLE IF NOT EXISTS dietaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT );";

        String sqlrutinaSemana = " CREATE TABLE IF NOT EXISTS rutinaSemanal ( id INTEGER PRIMARY KEY AUTOINCREMENT  ); ";

        String sqlSeguimiento = "  CREATE TABLE IF NOT EXISTS seguimiento ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkUsuario TEXT, fechaSeguimiento TEXT, nuevoPeso REAL, nuevoImc REAL, fk_dietaSemanal INTEGER, fk_rutinaSemanal INTEGER, FOREIGN KEY (fkUsuario) REFERENCES usuarios(dni), FOREIGN KEY (fk_dietaSemanal) REFERENCES dietaSemanal(id), FOREIGN KEY (fk_rutinaSemanal) REFERENCES rutinaSemanal(id) ); ";

        String sqlDietaDia = " CREATE TABLE IF NOT EXISTS dietaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkDietaSemanal INTEGER,  desayuno TEXT,  mediaManana TEXT, comida TEXT, merienda TEXT,  cena TEXT, FOREIGN KEY (fkDietaSemanal) REFERENCES dietaSemanal(id) ); ";

        String sqlRutinaDia = " CREATE TABLE IF NOT EXISTS rutinaDia ( id INTEGER PRIMARY KEY AUTOINCREMENT, fkRutinaSemanal INTEGER, nombreEjercicio TEXT, repeticiones INTEGER, series INTEGER, peso REAL, tiempo TEXT,  urlImagen TEXT, FOREIGN KEY (fkRutinaSemanal) REFERENCES rutinaSemanal(id)  ); ";

        String url = "jdbc:sqlite:" + DB_PATH;
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

}
