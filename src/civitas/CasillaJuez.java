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
public class CasillaJuez extends Casilla {
    private int carcel;
    
    CasillaJuez(int numCasillaCarcel, String nombre) {
        super(nombre);
        this.carcel = numCasillaCarcel;
    }
    
    public void recibeJugador_juez(int actual, ArrayList<Jugador> todos) {
        todos.get(actual).encarcelar(carcel);
    }
}
