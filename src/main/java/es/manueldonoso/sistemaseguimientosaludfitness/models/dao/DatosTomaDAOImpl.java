/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author donpe
 */
public class DatosTomaDAOImpl implements DatosTomaDAO{

    private Connection conn;

    public DatosTomaDAOImpl(Connection conn) {
        this.conn = conn;
    }


    
    
    @Override
    public void insertarDatosToma(DatosToma datosToma) {
        
        String proximaCitaStr = (datosToma.getProximaCita() != null) 
    ? datosToma.getProximaCita().toString() 
    : null;
        
        
        String sql="INSERT INTO datosToma ("
                + "fkCliente, fechaToma,peso,imc,dirFoto, grasac, "
                + "proteina, metabolismoV, grasaV,fechaProximaCita) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        
         //dni, peso, imc, dirFoto, grasac, proteina, metabolismoV, grasaV, PesoIdeal;
         try (PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setString(1, datosToma.getDni());
             ps.setString(2, datosToma.getFechaToma().toString());
             ps.setString(3, datosToma.getPeso());
             ps.setString(4, datosToma.getImc());
             ps.setString(5, datosToma.getDirFoto());
             ps.setString(6, datosToma.getGrasac());
             ps.setString(7, datosToma.getProteina());
             ps.setString(8, datosToma.getMetabolismoV());
             ps.setString(9, datosToma.getGrasaV());
             ps.setString(10, proximaCitaStr);
             
             
                 ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al insertar usuario: " + ex.getMessage());
            // Puedes lanzar una excepción personalizada aquí si lo prefieres
        }
    
    }

    @Override
    public void modificarDatosToma(DatosToma DatosToma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void EliminarDatosToma(DatosToma DatosToma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
