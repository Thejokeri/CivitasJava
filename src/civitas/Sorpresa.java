/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author thejoker
 */
public abstract class Sorpresa {
    public String texto;
    public int valor;
    public MazoSorpresa mazo;
    public Tablero tablero;
    
    public void init() {
        this.valor = -1;
        this.tablero = null;
        this.mazo = null;
    }
    
    // Ir Carcel
    Sorpresa(Tablero tablero) {
        this.init();
        this.tablero = tablero;
    }
    
    // Ir Casilla
    Sorpresa(Tablero tablero, int valor, String texto) {
        this.init();
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    // Resto de sorpresas
    Sorpresa(int valor, String texto) {
        this.init();
        this.valor = valor;
        this.texto = texto;
    }
    
    // Salir Carcel
    Sorpresa(MazoSorpresa mazo) {
        this.init();
        this.mazo = mazo;
    }
    
    
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    public void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Aplicando sorpresa: " + this.texto + " al jugador " + todos.get(actual).getNombre());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (0 <= actual && actual < todos.size());
    }
    
    public String toString() {
        return texto;
    }
}
