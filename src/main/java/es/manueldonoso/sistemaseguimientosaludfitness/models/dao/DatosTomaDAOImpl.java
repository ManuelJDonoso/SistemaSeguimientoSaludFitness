package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author donpe
 */
public class DatosTomaDAOImpl implements DatosTomaDAO {

    private Connection conn;

    public DatosTomaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertarDatosToma(DatosToma datosToma) {

        String proximaCitaStr = (datosToma.getProximaCita() != null)
                ? datosToma.getProximaCita().toString()
                : null;

        String sql = "INSERT INTO datosToma ("
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

    @Override
    public void EliminarTodosDatosTomas() {
        String sql = "DELETE FROM datosToma";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int FilasBorradas = pstmt.executeUpdate();
            String mensaje = (FilasBorradas == 1) ? "Se Han borrado " + FilasBorradas + " Dato" : "Se Han borrado " + FilasBorradas + " Datos tomas";
            System.out.println(mensaje);

        } catch (Exception e) {
            e.getCause();
        }

    }

    @Override
    public DatosToma buscarUsuarioFechaToma(String dni, String fecha) {
    DatosToma datosToma = null;

    // Corregido: espacio después de grasav
    String sql = "SELECT fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina, "
               + "metabolismoV, grasaV "
               + "FROM datosToma "
               + "WHERE fechaToma = ? AND fkCliente = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, fecha);
        stmt.setString(2, dni);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Verificar que las fechas no sean nulas antes de parsear
                String fechaTomaStr = rs.getString("fechaToma");
                String fechaProximaStr = rs.getString("fechaProximaCita");
                
                LocalDateTime fechaToma = null;
                LocalDateTime fechaProxima = null;
                
                if (fechaTomaStr != null) {
                    fechaToma = LocalDateTime.parse(fechaTomaStr);
                }
                
                if (fechaProximaStr != null) {
                    fechaProxima = LocalDateTime.parse(fechaProximaStr);
                }

                datosToma = new DatosToma(
                    rs.getString("fkCliente"),        // dni
                    rs.getString("peso"),             // peso
                    rs.getString("imc"),              // imc
                    rs.getString("dirFoto"),          // dirfoto
                    rs.getString("grasac"),           // grasac
                    rs.getString("proteina"),         // proteina
                    rs.getString("metabolismoV"),     // metabolismo v
                    rs.getString("grasaV"),           // grasav
                    fechaToma,                        // fecha toma
                    fechaProxima                      // fecha proxima
                );
            }
        }
    } catch (Exception e) {
        System.err.println("Error al buscar datos de toma: " + e.getMessage());
        e.printStackTrace();
    }

    return datosToma;
}
    
//    public DatosToma buscarUsuarioFechaToma(String dni, String fecha) {
//        DatosToma datosToma = null;
//
//        String sql = "SELECT  fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina,"
//                + " metabolismoV, grasaV"
//                + "FROM datosToma "
//                + "WHERE fechaToma = ? AND fkCliente = ? ";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, fecha);
//            stmt.setString(2, dni);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//
//                    datosToma = new DatosToma(
//                            rs.getString("fkCliente"), //dni
//                            rs.getString("peso"), //peso
//                            rs.getString("imc"), //imc
//                            rs.getString("dirFoto"), //dirfoto
//                            rs.getString("grasac"), //grasac
//                            rs.getString("proteina"), //proteina
//                            rs.getString("metabolismoV"), //metabolismo v
//                            rs.getString("grasaV"), //grasav
//
//                            LocalDateTime.parse(rs.getString("fechaToma")), //fecha toma - formato ISO
//                            LocalDateTime.parse(rs.getString("fechaProximaCita")) // fecha proxima - formato ISO
//
//                    );//fecha Toma
//
//                }
//
//            }
//        } catch (Exception e) {  // Loggear el error o relanzar una excepción personalizada
//            System.err.println("Error al crear el cliente: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return datosToma;
//    }

}
