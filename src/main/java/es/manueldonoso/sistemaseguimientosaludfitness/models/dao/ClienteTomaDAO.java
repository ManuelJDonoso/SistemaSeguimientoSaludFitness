/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.ClienteTomaDTO;
import javafx.collections.ObservableList;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public interface ClienteTomaDAO {

   

        ObservableList<ClienteTomaDTO> findAll();

        ClienteTomaDTO findByDni(String dni);
    
}
