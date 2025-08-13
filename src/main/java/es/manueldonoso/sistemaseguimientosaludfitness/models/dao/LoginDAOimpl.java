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

    private static Connection conn;

    public LoginDAOimpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        LoginDAOimpl.conn = conn;
    }

    @Override
    public void insertar(Login login) {

        String sql = "INSERT INTO login(usuario,pass) VALUES (?,?)";

        //cifrar la contraseña
        String hashpass = Seguridad.hashSHA256(login.getPass());

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login.getUsuario());
            pstmt.setString(2, hashpass);

            pstmt.executeUpdate();
            System.out.println("Usuario " + login.getUsuario() + "  creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eleminar(Login login) {

        String sql = "DELETE FROM login WHERE usuario = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

        String sql = "UPDATE login SET pass = ? WHERE usuario = ?";

        // Cifrar la nueva contraseña
        String hashNuevaPass = Seguridad.hashSHA256(nuevaPass);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
    public boolean verificarLogin(Login login) {
        String hashpass = Seguridad.hashSHA256(login.getPass());

        String sql = "SELECT pass FROM login WHERE usuario = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login.getUsuario());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("pass");
//                System.out.println("Hash almacenado: " + storedHash);
//                System.out.println("Hash proporcionado: " + hashpass);
                return storedHash.equals(hashpass);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean usuarioExiste(String Usuario
    ) {

        String sql = "SELECT usuario, pass  FROM  login WHERE usuario = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Usuario);

            return pstmt.executeQuery().next(); //true si existe
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
