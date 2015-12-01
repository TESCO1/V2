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
 * @author DAYAN
 */
public class GestionCriterios {

    Connection cn2;
    private Statement st;
    private PreparedStatement pS1;
    private String respuestaStg ;
    private String[] respuestaArre;
    
    public GestionCriterios(){
        
        Conexion.singlenton();
        conectar conec = new conectar();
        cn2 = conec.conexion();
        
    }
    
    
    public String agregarCriterio(String[] datos)
    {
        PreparedStatement ps2;
      
        try {
            
            ps2 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `criterios` \n" +
                                        "WHERE  `ClaveCriterio` =  '"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery();
            
            
            if(!rs2.next()){
               
                    pS1 = cn2.prepareStatement("INSERT INTO  `sear`.`criterios` (\n" +
                                                "`ClaveCriterio` ,\n" +
                                                "`Nombre`\n" +
                                                ")\n" +
                                                "VALUES (\n" +
                                                "'"+datos[0]+"',  '"+datos[1]+"'\n" +
                                                ");");  

                    int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Criterio registrado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al registrar el criterio"; 
                }
            }
            else
            {
                respuestaStg = "Criterio ya existente";
                return respuestaStg;   
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
            respuestaStg = "Error en el sistema";
            return respuestaStg;
        }
    }
    
    public String[] consultarCriterios (String clave){
         
         
         try {
             
             pS1 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `criterios` \n" +
                                        "WHERE  `ClaveCriterio` =  '"+clave+"'");
             
             ResultSet rs2 = pS1.executeQuery();
             
             if(rs2.next()){
                 
                 respuestaArre = new String[2];
                 respuestaArre[0]=rs2.getString("ClaveCriterio").toString();
                 respuestaArre[1]=rs2.getString("Nombre").toString();
                 
                 return respuestaArre;
                 
             }
             else
             {
                 respuestaArre = new String[1];
                 respuestaArre [0] = "No se encontraron coincidencias con la clave";
                 return respuestaArre;
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaArre = new String[1];
             respuestaArre [0] = "Error del sistema";
             return respuestaArre;
         }
    }
    
    public String modificarCriterio(String datos[]){
        PreparedStatement ps2;
         try {
            
            ps2 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `criterios` \n" +
                                        "WHERE  `ClaveCriterio` =  '"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("UPDATE  `sear`.`criterios` SET  `ClaveCriterio` =  '"+datos[1]+"',\n" +
                                            "`Nombre` =  '"+datos[2]+"' WHERE `criterios`.`ClaveCriterio`  =  '"+datos[0]+"'");
                
             
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Criterio modificado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al modificar el criterio"; 
                }
                
            }
            else
            {
                respuestaStg = "Criterio no existente";
                return respuestaStg;
            }
             
            
            
            
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
        
    }
    
    
    public String eliminarCriterio (String clave)
    {
        PreparedStatement ps2;
         try {
            
            ps2 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `criterios` \n" +
                                        "WHERE  `ClaveCriterio` =  '"+clave+"'");
            
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("DELETE FROM  `criterios` WHERE  `ClaveCriterio` LIKE  '"+clave+"'");
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Criterio eliminado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al eliminar el criterio"; 
                }
                
            }
            else
            {
                respuestaStg = "Criterio no existente";
                return respuestaStg;
            }
            
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
    }

}
