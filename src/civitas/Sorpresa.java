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

    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    public void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Aplicando sorpresa: " + texto + " al jugador " + todos.get(actual).getNombre());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (0 <= actual && actual < todos.size());
    }
    
    @Override
    public String toString() {
        return "\n *--* Texto:" + texto + "*--*";
    }
}
