/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.models.dao;

import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import java.sql.Connection;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public interface LoginDAO {

    void insertar(Login login);

 

    void eleminar(Login login);

    void cambiarPass(Login login, String Pass);

    boolean verificarLogin(Login login);

    boolean usuarioExiste(String Usuario);

    Connection getConn();
    void setConn(Connection conn);
}
