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
public class SorpresaCasilla extends Sorpresa {

    SorpresaCasilla(Tablero tablero, int valor, String texto) {
        super(tablero, valor, texto);
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            
            Jugador jugador = todos.get(actual);
            int posicion = this.tablero.calcularTirada(jugador.getNumCasillaActual(), this.valor);
            this.tablero.nuevaPosicion(jugador.getNumCasillaActual(), posicion);
            jugador.moverACasilla(posicion);
            Casilla casilla = this.tablero.getCasilla(posicion);
            casilla.recibeJugador(actual, todos);
        }
    }
}
