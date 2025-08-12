/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class UtilHelper {

    public static double calcularIMC(double Peso, int altura) {
        double imc;
        if (altura > 0 && Peso > 0) {
            Double alt = (double) altura;
            Double alt2 = (alt / 100) * (alt / 100);

            imc = Peso / alt2;

        } else {
            imc = 0;
            System.err.println("El valor no de la altura no puede ser <=0 ");
        }

        return imc;
    }

    public static double calcularPesoIdeal(int altura) {
        double pesoIdeal;
        if (altura > 0) {
            Double alt = (double) altura;
            Double alt2 = (alt / 100) * (alt / 100);

            pesoIdeal = 22.00 * alt2;

        } else {
            pesoIdeal = 0;
            System.err.println("El valor no de la altura no puede ser <=0 ");
        }

        return pesoIdeal;
    }

    /**
     * Comprueba si existe una ruta en el sistema de archivos
     *
     * @param rutaStr Ruta a comprobar
     * @return true si la ruta existe, false en caso contrario
     * @implNote Muestra un mensaje por consola indicando si la ruta existe o no
     */
    public static boolean ComprobarRuta(String rutaStr) {
        File carpeta = new File(rutaStr);
        boolean exists = carpeta.exists();

        String mensaje = (exists) ? "La ruta Existe" : "La ruta No existe";
        System.out.println(mensaje);

        return exists;
    }

    /**
     * Crea una carpeta en la ruta especificada, incluyendo todas las carpetas
     * padre necesarias
     *
     * @param rutaStr Ruta completa de la carpeta a crear
     * @return true si la carpeta fue creada o ya existía, false si no se pudo
     * crear
     * @implNote Muestra mensajes por consola sobre el estado de la operación
     */
    public static boolean CrearCarpetaRuta(String rutaStr) {
        boolean creada = false;
        File carpeta = new File(rutaStr);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println(" Carpeta creada: " + rutaStr);
                creada = true;
                return creada;
            } else {
                System.err.println("No se pudo crear la carpeta:" + rutaStr);
                return creada;
            }
        }
        System.out.println("La carpeta/Ruta ya existe.");
        return creada;
    }

    /**
     * Elimina una ruta de directorio si está vacía
     *
     * @param rutaStr Ruta del directorio a eliminar
     * @return true si se eliminó correctamente, false si no estaba vacía o no
     * existía
     * @implNote Verifica que el directorio esté vacío antes de eliminarlo
     * @throws IOException Si ocurre un error al acceder al sistema de archivos
     */
    public static boolean EliminarRutaVacia(String rutaStr) {
        boolean eliminado = false;
        Path ruta = Paths.get(rutaStr);

        try {
            if (Files.exists(ruta) && Files.isDirectory(ruta)) {
                // Verificar si está vacía
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(ruta)) {
                    if (!stream.iterator().hasNext()) {
                        Files.delete(ruta);
                        System.out.println("Directorio eliminado: " + ruta.toAbsolutePath());
                        eliminado = true;
                        return eliminado;
                    } else {
                        System.out.println("No se puede eliminar, la carpeta no está vacía.");
                        return eliminado;
                    }
                }
            } else {
                System.out.println("La ruta no existe o no es un directorio.");
                return eliminado;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eliminado;
    }

    /**
     * Elimina recursivamente una ruta y todo su contenido (archivos y
     * subdirectorios)
     *
     * @param rutaStr Ruta a eliminar
     * @return true si se eliminó correctamente, false si no existía o hubo un
     * error
     * @implNote Elimina todos los archivos y subdirectorios de forma recursiva
     * @throws IOException Si ocurre un error al acceder al sistema de archivos
     */
    public static boolean EliminarRuta(String rutaStr) {
        boolean eliminado = false;
        Path ruta = Paths.get(rutaStr);

        try {
            if (Files.exists(ruta)) {
                Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file); // Eliminar archivo
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir); // Eliminar carpeta después de vaciarla
                        return FileVisitResult.CONTINUE;
                    }
                });
                System.out.println("Carpeta eliminada: " + ruta.toAbsolutePath());
                eliminado = true;
                return eliminado;

            } else {
                System.out.println("La ruta no existe.");
                return eliminado;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eliminado;
    }

    public static void EliminarFichero(String ruta, String nombreFichero) {
        Path pathFichero = Paths.get(ruta, nombreFichero);

        try {
            if (Files.deleteIfExists(pathFichero)) {
                System.out.println("Fichero eliminado: " + pathFichero.toAbsolutePath());
            } else {
                System.out.println("El fichero no existe: " + pathFichero.toAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("No se pudo eliminar: " + e.getMessage());
        }
    }

}
