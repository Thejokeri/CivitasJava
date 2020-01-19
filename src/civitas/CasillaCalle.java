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
public class CasillaCalle extends Casilla {
    private TituloPropiedad tituloPropiedad;
    private float importe;
    
    CasillaCalle(TituloPropiedad titulo) {
        super(titulo.getNombre());
        this.tituloPropiedad = titulo;
        this.importe = titulo.getPrecioCompra();
    }
    
    TituloPropiedad getTituloPropiedad() {
        return this.tituloPropiedad;
    }
    
    public void recibeJugador_calle(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        Jugador jugador = todos.get(actual);

        if (!this.tituloPropiedad.tienePropietario()) {
            jugador.puedeComprarCasilla();
            todos.get(actual).puedeComprar = true;
        } else {
            this.tituloPropiedad.tramitarAlquiler(jugador);
            todos.get(actual).puedeComprar = false;
        }
    }
    
    @Override
    public String toString() {
        return "\n   *---* Nombre de la propiedad: " + super.getNombre() + " *---*"
                + "\n *---* Importe: " + Float.toString(importe) + "*---*";
    }
}
