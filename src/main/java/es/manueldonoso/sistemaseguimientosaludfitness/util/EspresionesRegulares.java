package es.manueldonoso.sistemaseguimientosaludfitness.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Manuel Jesús Donoso Pérez
 */
public class  EspresionesRegulares {

    /**
     * Valida una dirección de email usando expresiones regulares
     *
     * @param email La dirección de email a validar
     * @return true si el email es válido, false en caso contrario
     * @throws IllegalArgumentException si el email es null
     */
    public static boolean validarEmail(String email) {
        if (email == null) {
            return false;
        }

        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    /**
     * Valida si una cadena contiene únicamente letras (Unicode) y espacios.
     *
     * @param texto Cadena a validar. Puede ser {@code null}.
     * @return {@code true} si la cadena no es {@code null} y solo contiene
     * letras y espacios; {@code false} en caso contrario, incluso para cadenas
     * vacías.
     */
    public static boolean soloLetras(String texto) {
        return texto != null && texto.matches("^[\\p{L} ]+$");
    }

    /**
     * Valida si una cadena contiene únicamente letras (Unicode) y espacios,
     * asegurando un número mínimo de letras.
     *
     * @param texto Cadena a validar. Puede ser {@code null}.
     * @param min Número mínimo de letras requeridas (debe ser ≥ 1).
     * @return {@code true} si la cadena no es {@code null}, no está vacía,
     * cumple con el mínimo de letras y solo contiene letras y espacios;
     * {@code false} en caso contrario.
     * @throws IllegalArgumentException Si {@code min} es menor que 1.
     */
    public static boolean soloLetras(String texto, int min) {
        if (min < 1) {
            throw new IllegalArgumentException("El mínimo de letras debe ser ≥ 1");
        }
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        String lettersOnlyRegex = "^(?=(.*\\p{L}){" + min + "})[\\p{L} ]+$";
        return texto.matches(lettersOnlyRegex);
    }

    /**
     * Valida si una cadena cumple con el formato de un DNI español válido (8
     * dígitos + letra de control).
     * <p>
     * <b>Formato aceptado:</b> 8 dígitos seguidos de una letra (A-H, J-N, P-T,
     * V-Z), excluyendo Ñ, O, U, I.
     *
     * @param dni Cadena a validar. Puede ser {@code null}.
     * @return {@code true} si la cadena no es {@code null} y coincide con el
     * formato de DNI español; {@code false} en caso contrario.
     */
    public static boolean validarDNIEspana(String dni) {
        return dni != null && dni.matches("^\\d{8}[A-HJ-NP-TV-Z]$");
    }
}
