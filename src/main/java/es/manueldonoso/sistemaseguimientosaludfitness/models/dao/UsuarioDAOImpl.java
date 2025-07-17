/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Manuel Jesús Donoso Pérez
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection conn;

    public UsuarioDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Usuario u) {
        String sql = "INSERT INTO usuarios (dni, nombre, apellido1, apellido2, sexo,"
                + " fnacimiento, altura, peso, imc, dirFoto, direccion, poblacion, cp, tel, grasac,"
                + " proteina, metabolismoV, grasaVis, PesoIdeal, anotaciones,fAlta ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido1());
            ps.setString(4, u.getApellido2());
            ps.setString(5, u.getSexo());

            // Convertir LocalDate a java.sql.Date
            ps.setDate(6, java.sql.Date.valueOf(u.getFechaNacimiento()));

            ps.setString(7, u.getAltura());
            ps.setString(8, u.getPeso());
            ps.setString(9, u.getImc());
            ps.setString(10, u.getDirFoto());
            ps.setString(11, u.getDireccion());
            ps.setString(12, u.getPoblacion());
            ps.setString(13, u.getCp());
            ps.setString(14, u.getTelefono());
            ps.setString(15, u.getGrasac());
            ps.setString(16, u.getProteina());
            ps.setString(17, u.getMetabolismoV());
            ps.setString(18, u.getGrasaV());
            ps.setString(19, u.getPesoIdeal());
            ps.setString(20, u.getAnotaciones());

            // Convertir LocalDate a java.sql.Date para fechaAlta
            ps.setDate(21, java.sql.Date.valueOf(u.getFechaAlta()));

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al insertar usuario: " + ex.getMessage());
            // Puedes lanzar una excepción personalizada aquí si lo prefieres
        }
    }

    @Override
    public Usuario obtenerPorDni(String dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
