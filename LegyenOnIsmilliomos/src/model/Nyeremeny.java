/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Akos
 */
public class Nyeremeny {
    private int id;
    private int garnyer;

    public Nyeremeny() {
    }

    public Nyeremeny(int id, int garnyer) {
        this.id = id;
        this.garnyer = garnyer;
    }

    @Override
    // Nyeremény formázott kiiratása
    public String toString() {
        if (id < 10 ) {
            
            return "    " + id + "    "+ garnyer/1000 + " 000";
        } else if (id == 10){
            return "  " + id + "    "+ garnyer/1000000 + " 500 000";
        }
        return "  "+id + "    "+ garnyer/1000000 + " 000 000";
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGarnyer() {
        return garnyer;
    }

    public void setGarnyer(int garnyer) {
        this.garnyer = garnyer;
    }
    
    
    
    
    
}
