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
public class SorpresaSalirCarcel extends Sorpresa {

    SorpresaSalirCarcel(MazoSorpresa mazo) {
        super.init();
        this.mazo = mazo;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            boolean encontrado = false;

            for(int i = 0; i < todos.size() && !encontrado; i++) {
                if (i != actual) {
                    encontrado = todos.get(i).tieneSalvoconducto();
                }
            }

            if (!encontrado) {
                todos.get(actual).obtenerSalvoconducto(this);
                this.salirDelMazo();
            }
        }
    }
    
    void salirDelMazo() {
        this.mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada() {
        this.mazo.habilitarCartaEspecial(this);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
