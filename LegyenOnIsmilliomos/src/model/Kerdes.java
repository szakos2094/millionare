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
public class Kerdes {

    private int id;
    private String kerdes;
    private String valasz1;
    private String valasz2;
    private String valasz3;
    private String valasz4;
    private int helyesvalasz;

    public Kerdes() {
    }

    public Kerdes(int id, String kerdes, String valasz1, String valasz2, String valasz3, String valasz4, int helyesvalasz) {
        this.id = id;
        this.kerdes = kerdes;
        this.valasz1 = valasz1;
        this.valasz2 = valasz2;
        this.valasz3 = valasz3;
        this.valasz4 = valasz4;
        this.helyesvalasz = helyesvalasz;
    }

    @Override
    public String toString() {
        return id + ", " + kerdes ;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKerdes() {
        return kerdes;
    }

    public void setKerdes(String kerdes) {
        this.kerdes = kerdes;
    }

    public String getValasz1() {
        return valasz1;
    }

    public void setValasz1(String valasz1) {
        this.valasz1 = valasz1;
    }

    public String getValasz2() {
        return valasz2;
    }

    public void setValasz2(String valasz2) {
        this.valasz2 = valasz2;
    }

    public String getValasz3() {
        return valasz3;
    }

    public void setValasz3(String valasz3) {
        this.valasz3 = valasz3;
    }

    public String getValasz4() {
        return valasz4;
    }

    public void setValasz4(String valasz4) {
        this.valasz4 = valasz4;
    }

    public int getHelyesvalasz() {
        return helyesvalasz;
    }

    public void setHelyesvalasz(int helyesvalasz) {
        this.helyesvalasz = helyesvalasz;
    }
}
