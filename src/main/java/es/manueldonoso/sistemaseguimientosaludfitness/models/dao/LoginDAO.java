/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public interface LoginDAO {

    void insertar(Login login);

    void insertar(Login login, String url);

    Login obtenerPorUsuario(String Usuario);

    Login obtenerPorUsuario(String Usuario, String url);

    void eleminar(Login login);

    void eleminar(Login login, String url);

    void cambiarPass(Login login, String Pass);

    void cambiarPass(Login login, String Pass, String url);

    boolean verificarLogin(String Usuario, String pass);

    boolean verificarLogin(String Usuario, String pass, String url);

    boolean usuarioExiste(String Usuario);

    boolean usuarioExiste(String Usuario, String url);

}
