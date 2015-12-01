/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexX
 */
public class VO {
    
    Connection cn;
    private Statement st;
    private PreparedStatement pS;
    
    public VO()
    {
        Conexion.singlenton();
        DAO dao = new DAO();
        cn = dao.DAOConection();
              
    }
    

}
