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
public class Casilla {
    private String nombre;
    
    Casilla(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public void informe(int actual, ArrayList<Jugador> todos) {
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("Jugador: " + todos.get(actual) + " Casilla: " + this.toString());   
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (0 <= actual && actual < todos.size());
    }
    
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        if(this.jugadorCorrecto(actual, todos)) {
            if (this instanceof CasillaCalle){ 
                ((CasillaCalle) this).recibeJugador_calle(actual, todos);
            } else if (this instanceof CasillaImpuesto) {
                ((CasillaImpuesto) this).recibeJugador_impuesto(actual, todos);
            } else if (this instanceof CasillaJuez) {
                ((CasillaJuez) this).recibeJugador_juez(actual, todos);
            } else if (this instanceof CasillaSorpresa) {
                ((CasillaSorpresa) this).recibeJugador_sorpresa(actual, todos);
            } else {
                this.informe(actual, todos);
            }
        }
    }

    @Override
    public String toString() {
        return "\n   *---* " + nombre + " *---*";
    }
}
