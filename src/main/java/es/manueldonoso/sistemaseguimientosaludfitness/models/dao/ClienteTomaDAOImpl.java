/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.ClienteTomaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class ClienteTomaDAOImpl implements ClienteTomaDAO {

    private Connection conn;

    public ClienteTomaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public ObservableList<ClienteTomaDTO> findAll() {

        ObservableList<ClienteTomaDTO> lista = FXCollections.observableArrayList();
        String sql = "SELECT c.dni, c.nombre, c.apellidos, c.poblacion, d.proxima_cita "
                + "FROM clientes c "
                + "JOIN datostoma d ON c.dni = d.dni";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClienteTomaDTO dto = new ClienteTomaDTO(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("Poblacion"),
                        LocalDateTime.parse(rs.getString("fechaProximaCita"))
                );


                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public ClienteTomaDTO findByDni(String dni) {
        String sql = "SELECT c.dni, CONCAT(c.nombre, ' ', c.apellido) AS nombreCompleto, "
                + "c.poblacion, d.proxima_cita "
                + "FROM clientes c "
                + "JOIN datostoma d ON c.dni = d.dni "
                + "WHERE c.dni = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClienteTomaDTO(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("Poblacion"),
                            LocalDateTime.parse(rs.getString("fechaProximaCita"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
