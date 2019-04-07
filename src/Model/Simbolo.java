/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Carlos Rodriguez
 */
public class Simbolo {
    private String cadena;
    private String tipo;

    public Simbolo() {
    }

    public Simbolo(String cadena, String tipo) {
        this.cadena = cadena;
        this.tipo = tipo;
    }
   
    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Tipo:" + this.tipo
                + " Cadena:" + this.cadena;
    }
    
    
}
