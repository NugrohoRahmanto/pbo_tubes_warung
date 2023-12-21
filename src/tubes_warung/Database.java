/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author anand
 */
public class Database {
    static final String DB_URL = "jdbc:mysql://localhost/pbo_tubes_warung";
    static final String DB_USER = "root";
    static final String DB_PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    
    public Database() throws SQLException, ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            stmt = conn.createStatement();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,""+e.getMessage(),"Connection Error",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public ResultSet getData(String SQLString){
        try{
            rs = stmt.executeQuery(SQLString);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error :"+e.getMessage(),"Communication Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return rs; 
    }
    
    public void query (String SQLString){
        try{
            stmt.executeUpdate(SQLString);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error :"+e.getMessage(),"Communication Error",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void close(){
        try {
            if (conn != null){
                conn.close();
            }
        } catch (SQLException err){
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, err);
        }
    }
    
}
