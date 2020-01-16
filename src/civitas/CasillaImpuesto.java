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
public class CasillaImpuesto extends Casilla {
    private float importe;
    
    CasillaImpuesto(float cantidad, String nombre) {
        super(nombre);
        this.importe = cantidad;
    }
    
    public void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos) {
        todos.get(actual).pagaImpuesto(importe);
    }
    
    public String toString() {
        return "\n   *---* " + super.getNombre() + " *---*"
                    + "  *---* Importe: " + importe + " *---*";
    }
}
