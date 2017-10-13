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
public class ConnectDB {
    Connection connect = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    public Connection dbConnect(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/qlhs?useUnicode=true&characterEncoding=UTF-8";
            connect = DriverManager.getConnection(url, "root", "root");
            // = DriverManager.getConnection("jdbc:mysql://195.195.200.186/qlhs?user=ttdk3&password=trungtam3");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
    
    
    public Connection connectVTDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/vanthudb?useUnicode=true&characterEncoding=UTF-8";
            connect = DriverManager.getConnection(url, "root", "root");
            // = DriverManager.getConnection("jdbc:mysql://195.195.200.186/qlhs?user=ttdk3&password=trungtam3");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
    
    
    public void closeAll(Connection connect,PreparedStatement pstm, ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pstm != null){
            pstm.close();
        }
        if(connect != null){
            connect.close();
        }
    }
    public void closeAll(Connection connect,PreparedStatement pstm) throws SQLException{
        if(pstm != null){
            pstm.close();
        }
        if(connect != null){
            connect.close();
        }
    }
    
    public String[] getMaonline(String madon,String datepicker){
        String[] maonline = {"","",""};
        String sql  = "select post_id,loaihinhgoi,loaidon from posts where ma_loaihinhnhan = ? and datepicker like ?;";
        connect = dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setString(1, madon);
            pstm.setString(2, datepicker+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                maonline[0] = rs.getString(1);
                maonline[1] = rs.getString(2);
                maonline[2] = rs.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return maonline;
    }
    
    // Lấy toàn bộ phụ lục csgt
    public void getAllCSGT(){
        String sql = "";
        connect = connectVTDB();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Lưu mã cv và địa chỉ csgt
    public void updateDonCSgt(int dccsgt,String macv){
        String sql = "";
        connect = connectVTDB();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setInt(1, dccsgt);
            pstm.setString(2, macv);
            rs = pstm.executeQuery();
            while(rs.next()){
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
