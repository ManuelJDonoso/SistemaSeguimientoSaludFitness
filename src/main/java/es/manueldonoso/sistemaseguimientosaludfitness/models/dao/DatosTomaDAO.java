/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;
import java.util.List;

/**
 *
 * @author donpe
 */
public interface DatosTomaDAO {
    void insertarDatosToma(DatosToma datosToma);
    int modificarDatosToma(DatosToma old, DatosToma nuevo);
    void EliminarDatosToma(DatosToma datosToma);
    void EliminarTodosDatosTomas();
    DatosToma buscarUsuarioFechaToma(String dni,String fecha);
    DatosToma buscarUsuarioProximaCita(String dni,String fecha);
    int EliminarProximaCita(DatosToma datoToma);
    List<DatosToma>ListarTodosDatos();
    List<DatosToma>ListarDatosTomaCliente(String dni);
    List<DatosToma>ListarDatosFecha(String FechaToma);
    
}
