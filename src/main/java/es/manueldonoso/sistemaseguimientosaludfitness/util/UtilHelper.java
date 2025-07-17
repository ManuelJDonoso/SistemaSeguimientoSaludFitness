/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.util;

/**
 *
 * @author Manuel Jesus Donoso Pérez 
 */
public class UtilHelper {
    
    public static double calcularIMC(double Peso, int altura){
    double imc;
      if(altura>0 && Peso>0){
          Double alt=(double)altura;
          Double alt2=(alt/100)*(alt/100);
          
 
          
         imc= Peso/alt2;   
       
      }else{
          imc=0;
          System.err.println("El valor no de la altura no puede ser <=0 ");
      }
    
    return imc;
    }
    
    
    public static double calcularPesoIdeal( int altura){
     double pesoIdeal;
     if(altura>0 ){
          Double alt=(double)altura;
          Double alt2=(alt/100)*(alt/100);
          
 
          
         pesoIdeal= 22.00*alt2;   
       
      }else{
          pesoIdeal=0;
          System.err.println("El valor no de la altura no puede ser <=0 ");
      }
     
     return pesoIdeal;
    }
}
