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
public class CasillaSorpresa extends Casilla {
    private Sorpresa sorpresa;
    private MazoSorpresa mazo;
    
    CasillaSorpresa(MazoSorpresa mazo, String nombre) {
        super(nombre);
        this.mazo = mazo;
    }
    
    public void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos) {
        if (this.jugadorCorrecto(actual, todos)) {
            sorpresa = this.mazo.siguiente();
            super.informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
