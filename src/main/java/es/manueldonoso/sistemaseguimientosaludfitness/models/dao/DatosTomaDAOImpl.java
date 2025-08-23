package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link DatosTomaDAO} que gestiona las
 * operaciones CRUD sobre la tabla <code>datosToma</code> en la base de datos.
 * <p>
 * Esta clase utiliza una conexión JDBC para realizar inserciones,
 * actualizaciones, eliminaciones y consultas de registros relacionados con la
 * entidad {@link DatosToma}.
 * </p>
 *
 * Ejemplos de funcionalidades soportadas:
 * <ul>
 * <li>Insertar un nuevo registro de datos de toma.</li>
 * <li>Modificar registros existentes de datos de toma.</li>
 * <li>Eliminar registros individuales o todos los registros.</li>
 * <li>Buscar registros por fecha de toma, próxima cita, usuario o listar
 * todos.</li>
 * </ul>
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class DatosTomaDAOImpl implements DatosTomaDAO {

    /**
     * Conexión activa con la base de datos.
     */
    private Connection conn;

    /**
     * Crea una nueva instancia del DAO con la conexión especificada.
     *
     * @param conn conexión activa a la base de datos
     */
    public DatosTomaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserta un nuevo registro de {@link DatosToma} en la base de datos.
     *
     * @param datosToma objeto con los datos de la toma a insertar
     */
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

    /**
     * Modifica un registro existente de datos de toma.
     *
     * @param old objeto con los datos antiguos que identifican el registro a
     * modificar
     * @param nuevo objeto con los nuevos valores para actualizar
     * @return número de filas actualizadas
     */
    @Override
    public int modificarDatosToma(DatosToma old, DatosToma nuevo) {
        int filasActualizadas = 0;
        String sql = "UPDATE datosToma SET "
                + " fkCliente = ?, "
                + " fechaToma = ?, "
                + " fechaProximaCita = ?, "
                + " peso = ?, "
                + " imc  = ?, "
                + " dirFoto  = ?, "
                + " grasac = ?, "
                + " proteina  = ?,  "
                + " metabolismoV = ?, "
                + " grasaV "
                + " WHERE fechaToma = ? AND fkCliente = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevo.getDni());
            ps.setString(2, nuevo.getFechaToma().toString());
            ps.setString(3, nuevo.getProximaCita().toString());
            ps.setString(4, nuevo.getPeso());
            ps.setString(5, nuevo.getImc());
            ps.setString(6, nuevo.getDirFoto());
            ps.setString(7, nuevo.getGrasac());
            ps.setString(8, nuevo.getProteina());
            ps.setString(9, nuevo.getMetabolismoV());
            ps.setString(10, nuevo.getGrasaV());
            ps.setString(11, old.getFechaToma().toString());
            ps.setString(12, old.getDni());

            filasActualizadas = ps.executeUpdate();
        } catch (SQLException e) {
        }

        return filasActualizadas;
    }

    /**
     * Elimina un registro de datos de toma de un cliente en una fecha
     * específica.
     *
     * @param datosToma objeto con el cliente y la fecha de toma a eliminar
     */
    @Override
    public void EliminarDatosToma(DatosToma datosToma) {
        String sql = "DELETE FROM datosToma WHERE fechaToma = ? AND fkCliente = ?";
        String dni = datosToma.getDni();
        String fecha = datosToma.getFechaToma().toString();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            pstmt.setString(2, dni);
            int FilasBorradas = pstmt.executeUpdate();
            String mensaje = (FilasBorradas == 1) ? "Se Han borrado " + FilasBorradas + " Dato" : "Se Han borrado " + FilasBorradas + " Datos tomas";
            System.out.println(mensaje);

        } catch (Exception e) {
            e.getCause();
        }

    }

    /**
     * Elimina todos los registros de la tabla <code>datosToma</code>.
     */
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

    /**
     * Busca un registro de datos de toma para un cliente en una fecha
     * específica.
     *
     * @param dni DNI del cliente
     * @param fecha fecha de toma (en formato ISO-8601)
     * @return objeto {@link DatosToma} encontrado o {@code null} si no existe
     */
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
                            rs.getString("fkCliente"), // dni
                            rs.getString("peso"), // peso
                            rs.getString("imc"), // imc
                            rs.getString("dirFoto"), // dirfoto
                            rs.getString("grasac"), // grasac
                            rs.getString("proteina"), // proteina
                            rs.getString("metabolismoV"), // metabolismo v
                            rs.getString("grasaV"), // grasav
                            fechaToma, // fecha toma
                            fechaProxima // fecha proxima
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar datos de toma: " + e.getMessage());
            e.printStackTrace();
        }

        return datosToma;
    }

    /**
     * Busca un registro de datos de toma por cliente y fecha de próxima cita.
     *
     * @param dni DNI del cliente
     * @param fecha fecha de próxima cita (en formato ISO-8601)
     * @return objeto {@link DatosToma} encontrado o {@code null} si no existe
     */
    @Override
    public DatosToma buscarUsuarioProximaCita(String dni, String fecha) {
        DatosToma datosToma = null;

        // Corregido: espacio después de grasav
        String sql = "SELECT fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina, "
                + "metabolismoV, grasaV "
                + "FROM datosToma "
                + "WHERE fechaProximaCita = ? AND fkCliente = ?";

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
                            rs.getString("fkCliente"), // dni
                            rs.getString("peso"), // peso
                            rs.getString("imc"), // imc
                            rs.getString("dirFoto"), // dirfoto
                            rs.getString("grasac"), // grasac
                            rs.getString("proteina"), // proteina
                            rs.getString("metabolismoV"), // metabolismo v
                            rs.getString("grasaV"), // grasav
                            fechaToma, // fecha toma
                            fechaProxima // fecha proxima
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar datos de toma: " + e.getMessage());
            e.printStackTrace();
        }

        return datosToma;
    }

    /**
     * Elimina la fecha de próxima cita de un registro de datos de toma.
     *
     * @param datoToma objeto con los datos de toma a actualizar
     * @return número de filas actualizadas
     */
    @Override
    public int EliminarProximaCita(DatosToma datoToma) {

        String sql = "UPDATE datosToma SET fechaProximaCita = NULL ";
        int filasActualizadas = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            filasActualizadas = ps.executeUpdate();
        } catch (SQLException e) {
            e.getCause();
        }

        return filasActualizadas;
    }

    /**
     * Lista todos los registros de datos de toma existentes en la base de
     * datos.
     *
     * @return lista con todos los objetos {@link DatosToma}
     */
    @Override
    public List<DatosToma> ListarTodosDatos() {
        List<DatosToma> lista = new ArrayList<>();
        String sql = "SELECT fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina, "
                + "metabolismoV, grasaV "
                + "FROM datosToma ";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { // <-- CAMBIO AQUÍ
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

                lista.add(
                        new DatosToma(
                                rs.getString("fkCliente"), // dni
                                rs.getString("peso"), // peso
                                rs.getString("imc"), // imc
                                rs.getString("dirFoto"), // dirfoto
                                rs.getString("grasac"), // grasac
                                rs.getString("proteina"), // proteina
                                rs.getString("metabolismoV"), // metabolismo v
                                rs.getString("grasaV"), // grasav
                                fechaToma, // fecha toma
                                fechaProxima // fecha proxima
                        ));
            }

        } catch (Exception e) {
            System.err.println("Error al buscar datos de toma: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Lista todos los registros de datos de toma de un cliente específico.
     *
     * @param dni DNI del cliente
     * @return lista de objetos {@link DatosToma} asociados al cliente
     */
    @Override
    public List<DatosToma> ListarDatosTomaCliente(String dni
    ) {
        String sql = "SELECT fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina, "
                + "metabolismoV, grasaV "
                + "FROM datosToma "
                + "WHERE  fkCliente = ?";

        List<DatosToma> lista = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
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

                    lista.add(
                            new DatosToma(
                                    rs.getString("fkCliente"), // dni
                                    rs.getString("peso"), // peso
                                    rs.getString("imc"), // imc
                                    rs.getString("dirFoto"), // dirfoto
                                    rs.getString("grasac"), // grasac
                                    rs.getString("proteina"), // proteina
                                    rs.getString("metabolismoV"), // metabolismo v
                                    rs.getString("grasaV"), // grasav
                                    fechaToma, // fecha toma
                                    fechaProxima // fecha proxima
                            ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar datos de toma: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Lista todos los registros de datos de toma en una fecha concreta.
     *
     * @param FechaToma fecha de toma en formato ISO-8601 (yyyy-MM-dd)
     * @return lista de objetos {@link DatosToma} correspondientes a la fecha
     * indicada
     */
    @Override
    public List<DatosToma> ListarDatosFecha(String FechaToma) {
        List<DatosToma> lista = new ArrayList<>();
        String sql = "SELECT fkCliente, fechaToma, fechaProximaCita, peso, imc, dirFoto, grasac, proteina, "
                + "metabolismoV, grasaV "
                + "FROM datosToma "
                + "WHERE fechaToma LIKE ?";

        System.out.println("Se ha introducido la fecha " + FechaToma);
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, FechaToma + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
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

                    lista.add(
                            new DatosToma(
                                    rs.getString("fkCliente"), // dni
                                    rs.getString("peso"), // peso
                                    rs.getString("imc"), // imc
                                    rs.getString("dirFoto"), // dirfoto
                                    rs.getString("grasac"), // grasac
                                    rs.getString("proteina"), // proteina
                                    rs.getString("metabolismoV"), // metabolismo v
                                    rs.getString("grasaV"), // grasav
                                    fechaToma, // fecha toma
                                    fechaProxima // fecha proxima
                            ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar datos de toma: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

}
