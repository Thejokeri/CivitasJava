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
public class Sorpresa {
    private String texto;
    private int valor;
    private MazoSorpresa mazo;
    private TipoSorpresa tipo;
    private Tablero tablero;
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero) {
        this.init();
        this.tipo = tipo;
        this.tablero = tablero;
    }
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero, int valor, String texto) {
        this.init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }
    
    Sorpresa(TipoSorpresa tipo, int valor, String texto) {
        this.init();
        this.tipo = tipo;
        this.valor = valor;
        this.texto = texto;
    }
    
    Sorpresa(TipoSorpresa tipo, MazoSorpresa mazo) {
        this.init();
        this.tipo = tipo;
        this.mazo = mazo;
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if(this.jugadorCorrecto(actual, todos)) {
            this.informe(actual, todos);
            
            if (this.tipo == TipoSorpresa.IRCARCEL) {
                this.aplicarAJugador_irCarcel(actual, todos);
            } else if (this.tipo == TipoSorpresa.IRCASILLA) {
                this.aplicarAJugador_irACasilla(actual, todos);
            } else if (this.tipo == TipoSorpresa.PAGARCOBRAR) {
                this.aplicarAJugador_pagarCobrar(actual, todos);
            } else if (this.tipo == TipoSorpresa.PORCASAHOTEL) {
                this.aplicarAJugador_porCasaHotel(actual, todos);
            } else if (this.tipo == TipoSorpresa.PORJUGADOR) {
                this.aplicarAJugador_porJugador(actual, todos);
            } else if (this.tipo == TipoSorpresa.SALIRCARCEL) {
                this.aplicarAJugador_salirCarcel(actual, todos);
            }
        }  
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
        Jugador jugador = todos.get(actual);
        int posicion = this.tablero.calcularTirada(jugador.getNumCasillaActual(), this.valor);
        this.tablero.nuevaPosicion(jugador.getNumCasillaActual(), posicion);
        jugador.moverACasilla(posicion);
        Casilla casilla = this.tablero.getCasilla(posicion);
        // casilla.recibeJugador(actual, todos);
    }
    
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
        todos.get(actual).encarcelar(this.tablero.getCarcel());    
    }
    
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        Jugador jugador = todos.get(actual);
        jugador.modificarSaldo(this.valor);
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        Jugador jugador = todos.get(actual);
        jugador.modificarSaldo(this.valor * jugador.cantidadCasasHoteles());
    }
    
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
        Jugador jugador = todos.get(actual);
        Sorpresa pago = new Sorpresa(TipoSorpresa.PAGARCOBRAR, -1*this.valor, this.texto);
        
        for(int i = 0; i < todos.size(); i++) {
            if (i != actual) {
                pago.aplicarAJugador(i, todos);
            }
        }
        
        Sorpresa cobro = new Sorpresa(TipoSorpresa.PAGARCOBRAR, (todos.size() - 1)*this.valor, this.texto);
        cobro.aplicarAJugador(actual, todos);
    }
    
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
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
    
    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Aplicando sorpresa: " + this.texto + " al jugador " + todos.get(actual).getNombre());
    }
    
    private void init(){
        this.valor = -1;
        this.tablero = null;
        this.mazo = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (0 <= actual && actual < todos.size());
    }
    
    void salirDelMazo() {
        if (this.tipo == TipoSorpresa.SALIRCARCEL) {
            this.mazo.inhabilitarCartaEspecial(this);
        }
    }
    
    public String toString() {
        return texto;
    }
    
    void usada() {
        if (this.tipo == TipoSorpresa.SALIRCARCEL) {
            this.mazo.habilitarCartaEspecial(this);
        }
    }
}
