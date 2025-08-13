/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import base.BaseTest;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Login;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.LoginDAOimpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Manuel Jesús Donoso Pérez <dev@manueldonoso.es>
 */
public class LoginTest extends BaseTest {

    private String usuario = "UserTest";
    private String pass = "PassTest";
    LoginDAOimpl DAO;
    Login login;

    public LoginTest() {
    }

    @BeforeEach
    public void setUp() {
        try {
            DatabaseHelper.crearTablasdefault(conn);
            login = new Login(usuario, pass);
            conn = DatabaseHelper.getConnection();
            DAO = new LoginDAOimpl(conn);
        } catch (SQLException ex) {
            System.getLogger(LoginTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Limpiando Datos");
        DAO.usuarioExiste(usuario);
    }

    @Test
    public void insertarTest() {
        System.out.println("Insertando Usuario");
        DAO.insertar(login);
        System.out.println("Comprobando que el usuario ha sido insertado");
        assertTrue(DAO.usuarioExiste(usuario));
    }

    @Test
    public void EliminarTest() {
        System.out.println("Insertando Usuario");
        DAO.insertar(login);
        System.out.println("Comprobando que el usuario ha sido insertado");
        assertTrue(DAO.usuarioExiste(usuario));
        System.out.println("Eliminando Usuario");
        DAO.eleminar(login);
        System.out.println("Comprobando que el usuario ha sido Eliminado");
        assertFalse(DAO.usuarioExiste(usuario));

    }
    
    @Test
    public void ComprobarLogin(){
    System.out.println("Insertando Usuario");
        DAO.insertar(login);
        System.out.println("Comprobando que Login es:" +usuario+" y contraseña: "+ pass );
        assertTrue(DAO.verificarLogin(login));
    }
    
        @Test
    public void CambiarLogin(){
        System.out.println("Insertando Usuario");
        DAO.insertar(login);
        System.out.println("Comprobando que Login es:" +usuario+" y contraseña: "+ pass );
        assertTrue(DAO.verificarLogin(login));
            System.out.println("Cambiando Contraseña a 1234");
            DAO.cambiarPass(login, "1234");
             System.out.println("Comprobando que Login no es el antiguo :" +usuario+" y contraseña: "+ pass );
        assertFalse(DAO.verificarLogin(login));
        login.setPass("1234");
        System.out.println("Comprobando que Login es:" +usuario+" y contraseña: 1234 " );
        assertTrue(DAO.verificarLogin(login));
        
    }
}
