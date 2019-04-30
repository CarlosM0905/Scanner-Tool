/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;

public class AFDP {

    private Pila<Simbolo> pila = new Pila<>();

    public void completarPila(String texto) {
        final int QF = 100;
        final int QE = -1;

        int Q = 0;

        while (Q != QF && Q != QE) {
            Simbolo simbolo = Escaner.obtenerSimbolo(texto);

            System.out.println(simbolo.getCadena());
            if (simbolo.getCadena().equals("if") || simbolo.getCadena().equals("else") || simbolo.getCadena().equals("while")
                    || simbolo.getCadena().equals("endif") || simbolo.getCadena().equals("endwhile") || simbolo.getCadena().equals("$")) {
                switch (Q) {
                    case 0:
                        if (simbolo.getCadena().equals("if") && pila.estaVacia()) {
                            pila.apilar(simbolo);
                            Q = 1;
                        } else if (simbolo.getCadena().equals("while") && pila.estaVacia()) {
                            pila.apilar(simbolo);
                            Q = 1;
                        } else {
                            Q = QE;
                            break;
                        }
                        break;
                    case 1:
                        if (!pila.estaVacia()) {
                            if (simbolo.getCadena().equals("if") || simbolo.getCadena().equals("while")) {
                                pila.apilar(simbolo);
                            } else if (simbolo.getCadena().equals("else") && pila.cima().getCadena().equals("if")) {
                                pila.apilar(simbolo);
                            } else if (simbolo.getCadena().equals("endif") && pila.cima().getCadena().equals("else")) {
                                pila.desapilar();
                                pila.desapilar();
                            } else if (simbolo.getCadena().equals("endif") && pila.cima().getCadena().equals("if")) {
                                pila.desapilar();
                            } else if (simbolo.getCadena().equals("endwhile") && pila.cima().getCadena().equals("while")) {
                                pila.desapilar();
                            } else {
                                Q = QE;
                                break;
                            }

                        } else if (simbolo.getCadena().equals("$") && pila.estaVacia()) {
                            Q = QF;
                            break;
                        } else if (simbolo.getCadena().equals("if") && pila.estaVacia()) {
                            pila.apilar(simbolo);
                        } else if (simbolo.getCadena().equals("while") && pila.estaVacia()) {
                            pila.apilar(simbolo);
                        } else {
                            Q = QE;
                            break;
                        }
                }
            }

        }

        if (Q == QF) {
            reconoce();
        } else {
            error();
        }
    }

    private void reconoce() {
        JOptionPane.showMessageDialog(null, "BIEN");

    }

    private void error() {
        JOptionPane.showMessageDialog(null, "ERROR");
    }

    private void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("ajsdjagsd");

    }
}
