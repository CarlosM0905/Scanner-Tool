/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Rodriguez
 */
public class Escaner {

    public static int indice = 0;

    //Palabras reservadas por el lenguaje
    private static final String[] PALABRAS_R = {
        "break", "case", "catch", "class", "const", "continue", "default",
        "delete", "do", "else", "enum", "false", "for", "friend",
        "goto", "if", "inline", "namespace", "new", "operator", "private",
        "protected", "public", "register", "return", "signed", "sizeof", "static",
        "struct", "switch", "this", "throw", "true", "try", "typedef", "typeid", "typename",
        "union", "unsigned", "using", "virtual", "volatile", "while"
    };

    // Tipos de dato permitidos por el lenguaje
    private static final String[] TIPOS_DATO = {
        "bool", "byte", "double", "float", "int", "long", "short", "void"
    };

    public static String leerArchivo() {
        try {
            FileReader lector = new FileReader("src/Model/codigo.txt");
            BufferedReader archivo = new BufferedReader(lector);
            String cadena = "";
            String linea = "";

            do {
                linea = archivo.readLine();
                if (linea != null) {
                    cadena += linea + "\n";
                }

            } while (linea != null);

            return cadena;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Escaner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Escaner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean esReservada(String palabra) {
        int inicio = 0;
        int fin = PALABRAS_R.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;

            if (PALABRAS_R[medio].compareTo(palabra) == 0) {
                return true;
            } else if (PALABRAS_R[medio].compareTo(palabra) > 0) {
                fin = medio - 1;
            } else if (PALABRAS_R[medio].compareTo(palabra) < 0) {
                inicio = medio + 1;
            }
        }
        return false;
    }

    public static boolean esTipoDato(String palabra) {
        int inicio = 0;
        int fin = TIPOS_DATO.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;

            if (TIPOS_DATO[medio].compareTo(palabra) == 0) {
                return true;
            } else if (TIPOS_DATO[medio].compareTo(palabra) > 0) {
                fin = medio - 1;
            } else if (TIPOS_DATO[medio].compareTo(palabra) < 0) {
                inicio = medio + 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        String cadena = leerArchivo();
        for (int i = 0; i < 30; i++) {
            Simbolo simbolo = obtenerSimbolo(cadena);
            if(simbolo != null){
                System.out.println(simbolo);
                
            }
            else{
                break;
            }
        }
    }

    public static Simbolo obtenerSimbolo(String cadena) {
        if (indice <= cadena.length()) {
            // Se omiten espacios en blanco y saltos de linea
            while (cadena.charAt(indice) == ' ' || cadena.charAt(indice) == '\n') {
                indice++;
                if(indice == cadena.length()){
                    return null;
                }
            }
            
            // Se verfica si es un comentario multilinea
            if(cadena.charAt(indice) == '/' && cadena.charAt(indice+1) == '*'){
                //Si lo es, se omite
                indice+=2;
                do{
                    indice++;
                }while(cadena.charAt(indice) != '*' && cadena.charAt(indice+1) != '/');
                indice+=2;
            }
            
            // Se verifica si es un comentario simple
            if(cadena.charAt(indice) == '/' && cadena.charAt(indice+1) == '/'){
                //Si lo es, se omite
                indice+=2;
                do{
                    indice++;
                }while(cadena.charAt(indice) != '\n');
                indice+=1;
            }
            
            
            // Es un identificador, palabra clave o funcion
            if (cadena.charAt(indice) >= 'a' && cadena.charAt(indice) <= 'z'
                    || cadena.charAt(indice) >= 'A' && cadena.charAt(indice) <= 'Z'
                    || cadena.charAt(indice) == '_') {
                return esIdentificador(cadena, indice);
            }

            // Es un numero
            if (cadena.charAt(indice) >= '0' && cadena.charAt(indice) <= '9') {
                return esNumero(cadena, indice);
            }

            // Es operador relacional
            if (cadena.charAt(indice) == '=' && cadena.charAt(indice + 1) == '='
                    || cadena.charAt(indice) == '>' || cadena.charAt(indice) == '<'
                    || cadena.charAt(indice) == '!' && cadena.charAt(indice + 1) == '=') {
                return esOperadorR(cadena, indice);
            }

            //Es operador logico
            if (cadena.charAt(indice) == '&' || cadena.charAt(indice) == '|'
                    || cadena.charAt(indice) == '!') {
                return esOperadorL(cadena, indice);
            }

            //Es operador aritmetico
            if (cadena.charAt(indice) == '+' || cadena.charAt(indice) == '-'
                    || cadena.charAt(indice) == '*' || cadena.charAt(indice) == '/'
                    || cadena.charAt(indice) == '%') {
                return esOperadorA(cadena, indice);
            }

            // Es cadena 
            if (cadena.charAt(indice) == '"') {
                return esCadena(cadena, indice);
            }

            //Es un caracter
            if (cadena.charAt(indice) == '\'') {
                return esCaracter(cadena, indice);
            }

            //Es cualquier otro valor
            if (cadena.charAt(indice) >= '!' && cadena.charAt(indice) <= '/'
                    || cadena.charAt(indice) >= ':' && cadena.charAt(indice) <= '@'
                    || cadena.charAt(indice) >= '[' && cadena.charAt(indice) <= '`'
                    || cadena.charAt(indice) >= '{' && cadena.charAt(indice) <= '¡') {

                String otro = "";
                otro += cadena.charAt(indice);
                indice++;

                return new Simbolo(otro, "Otro");
            }
        }
        return new Simbolo("", "Comentario Omitido");
    }

    private static Simbolo esIdentificador(String cadena, int indice) {

        String id = "";
        Simbolo simbolo;

        while (cadena.charAt(indice) >= 'a' && cadena.charAt(indice) <= 'z'
                || cadena.charAt(indice) >= 'A' && cadena.charAt(indice) <= 'Z'
                || cadena.charAt(indice) >= '0' && cadena.charAt(indice) <= '9'
                || cadena.charAt(indice) == '_') {
            id += cadena.charAt(indice);
            indice++;
        }

        Escaner.indice = indice;

        if (esReservada(id)) {
            return simbolo = new Simbolo(id, "Palabra Reservada");
        } else if (esTipoDato(id)) {
            return simbolo = new Simbolo(id, "Tipo de Dato");
        } else {
            return simbolo = new Simbolo(id, "Identificador");
        }

    }

    private static Simbolo esNumero(String cadena, int indice) {

        String numero = "";
        Simbolo simbolo;

        while (cadena.charAt(indice) >= '0' && cadena.charAt(indice) <= '9'
                || cadena.charAt(indice) == '.') {
            numero += cadena.charAt(indice);
            indice++;
        }

        Escaner.indice = indice;

        return simbolo = new Simbolo(numero, "Numero");
    }

    private static Simbolo esOperadorR(String cadena, int indice) {
        String operador = "";
        Simbolo simbolo;

        while (cadena.charAt(indice) == '=' || cadena.charAt(indice) == '!'
                || cadena.charAt(indice) == '>' || cadena.charAt(indice) == '<') {
            operador += cadena.charAt(indice);
            indice++;
        }

        Escaner.indice = indice;

        if(operador.equals("<<") || operador.equals(">>")){
            return simbolo = new Simbolo(operador, "Flujo de programa");
        }else{
            return simbolo = new Simbolo(operador, "Operador Relacional");
        }
    }

    private static Simbolo esOperadorL(String cadena, int indice) {
        String operador = "";
        Simbolo simbolo;

        while (cadena.charAt(indice) == '&' || cadena.charAt(indice) == '|'
                || cadena.charAt(indice) == '!') {
            operador += cadena.charAt(indice);
            indice++;
        }

        Escaner.indice = indice;

        return simbolo = new Simbolo(operador, "Operador Logico");
    }

    private static Simbolo esOperadorA(String cadena, int indice) {
        String operador = "";
        Simbolo simbolo;

        while (cadena.charAt(indice) == '+' || cadena.charAt(indice) == '-'
                || cadena.charAt(indice) == '*' || cadena.charAt(indice) == '/'
                || cadena.charAt(indice) == '%' || cadena.charAt(indice) == '=') {
            operador += cadena.charAt(indice);
            indice++;
        }

        Escaner.indice = indice;

        return simbolo = new Simbolo(operador, "Operador Aritmetico");
    }

    private static Simbolo esCadena(String cadena, int indice) {
        String cad = "";
        Simbolo simbolo;

        do {
            cad += cadena.charAt(indice);
            indice++;
        } while (cadena.charAt(indice) != '"');
        cad += cadena.charAt(indice++);

        Escaner.indice = indice;

        return simbolo = new Simbolo(cad, "Cadena");
    }

    private static Simbolo esCaracter(String cadena, int indice) {
        String caracter = "";
        Simbolo simbolo;

        do {
            caracter += cadena.charAt(indice);
            indice++;
        } while (cadena.charAt(indice) != '\'');

        caracter += cadena.charAt(indice++);

        Escaner.indice = indice;

        return simbolo = new Simbolo(caracter, "Caracter");
    }

}
