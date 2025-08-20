/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.DatosToma;

/**
 *
 * @author donpe
 */
public interface DatosTomaDAO {
    void insertarDatosToma(DatosToma datosToma);
    void modificarDatosToma(DatosToma datosToma);
    void EliminarDatosToma(DatosToma datosToma);
    void EliminarTodosDatosTomas();
    DatosToma buscarUsuarioFechaToma(String dni,String fecha);
    
    
}
