/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author thejoker
 */
public class CivitasJuego {
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private GestorEstados gestorEstados;
    private MazoSorpresa mazo;
    private EstadosJuego estado;
    
    public CivitasJuego(ArrayList<String> nombres) {
        int totaljugadores = 4;
        this.jugadores = new ArrayList<Jugador>();
        
        for(int i = 0; i < totaljugadores; i++) {
            this.jugadores.add(new Jugador(nombres.get(i)));
        }
        
        this.gestorEstados = new GestorEstados();
        this.estado = this.gestorEstados.estadoInicial();
        this.indiceJugadorActual = Dado.getInstance().quienEmpieza(totaljugadores);
        this.tablero = new Tablero(30);
        
        Casilla casilla = new Casilla(1, "Defecto");
        
        for(int i = 0; i < 39; i++) {  
            this.tablero.aniadeCasilla(casilla);
        }
      
        this.tablero.aniadeJuez();
              
        this.mazo = new MazoSorpresa();
    }
    
    private void avanzaJugador() {
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual, tirada, posicionNueva;
        
        posicionActual = jugadorActual.getNumCasillaActual();
        tirada = Dado.getInstance().tirar();
        posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        
        Casilla casilla = this.tablero.getCasilla(posicionActual);
        
        this.contabilizarPasosPorSalida(jugadorActual);
        
        jugadorActual.moverACasilla(posicionNueva);
       
        casilla.recibeJugador(this.indiceJugadorActual, this.jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int ip) {
        return this.getJugadorActual().cancelarHipoteca(ip);
    }
    
    public boolean comprar() {
        Jugador jugadorActual = this.getJugadorActual();
        Casilla casilla = this.getCasillaActual();
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        
        return res;
    }
    
    public boolean construirCasa(int ip) {
        return this.getJugadorActual().construirCasa(ip);
    }
    
    public boolean construirHotel(int ip) {
        return this.getJugadorActual().construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual) {
        while( this.tablero.getPorSalida() > 0 ) {
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego() {
        return this.getJugadorActual().enBancarrota();
    }
    
    public Casilla getCasillaActual() {
        return this.tablero.getCasilla(this.getJugadorActual().getNumCasillaActual());
    }
    
    public Jugador getJugadorActual() {
        return this.jugadores.get(this.indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip) {
        return this.getJugadorActual().hipotecar(ip);
    }
    
    public String infoJugadorTexto() {
        String salida = this.getJugadorActual().toString();
        
        if (this.finalDelJuego())
            salida += this.ranking(); 
        
        return salida;
    }
    
    private void inicializarMazoSorpresas(Tablero tablero) {
        this.tablero = tablero;
        MazoSorpresa mazo = new MazoSorpresa();
        this.mazo = mazo;
    }
    
    private void inicializarTablero(MazoSorpresa mazo) {
        this.mazo = mazo;
        Tablero tablero = new Tablero(30);
        this.tablero = tablero;
    }
    
    private void pasarTurno() {
        this.indiceJugadorActual = (this.indiceJugadorActual + 1) % this.jugadores.size();
        this.estado = EstadosJuego.INICIO_TURNO;
    }
    
    private ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ranking = new ArrayList<Jugador>(this.jugadores);
        Collections.sort(ranking);
        
        return ranking;
    }
    
    public boolean salirCarcelPagando() {
        boolean salida = this.getJugadorActual().salirCarcelPagando();
        
        if (salida) {
            this.estado = EstadosJuego.DESPUES_CARCEL;
        }
        
        return salida;
    }
    
    public boolean salirCarcelTirando() {
        boolean salida = this.getJugadorActual().salirCarcelTirando();
        
        if (salida) {
            this.estado = EstadosJuego.DESPUES_CARCEL;
        }
        
        return salida;
    }
    
    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = this.getJugadorActual();
        OperacionesJuego operacion = this.gestorEstados.operacionesPermitidas(jugadorActual, this.estado);
     
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            this.pasarTurno();
            this.siguientePasoCompletado(operacion);
        }else if (operacion == OperacionesJuego.AVANZAR) {
            this.avanzaJugador();
            this.siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion) {
        this.estado = this.gestorEstados.siguienteEstado(this.getJugadorActual(), this.estado, operacion);
    }
    
    public boolean vender(int ip) {
        return this.getJugadorActual().vender(ip);
    }
}
