/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Cliente;
import java.util.List;

/**
 *
 * @author Manuel Jesús Donoso Pérez
 */
public interface ClienteDAO {
    void insertar(Cliente u);
    Cliente obtenerPorDni(String dni);
    List<Cliente> listarTodos();
    void actualizar(Cliente u);
    void eliminar(String dni);
}
