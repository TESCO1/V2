/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alexX
 */
public class conectar {
     Connection conect = null;
    String base = "sear";
    String user = "root";
    String pass = "mysql";
    public boolean error;
    
   
    
    public Connection conexion()
    {
        try
        {
            conect = DriverManager.getConnection("jdbc:mysql://localhost/" + base , user , pass );
            System.out.println("Mensaje: conectado correctamente");
            this.error = false;
        } 
      
        catch ( SQLException e )
        {
            this.error = true;
            System.out.println("SEAR:Conectar - Catch: \nBase: " + base
                + "\nUsuario: " + user + "\nContraseña: " +pass  );
            System.out.println("Exception: " + e.getMessage() + "\n");
            
        }
        return conect;
    }
    
    
}
