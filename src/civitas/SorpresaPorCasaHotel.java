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
public class SorpresaPorCasaHotel extends Sorpresa {
    
    SorpresaPorCasaHotel(int valor, String texto) {
        super.init();
        this.valor = valor;
        this.texto = texto;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            Jugador jugador = todos.get(actual);
            jugador.modificarSaldo(this.valor * jugador.cantidadCasasHoteles());
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n *--* Valor:" + valor + "*--*";
    }
}
