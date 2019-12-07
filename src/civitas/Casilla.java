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
    private int carcel;
    private float importe;
    private String nombre;
    private TituloPropiedad tituloPropiedad;
    private Sorpresa sorpresa;
    private MazoSorpresa mazo;
    private TipoCasilla tipo;
    
    Casilla(String nombre) {
        this.init();
        this.nombre = nombre;
        this.tipo = TipoCasilla.DESCANSO;
    }

    Casilla(TituloPropiedad titulo) {
        this.init();
        this.tituloPropiedad = titulo;
        this.nombre = titulo.getNombre();
        this.tipo = TipoCasilla.CALLE;
    }
    
    Casilla(float cantidad, String nombre) {
        this.init();
        this.importe = cantidad;
        this.nombre = nombre;
        this.tipo = TipoCasilla.IMPUESTO;
    }
    
    Casilla(int numCasillaCarcel, String nombre) {
        this.init();
        this.carcel = numCasillaCarcel;
        this.nombre = nombre;
        this.tipo = TipoCasilla.JUEZ;
    }
    
    Casilla(MazoSorpresa mazo, String nombre) {
        this.init();
        this.mazo = mazo;
        this.nombre = nombre;
        this.tipo = TipoCasilla.SORPRESA;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    TituloPropiedad getTituloPropiedad() {
        return this.tituloPropiedad;
    }
    
    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario diario = Diario.getInstance();
        diario.ocurreEvento("Jugador: " + todos.get(actual) + " Casilla: " + this.toString());   
    }
    
    private void init() {
        this.carcel = -1;
        this.importe = (float) 0;
        this.nombre = "Defecto";
        this.tituloPropiedad = null;
        this.sorpresa = null;
        this.mazo = null;
        this.tipo = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (0 <= actual && actual < todos.size());
    }
    
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        switch(this.tipo) {
            case CALLE:
                this.recibeJugador_calle(actual, todos);
                break;
            case IMPUESTO:
                this.recibeJugador_impuesto(actual, todos);
                break;
            case JUEZ:
                this.recibeJugador_juez(actual, todos);
                break;
            case SORPRESA:
                this.recibeJugador_sorpresa(actual, todos);
                break;
            default:
                this.informe(actual, todos);
                break;
        }
    }
    
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos) {
        if (this.jugadorCorrecto(actual, todos)) {
            this.informe(actual, todos);
            Jugador jugador = todos.get(actual);
            
            if (!this.tituloPropiedad.tienePropietario()) {
                jugador.puedeComprarCasilla();
            } else {
                this.tituloPropiedad.tramitarAlquiler(jugador);
            }
        }
    }
    
    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos) {
        if (this.jugadorCorrecto(actual, todos)) {
            this.informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    
    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos) {
        if (this.jugadorCorrecto(actual, todos)) {
            this.informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
    
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos) {
        if (this.jugadorCorrecto(actual, todos)) {
            sorpresa = this.mazo.siguiente();
            this.informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
    public String toString() {
        if (importe != (float) 0.0)
            return "\n   *---* " + nombre + " *---*"
                    + "  *---* Importe: " + importe + " *---*";
        else
            return "\n   *---* " + nombre + " *---*";
    }
}
