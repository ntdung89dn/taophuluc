/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qlhs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntdung
 */
public class ConnectVTDB {
    Connection connect = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    public Connection ConnectVTDB(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/qlhs?useUnicode=true&characterEncoding=UTF-8";
            connect = DriverManager.getConnection(url, "root", "root");
            // = DriverManager.getConnection("jdbc:mysql://195.195.200.186/qlhs?user=ttdk3&password=trungtam3");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectVTDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectVTDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConnectVTDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnectVTDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
}
