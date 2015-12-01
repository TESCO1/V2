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
public class AdministracionGrupos {
    
    
     Connection cn2;
     private Statement st;
     private PreparedStatement pS1;
     private String respuestaStg ;
     private String[] respuestaArre;
     
     public AdministracionGrupos(){
    
        Conexion.singlenton();
        conectar conec = new conectar();
        cn2 = conec.conexion();
    }
     
     public String registroGrupo (String[] datosUsu){
         
        PreparedStatement ps2;
      
        try {
            
            ps2 = cn2.prepareStatement("SELECT * FROM `grupos` WHERE `ClaveGrupo`='"+datosUsu[0]+"'");
            ResultSet rs2 = ps2.executeQuery();
            
            
            if(!rs2.next()){
               
                    pS1 = cn2.prepareStatement("INSERT INTO  `sear`.`grupos` (\n" +
                        "`ClaveGrupo` ,\n" +
                        "`Periodo` ,\n" +
                        "`Grado`\n" +
                        ")\n" +
                        "VALUES (\n" +
                        "'"+datosUsu[0]+"',  '"+datosUsu[1]+"',  '"+datosUsu[2]+"'\n" +
                        ");");  
            
                    int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Grupo registrado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al registrar al grupo"; 
                }
            }
            else
            {
                respuestaStg = "Grupo ya existente";
                return respuestaStg;   
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
            respuestaStg = "Error en el sistema";
            return respuestaStg;
        }
         
     }
       
    
     public String[] consultarGrupos (String clave){
         
         
         try {
             
             pS1 = cn2.prepareStatement("SELECT  `ClaveGrupo` , `Periodo` ,  `Grado` \n" +
                     "FROM  `grupos` \n" +
                     "WHERE  `ClaveGrupo` LIKE  '"+clave+"'");
             
             ResultSet rs2 = pS1.executeQuery();
             
             if(rs2.next()){
                 
                 respuestaArre = new String[3];
                 respuestaArre[0]=rs2.getString("ClaveGrupo").toString();
                 respuestaArre[1]=rs2.getString("Periodo").toString();
                 respuestaArre[2]=rs2.getString("Grado").toString();
                 
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

    public String modificarGrupo(String datos[]){
        PreparedStatement ps2;
         try {
            
            ps2 = cn2.prepareStatement("SELECT * FROM `grupos` WHERE `ClaveGrupo`='"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("UPDATE  `sear`.`grupos` SET  `ClaveGrupo` =  '"+datos[0]+"',\n" +
                                        " `Periodo` =  '"+datos[1]+"',\n" +
                                        " `Grado` =  '"+datos[2]+"' WHERE  `grupos`.`ClaveGrupo` =  '"+datos[0]+"'");
                
             
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Grupo modificado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al modificar al grupo"; 
                }
                
            }
            else
            {
                respuestaStg = "Grupo no existente";
                return respuestaStg;
            }
             
            
            
            
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
        
    }
    
    public String eliminarGrupos (String clave){
        
        PreparedStatement ps2;
         try {
            
            ps2 = cn2.prepareStatement("SELECT * FROM `grupos` WHERE `ClaveGrupo`='"+clave+"'");
            
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("DELETE FROM `grupos` WHERE `ClaveGrupo` LIKE '"+clave+"'");
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Grupo eliminado correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al eliminar al grupo"; 
                }
                
            }
            else
            {
                respuestaStg = "Grupo no existente";
                return respuestaStg;
            }
             
            
            
            
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
    }
    
    
    public String agregarAlumGru (String datos[]){

        PreparedStatement ps2,ps3;
         try {
            
            ps2 = cn2.prepareStatement("SELECT * FROM `grupos` WHERE `ClaveGrupo`='"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                ps3 = cn2.prepareStatement("SELECT * \n" +
                                            "FROM  `controlgrupos` \n" +
                                            "WHERE  `Matricula` LIKE  '"+datos[1]+"'");
                ResultSet rs3 = ps3.executeQuery();
                
                if(!rs3.next())
                {
                    pS1 = cn2.prepareStatement("INSERT INTO  `sear`.`controlGrupos` (\n" +
                                            "`clavecg` ,\n" +
                                            "`Matricula` ,\n" +
                                            "`ClaveGrupo`\n" +
                                            ")\n" +
                                            "VALUES (\n" +
                                            "NULL ,  '"+datos[1]+"',  '"+datos[0]+"'\n" +
                                            ");");


                    int n = pS1.executeUpdate();

                    if(n>0){

                        respuestaStg = "Alumno agregado al grupo exitosamente";
                        return respuestaStg;
                    }
                    else
                    {
                        return respuestaStg = "Error al agregar el alumno al grupo"; 
                    }
                }
                else
                {
                    
                    respuestaStg = "Alumno ya agregado a un grupo";
                    return respuestaStg;
                }
                
            }
            else
            {
                respuestaStg = "No se puede agregar el alumno ya que el grupo no existe o no se a ingresado la clave";
                return respuestaStg;
            }
             
            
            
            
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
        
    }
    
    
    public String[] consultarAlumnos (){
        
        String[] datos;
        int contador= 0;
        int i;
        String tipoUsu;
        
        try {
             
             pS1 = cn2.prepareStatement("SELECT  `alumno`.`Matricula` ,  `alumno`.`Nombre` ,  `alumno`.`Ap_pat` ,  `alumno`.`Ap_mat` ,  `alumno`.`Periodo` \n" +
                                        "FROM  `alumno` ");
             
             ResultSet rs2 = pS1.executeQuery();
           
            while(rs2.next())
             {
                 contador += 1;
                 System.out.println("no."+ contador);
             }
             
             rs2.first();
             datos = new String [contador];
             
            for(i=0;i<contador;i++)
             {       
                 datos[i] = rs2.getString("Matricula").concat("-").concat(rs2.getString("Nombre")).concat("-").concat(rs2.getString("Ap_pat")).concat("-").concat(rs2.getString("Ap_mat")).concat("-").concat(rs2.getString("Periodo")).concat("-");
                 rs2.next();
                 System.out.println("datos: "+datos[i]);
             }
             
             return datos;
     
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaArre = new String[1];
             respuestaArre [0] = "Error del sistema";
             return respuestaArre;
         }
      
    }
           
     
}
