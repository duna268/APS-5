/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;  
import java.util.Properties;
/**
 *
 * @author Daniel
 */
public class dbConnections {
    
    private static Connection conn = null;
    
    //Abrir a Conexão com o banco
    public static Connection getConnection(){
        if(conn == null){
            try{
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url,props);
            }
            catch(SQLException e){
                throw new DbExeception(e.getMessage());
            }
        }   
        return conn;
    }
    
    //Fechar a Conexão com o banco
    public static void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException e){
                throw new DbExeception(e.getMessage());
            }
        }           
    }
    
    //Pegar as propriedades do db.properties
    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch(IOException e){
            throw new DbExeception(e.getMessage());
        }
    }
    
    //Fechar o Statement
    public static void closeStatement(Statement st){
        if(st != null){
            try {
                st.close();
            }
            catch (SQLException e) {
                throw new DbExeception(e.getMessage());
            }
        }   
    }
    
    //Fechar o ResultSet
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            }
            catch (SQLException e) {
                throw new DbExeception(e.getMessage());
            }
        }   
    }
}
