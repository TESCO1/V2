/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author alexX
 */
public class Usuarios {
    
    Connection cn;
     private Statement st;
     private PreparedStatement pS;
     private String respuestaS ;
     private String[] respuestaAr;

    
    public Usuarios()
    {
        Conexion.singlenton();
        conectar conec = new conectar();
        cn = conec.conexion();
    }
    
    
           
    
}
