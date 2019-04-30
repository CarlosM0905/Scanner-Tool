package Model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Carlos Rodriguez
 * @param <Tipo>
 */
public class Pila<Tipo> implements Iterable<Tipo> {

    private class Nodo<Tipo> {

        Tipo valor;
        Nodo<Tipo> sgte;
    }

    Nodo<Tipo> tope;

    public boolean estaVacia() {
        return tope == null;
    }

    public Tipo cima() {
        return tope.valor;
    }

    public void apilar(Tipo valor) {
        if (tope == null) {
            tope = new Nodo<>();
            tope.valor = valor;
            tope.sgte = null;
        } else {
            Nodo<Tipo> nuevo = new Nodo<>();
            nuevo.valor = valor;
            nuevo.sgte = tope;
            tope = nuevo;
        }
    }

    public Tipo desapilar() {
        Tipo valor;
        if (tope != null) {
            valor = tope.valor;
            tope = tope.sgte;
        } else {
            throw new NoSuchElementException("Pila Vacia");
        }
        return valor;
    }

    @Override
    public Iterator<Tipo> iterator() {

        Iterator<Tipo> it = new Iterator<Tipo>() {
            Nodo<Tipo> topeAux = tope;

            @Override
            public boolean hasNext() {
                return topeAux != null;
            }

            @Override
            public Tipo next() {

                Tipo valor = topeAux.valor;
                topeAux = topeAux.sgte;
                return valor;
            }
        };
        return it;
    }

    public static void main(String[] args) {
        Pila<String> pilita = new Pila<>();
        pilita.apilar("Hola");
        pilita.apilar("Mundo");
        pilita.apilar("Desde");
        pilita.apilar("Una");
        pilita.apilar("Pila Iterable");

        Iterator<String> it = pilita.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
