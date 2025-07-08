/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
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

                stmt.execute( " CREATE TABLE IF NOT EXISTS login("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "usuario TEXT NOT NULL, "
                        + "pass TEXT NOT NULL)"  );

                System.out.println("Base de datos creada correctamente en : " + DB_PATH);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    

}
