/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Akos
 */
public class DBModel implements IModel {
    
    private Connection conn;
    private PreparedStatement getAllKerdesPstmt;
    private PreparedStatement getAllNyeremenyPstmt;
    private PreparedStatement addKerdesPstmt;
    private PreparedStatement updateKerdesPstmt;
    private PreparedStatement removeKerdesPstmt;
    
    public DBModel(Connection conn) throws SQLException {
        
        this.conn = conn;
        getAllKerdesPstmt = conn.prepareStatement("SELECT * FROM kerdesek");
        getAllNyeremenyPstmt = conn.prepareStatement("SELECT * FROM nyeremeny");
        addKerdesPstmt = conn.prepareStatement("INSERT INTO kerdesek (kerdes, valasz1, valasz2, valasz3, valasz4, helyesvalasz) VALUES (?,?,?,?,?,?)");  
        updateKerdesPstmt = conn.prepareStatement("UPDATE kerdesek SET kerdes=?, valasz1=?, valasz2=?, valasz3=?, valasz4=?, helyesvalasz=? WHERE idkerdesek=?");
        removeKerdesPstmt = conn.prepareStatement("DELETE from kerdesek WHERE idkerdesek=?");
    }
     
    @Override
    public List<Kerdes> getAllKerdes() throws SQLException {
        List<Kerdes> kerdesek = new ArrayList<>();

        ResultSet rs = getAllKerdesPstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("idkerdesek");
            String kerdes = rs.getString("kerdes");
            String valasz1 = rs.getString("valasz1");
            String valasz2 = rs.getString("valasz2");
            String valasz3 = rs.getString("valasz3");
            String valasz4 = rs.getString("valasz4");
            int helyesvalasz = rs.getInt("helyesvalasz");

            Kerdes k = new Kerdes(id, kerdes, valasz1, valasz2, valasz3, valasz4, helyesvalasz);
            kerdesek.add(k);
        }
        rs.close();
        return kerdesek;
    }
    
    @Override
    public List<Nyeremeny> getAllNyeremeny() throws SQLException {
        List<Nyeremeny> nyeremenyek = new ArrayList<>();

        ResultSet rs = getAllNyeremenyPstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("idnyeremeny");
            int garnyer = rs.getInt("nyeremeny");

            Nyeremeny k = new Nyeremeny(id, garnyer);
            nyeremenyek.add(k);
        }
        rs.close();
        return nyeremenyek;
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

    @Override
    public void exportToFile(File f) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addKerdes(Kerdes k) throws SQLException {
        addKerdesPstmt.setString(1, k.getKerdes());
        addKerdesPstmt.setString(2, k.getValasz1());
        addKerdesPstmt.setString(3, k.getValasz2());
        addKerdesPstmt.setString(4, k.getValasz3());
        addKerdesPstmt.setString(5, k.getValasz4());
        addKerdesPstmt.setInt(6, k.getHelyesvalasz());
        return addKerdesPstmt.executeUpdate();
    }

    @Override
    public int updateKerdes(Kerdes k) throws SQLException {
        updateKerdesPstmt.setString(1, k.getKerdes());
        updateKerdesPstmt.setString(2, k.getValasz1());
        updateKerdesPstmt.setString(3, k.getValasz2());
        updateKerdesPstmt.setString(4, k.getValasz3());
        updateKerdesPstmt.setString(5, k.getValasz4());
        updateKerdesPstmt.setInt(6, k.getHelyesvalasz());
        updateKerdesPstmt.setInt(7, k.getId());
        return updateKerdesPstmt.executeUpdate();
    }

    @Override
    public int removeKerdes(Kerdes k) throws SQLException {
        removeKerdesPstmt.setInt(1, k.getId());
        System.out.println("ID kerdés: "+k.getId());
        return removeKerdesPstmt.executeUpdate();
    }

    
}
