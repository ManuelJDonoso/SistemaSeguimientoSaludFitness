/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class ClienteDAOImpl implements ClienteDAO {

    private Connection conn;

    public ClienteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public boolean estaConectado() {
        return null != this.conn;
    }

    @Override
    public void insertar(Cliente u) {

        String sqlCliente = "INSERT INTO clientes (dni, nombre, apellido1, apellido2, sexo,"
                + " fnacimiento, altura,  dirFoto, direccion, poblacion, cp, tel,"
                + "  PesoIdeal, anotaciones, fAlta,email ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido1());
            ps.setString(4, u.getApellido2());
            ps.setString(5, u.getSexo());

            // Convertir LocalDate a java.sql.Date
            ps.setString(6, u.getFechaNacimiento().toString());

            ps.setString(7, u.getAltura());

            ps.setString(8, u.getDirFoto());
            ps.setString(9, u.getDireccion());
            ps.setString(10, u.getPoblacion());
            ps.setString(11, u.getCp());
            ps.setString(12, u.getTelefono());

            ps.setString(13, u.getPesoIdeal());
            ps.setString(14, u.getAnotaciones());

            // Convertir LocalDate a java.sql.Date para fechaAlta
            ps.setString(15, u.getFechaAlta().toString());
            ps.setString(16, u.getEmail());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al insertar usuario: " + ex.getMessage());
            // Puedes lanzar una excepción personalizada aquí si lo prefieres
        }

    }

    @Override
    public void insertar(Cliente u, DatosToma d) {
        String sqlCliente = "INSERT INTO clientes (dni, nombre, apellido1, apellido2, sexo,"
                + " fnacimiento, altura,  dirFoto, direccion, poblacion, cp, tel,"
                + "  PesoIdeal,anotaciones, fAlta,email ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DatosTomaDAOImpl datosTomasDaoimp = new DatosTomaDAOImpl(conn);

        try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido1());
            ps.setString(4, u.getApellido2());
            ps.setString(5, u.getSexo());

            // Convertir LocalDate a java.sql.Date
            ps.setString(6, u.getFechaNacimiento().toString());

            ps.setString(7, u.getAltura());

            ps.setString(8, u.getDirFoto());
            ps.setString(9, u.getDireccion());
            ps.setString(10, u.getPoblacion());
            ps.setString(11, u.getCp());
            ps.setString(12, u.getTelefono());

            ps.setString(13, u.getPesoIdeal());
            ps.setString(14, u.getAnotaciones());

            // Convertir LocalDate a java.sql.Date para fechaAlta
            ps.setString(15, u.getFechaAlta().toString());
            ps.setString(16, u.getEmail());
            datosTomasDaoimp.insertarDatosToma(d);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al insertar usuario: " + ex.getMessage());
            // Puedes lanzar una excepción personalizada aquí si lo prefieres
        }
    }

    @Override
    public Cliente obtenerPorDni(String dni) {

        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío");
        }

        String sql = "SELECT dni, nombre, apellido1, apellido2, sexo, fnacimiento, altura, "
                + "dirFoto, direccion, poblacion, cp, tel, "
                + "pesoIdeal, anotaciones, fAlta, email FROM clientes WHERE dni = ?";

        Cliente cliente = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String fechaStr = rs.getString("fnacimiento");
                    LocalDate fechaParseadaNacimiento = LocalDate.parse(fechaStr);

                    String fechaHoraStr = rs.getString("fAlta");
                    LocalDateTime fechaHoraParseadaFAlta = LocalDateTime.parse(fechaHoraStr);

                    cliente = new Cliente(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            rs.getString("sexo"),
                            rs.getString("altura"),
                            rs.getString("dirFoto"),
                            rs.getString("direccion"),
                            rs.getString("poblacion"),
                            rs.getString("cp"),
                            rs.getString("tel"),
                            rs.getString("pesoIdeal"),
                            rs.getString("anotaciones"),
                            fechaParseadaNacimiento,
                            fechaHoraParseadaFAlta,
                            rs.getString("email")
                    );

                }
            }
        } catch (Exception e) {  // Loggear el error o relanzar una excepción personalizada
            System.err.println("Error al crear el cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return cliente;

    }

    @Override
    public List<Cliente> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int actualizarCliente(Cliente old, Cliente New) {
        int filasActualizadas = 0;
        String sql = "UPDATE clientes SET "
                + "dni= ?, nombre = ?, apellido1 = ?, apellido2 = ?, "
                + "sexo = ?, "
                + "fnacimiento = ?,  altura = ?, "
                + "dirFoto = ?, "
                + "direccion = ?, poblacion = ?, cp = ?, tel = ?, "
                + "PesoIdeal = ?, "
                + "anotaciones = ?, "
                + "fAlta = ?, "
                + "email = ?  "
                + "WHERE dni = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!existeCliente(old)) {
                throw new NoSuchElementException("No se encontró el cliente con DNI: " + old.getDni() + ", no se procede a modificar.");
            }

            // Establecer parámetros
            ps.setString(1, New.getDni());
            ps.setString(2, New.getNombre());
            ps.setString(3, New.getApellido1());
            ps.setString(4, New.getApellido2());
            ps.setString(5, New.getSexo());
            ps.setString(6, New.getFechaNacimiento().toString());
            ps.setString(7, New.getAltura());
            ps.setString(8, New.getDirFoto());
            ps.setString(9, New.getDireccion());
            ps.setString(10, New.getPoblacion());
            ps.setString(11, New.getCp());
            ps.setString(12, New.getTelefono());
            ps.setString(13, New.getPesoIdeal());
            ps.setString(14, New.getAnotaciones());
            ps.setString(15, New.getFechaAlta().toString());
            ps.setString(16, New.getEmail());

            ps.setString(17, old.getDni());

            filasActualizadas = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return filasActualizadas;
    }

    @Override
    public boolean existeCliente(String dni) {
        boolean existe = false;
        String sql = "SELECT dni FROM clientes WHERE dni = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                existe = true;
            }

        } catch (Exception e) {
            e.getCause();
        }

        return existe;

    }

    @Override
    public boolean existeCliente(Cliente c) {

        String dni = c.getDni();
        boolean existe = false;
        String sql = "SELECT dni FROM clientes WHERE dni = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                existe = true;
            }

        } catch (Exception e) {
            e.getCause();
        }

        return existe;

    }

    @Override
    public void eliminarTodosUsuarios() {
        String sql = "DELETE FROM clientes";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int FilasBorradas = pstmt.executeUpdate();
            String mensaje = (FilasBorradas == 1) ? "Se Han borrado " + FilasBorradas + " cliente" : "Se Han borrado " + FilasBorradas + " clientes";
            System.out.println(mensaje);

        } catch (Exception e) {
            e.getCause();
        }

    }

    @Override
    public void eliminarUsuario(String dni) {

        String sql = "DELETE FROM clientes WHERE dni = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            int FilasBorradas = pstmt.executeUpdate();
            String mensaje = (FilasBorradas == 1) ? "Se Han borrado " + FilasBorradas + " cliente" : "Se Han borrado " + FilasBorradas + " clientes";
            System.out.println(mensaje);

        } catch (Exception e) {
            e.getCause();
        }
    }

}
