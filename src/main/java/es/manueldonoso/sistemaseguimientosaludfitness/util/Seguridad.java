/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

import java.security.MessageDigest;

/**
 *
 * @author donpe
 */
public class Seguridad {
    public static String hashSHA256(String texto){
    
        try {
            MessageDigest digest= MessageDigest.getInstance("SHA-256");
            byte[]hash=digest.digest(texto.getBytes("UTF-8"));
            
            StringBuilder hexString =new StringBuilder();
            for(byte b:hash){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length()==1)hexString.append('0');
            hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
        
        throw new RuntimeException("Error al hashear ",e);
        }
    }

   

}
