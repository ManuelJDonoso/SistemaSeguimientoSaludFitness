/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.util.Seguridad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class LoginDAOimpl implements LoginDAO {

    //ruta completa donde se guardara la base de datos
    private static final String DB_FOLDER = "data/databases";
    private static final String DB_NAME = "datos.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;

    @Override
    public void insertar(Login login) {
        String url = "jdbc:sqlite:" + DB_PATH;
        String sql = "INSERT INTO login(usuario,pass) VALUES (?,?)";

        //cifrar la contraseña
        String hashpass = Seguridad.hashSHA256(login.getPass());

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login.getUsuario());
            pstmt.setString(2, hashpass);

            pstmt.executeUpdate();
            System.out.println("Usuario " + login.getUsuario() + "  creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertar(Login login, String url) {

        String sql = "INSERT INTO login(usuario,pass) VALUES (?,?)";

        //cifrar la contraseña
        String hashpass = Seguridad.hashSHA256(login.getPass());

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login.getUsuario());
            pstmt.setString(2, hashpass);

            pstmt.executeUpdate();
            System.out.println("Usuario " + login.getUsuario() + "  creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Login obtenerPorUsuario(String Usuario) {

        String url = "jdbc:sqlite:" + DB_PATH;
        String sql = "SELECT usuario, pass FROM login WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Login login = new Login();
                login.setUsuario(rs.getString("usuario"));
                login.setPass(rs.getString("pass"));
                return login;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Login obtenerPorUsuario(String Usuario, String url) {
        String sql = "SELECT usuario, pass FROM login WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Login login = new Login();
                login.setUsuario(rs.getString("usuario"));
                login.setPass(rs.getString("pass"));
                return login;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void eleminar(Login login) {
        String url = "jdbc:sqlite:" + DB_PATH;
        String sql = "DELETE FROM login WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login.getUsuario());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Usuario " + login.getUsuario() + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró el usuario " + login.getUsuario() + " para eliminar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eleminar(Login login, String url) {

        String sql = "DELETE FROM login WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login.getUsuario());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Usuario " + login.getUsuario() + " eliminado correctamente.");
            } else {
                System.out.println("No se encontró el usuario " + login.getUsuario() + " para eliminar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cambiarPass(Login login, String nuevaPass) {
        String url = "jdbc:sqlite:" + DB_PATH;
        String sql = "UPDATE login SET pass = ? WHERE usuario = ?";

        // Cifrar la nueva contraseña
        String hashNuevaPass = Seguridad.hashSHA256(nuevaPass);

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashNuevaPass);
            pstmt.setString(2, login.getUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Contraseña cambiada correctamente para el usuario: " + login.getUsuario());
            } else {
                System.out.println("No se encontró el usuario " + login.getUsuario() + " para cambiar la contraseña.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cambiarPass(Login login, String nuevaPass, String url) {

        String sql = "UPDATE login SET pass = ? WHERE usuario = ?";

        // Cifrar la nueva contraseña
        String hashNuevaPass = Seguridad.hashSHA256(nuevaPass);

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashNuevaPass);
            pstmt.setString(2, login.getUsuario());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Contraseña cambiada correctamente para el usuario: " + login.getUsuario());
            } else {
                System.out.println("No se encontró el usuario " + login.getUsuario() + " para cambiar la contraseña.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verificarLogin(String Usuario, String pass) {
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

    @Override
    public boolean verificarLogin(String Usuario, String pass, String url) {
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

}
