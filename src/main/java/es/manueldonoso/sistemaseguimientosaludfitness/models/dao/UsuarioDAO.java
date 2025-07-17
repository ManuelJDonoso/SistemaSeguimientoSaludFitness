/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Usuario;
import java.util.List;

/**
 *
 * @author Manuel Jesús Donoso Pérez
 */
public interface UsuarioDAO {
    void insertar(Usuario u);
    Usuario obtenerPorDni(String dni);
    List<Usuario> listarTodos();
    void actualizar(Usuario u);
    void eliminar(String dni);
}
