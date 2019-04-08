/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Carlos Rodriguez
 */
public class Simbolo {

    private SimpleStringProperty cadena;
    private SimpleStringProperty tipo;
    private SimpleStringProperty longitud;

    public Simbolo() {
    }

    public Simbolo(String cadena, String tipo) {
        this.cadena = new SimpleStringProperty(cadena);
        this.tipo = new SimpleStringProperty(tipo);
        this.longitud = new SimpleStringProperty(String.valueOf(cadena.length()));
    }

    public String getCadena() {
        return cadena.get();
    }

    public void setCadena(String cadena) {
        this.cadena.set(cadena);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }
    
    public int getLongitud(){
        return Integer.parseInt(longitud.get());
    }

    public void setLongitud(int longitud){
        this.longitud.set(String.valueOf(longitud));
    }
    @Override
    public String toString() {
        return "Tipo:" + this.tipo.get()
                + " Cadena:" + this.cadena.get();
    }

}
