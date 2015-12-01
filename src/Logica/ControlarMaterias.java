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
public class ControlarMaterias {
    
    Connection cn2;
    private Statement st;
    private PreparedStatement pS1;
    private String respuestaStg ;
    private String[] respuestaArre;
    
    public ControlarMaterias(){
        Conexion.singlenton();
        conectar conec = new conectar();
        cn2 = conec.conexion();
    }
    
    public String agregarMaterias (String[] datos)
    {
        PreparedStatement ps2;
      
        try {
            
            ps2 = cn2.prepareStatement("SELECT `ClaveMateria`,`Nombre`,`Descripcion` FROM `materias` WHERE `ClaveMateria` LIKE '"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery();
            
            
            if(!rs2.next()){
               
                    pS1 = cn2.prepareStatement("INSERT INTO  `sear`.`materias` (\n" +
                                                "`ClaveMateria` ,\n" +
                                                "`Nombre` ,\n" +
                                                "`Descripcion`\n" +
                                                ")\n" +
                                                "VALUES (\n" +
                                                "'"+datos[0]+"',  '"+datos[1]+"',  '"+datos[2]+"'\n" +
                                                ");");  
            
                    int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Materia registrada correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al registrar la materia"; 
                }
            }
            else
            {
                respuestaStg = "Materia ya existente";
                return respuestaStg;   
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
            respuestaStg = "Error en el sistema";
            return respuestaStg;
        }
    }
    
    
    public String agregarControlCriterios (String datos[])
    {
        PreparedStatement ps2;
      
        try {
            
            ps2 = cn2.prepareStatement("SELECT `ClaveCriterio`,`ClaveMateria`FROM `controlcriterios` WHERE `ClaveMateria` LIKE '"+datos[0]+"' AND  `ClaveCriterio` LIKE '"+datos[1]+"' ");
            ResultSet rs2 = ps2.executeQuery();

            
            if(!rs2.next()){

                            pS1 = cn2.prepareStatement("INSERT INTO  `sear`.`controlcriterios` (\n"+
                                                        "`ClaveCriterio` ,\n" +
                                                        "`ClaveMateria`\n" +
                                                        ")\n" +
                                                        "VALUES (\n" +
                                                        "'"+datos[1]+"',  '"+datos[0]+"'\n" +
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
                respuestaStg = "Criterio ya registrado para la materia";
                return respuestaStg;   
            }
    
        } catch (SQLException ex) {
            Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
            respuestaStg = "Error en el sistema";
            return respuestaStg;
        }
    }
    
    
      public String[] consultarMaterias (String clave){
         
         
         try {
             
             pS1 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `materias` \n" +
                                        "WHERE  `materias`.`ClaveMateria` LIKE  '"+clave+"'");
             
             ResultSet rs2 = pS1.executeQuery();
             
             if(rs2.next()){
                 
                 respuestaArre = new String[3];
                 respuestaArre[0]=rs2.getString("ClaveMateria").toString();
                 respuestaArre[1]=rs2.getString("Nombre").toString();
                 respuestaArre[2]=rs2.getString("Descripcion").toString();
                 
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
      
    public String[] consultarMateriasCri (String clave){
              
        String[] datos;
        int contador= 0;
        int i;
        String tipoUsu; 
        
         try {
             
             pS1 = cn2.prepareStatement("SELECT  `controlcriterios`.`ClaveCriterio` ,  `criterios`.`Nombre` \n" +
                                        "FROM  `materias` ,  `criterios` ,  `controlcriterios` \n" +
                                        "WHERE  `materias`.`ClaveMateria` LIKE  '"+clave+"'\n" +
                                        "AND  `materias`.`ClaveMateria` =  `controlcriterios`.`ClaveMateria` \n" +
                                        "AND  `controlcriterios`.`ClaveCriterio` =  `criterios`.`ClaveCriterio` ");
             
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
                 datos[i] = rs2.getString("ClaveCriterio").concat("-").concat(rs2.getString("Nombre")).concat("-");
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
      
      
        public String[] consultarTodosCriterios (){
         
        String[] datosr;
        int contador= 0;
        int i;
        String tipoUsu;  
        
         try {
             
             pS1 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `criterios` ");
             
             ResultSet rs2 = pS1.executeQuery();
             
                while(rs2.next())
                {
                    contador += 1;
                    System.out.println("no."+ contador);
                }

                rs2.first();
                datosr = new String [contador];

               for(i=0;i<contador;i++)
                {       
                    datosr[i] = rs2.getString("ClaveCriterio").concat("-").concat(rs2.getString("Nombre")).concat("-");
                    rs2.next();
                    System.out.println("datos: "+datosr[i]);
                }

                return datosr;
                
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             datosr = new String[1];
             datosr [0] = "Error del sistema";
             return datosr;
         }
 
     }    
        
        
    public String modificarMateria(String datos[]){
        PreparedStatement ps2;
         try {
            
            ps2 = cn2.prepareStatement("SELECT `ClaveMateria`,`Nombre`,`Descripcion` FROM `materias` WHERE `ClaveMateria` LIKE '"+datos[0]+"'");
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("UPDATE  `sear`.`materias` SET  `ClaveMateria` =  '"+datos[0]+"',\n" +
                                        "`Nombre` =  '"+datos[1]+"',\n" +
                                        "`Descripcion` =  '"+datos[2]+"' WHERE `materias`.`ClaveMateria` LIKE  '"+datos[0]+"';");
                
             
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Materia modificada correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al modificar la materia"; 
                }
                
            }
            else
            {
                respuestaStg = "Materia no existente";
                return respuestaStg;
            }
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
        
    }
    
    public String eliminarControlCriterios (String clave)
    { 
        PreparedStatement ps2;
         try {
            ps2 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `controlcriterios` \n" +
                                        "WHERE  `ClaveCriterio` LIKE  '"+clave+"'");
            
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("DELETE FROM `controlcriterios` WHERE `ClaveCriterio` LIKE '"+clave+"'");
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Criterio removido correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al remover el criterio"; 
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
    
    public String eliminarMaterias (String clave)
    { 
        PreparedStatement ps2;
         try {
            ps2 = cn2.prepareStatement("SELECT * \n" +
                                        "FROM  `materias` \n" +
                                        "WHERE  `ClaveMateria` =  '"+clave+"'");
            
            ResultSet rs2 = ps2.executeQuery(); 
            
            if(rs2.next())
            {
                pS1 = cn2.prepareStatement("DELETE FROM `materias` WHERE `ClaveMateria` LIKE '"+clave+"'");
                int n = pS1.executeUpdate();

                if(n>0){
                    
                    respuestaStg = "Materia eliminada correctamente";
                    return respuestaStg;
                }
                else
                {
                    return respuestaStg = "Error al eliminar la materia"; 
                }
            }
            else
            {
                respuestaStg = "Materia no existente";
                return respuestaStg;
            }
         } catch (SQLException ex) {
             Logger.getLogger(AdministracionGrupos.class.getName()).log(Level.SEVERE, null, ex);
             respuestaStg = "Error del sistema";
             return respuestaStg;
         }
    }
    
    
}
