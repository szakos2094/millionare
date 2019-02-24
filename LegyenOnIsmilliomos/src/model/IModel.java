/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Akos
 */
public interface IModel {
    
    List<Kerdes>getAllKerdes() throws SQLException;
    int addKerdes(Kerdes k) throws SQLException;
    int updateKerdes(Kerdes k) throws SQLException;
    int removeKerdes(Kerdes k) throws SQLException;
    
    List<Nyeremeny>getAllNyeremeny() throws SQLException; 
    
    
    void close() throws SQLException;
    void exportToFile(File f) throws Exception;
    
}
