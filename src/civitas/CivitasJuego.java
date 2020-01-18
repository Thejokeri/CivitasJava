/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import GUI.Dado;

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
        this.jugadores = new ArrayList<>();
        
        for(int i = 0; i < totaljugadores; i++) {
            this.jugadores.add(new Jugador(nombres.get(i)));
        }
        
        this.gestorEstados = new GestorEstados();
        this.estado = this.gestorEstados.estadoInicial();
        this.indiceJugadorActual = Dado.getInstance().quienEmpieza(totaljugadores);
        this.inicializarTablero(new MazoSorpresa());
        this.inicializarMazoSorpresas(this.tablero);
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
        TituloPropiedad titulo = ((CasillaCalle) casilla).getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        
        if (res) {
            System.out.println("Lo has comprado");
        } else {
            System.out.println("No lo has comprado");
        }
        
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
        
        this.mazo.alMazo(new SorpresaPagarCobrar(-200, "Paga el impuesto de lujo"));
        this.mazo.alMazo(new SorpresaPagarCobrar(200, "Cobra"));
        
        this.mazo.alMazo(new SorpresaCasilla(tablero, 3, "Ve a Glorieta cuatro caminos"));
        this.mazo.alMazo(new SorpresaCasilla(tablero, 12, "Ve Avenida de America"));
        this.mazo.alMazo(new SorpresaCasilla(tablero, 23, "Ve a Paseo del Prado"));
        
        this.mazo.alMazo(new SorpresaPorCasaHotel(-100, "Pagas por cada casa y hotel que poseas"));
        this.mazo.alMazo(new SorpresaPorCasaHotel(100, "Cobras por cada casa y hotel que poseas"));
        
        this.mazo.alMazo(new SorpresaPorJugador(100, "Cada jugador paga el valor"));
        this.mazo.alMazo(new SorpresaPorJugador(200, "Cada jugador paga el valor"));
        
        this.mazo.alMazo(new SorpresaSalirCarcel(this.mazo));
        
        this.mazo.alMazo(new SorpresaIrCarcel(this.tablero));
        
        this.mazo.alMazo(new SorpresaJugadorEspeculador(1000, "Te conviertes en jugador especulador"));
    }
    
    private void inicializarTablero(MazoSorpresa mazo) {
        this.tablero = new Tablero(9);
        this.mazo = mazo;
        
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Ronda de Valencia", (float) 35, (float) 0.5, (float) 55, (float) 60, (float) 120)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Caja de Comunidad"));
        this.tablero.aniadeCasilla(new CasillaImpuesto((float) 200, "Impuesto sobre el capital"));
        
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Glorieta cuatro caminos", (float) 55, (float) 0.5, (float) 95, (float) 100, (float) 200)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Suerte"));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Calle bravo Murillo", (float) 65, (float) 0.5, (float) 115, (float) 120, (float) 240)));

        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Glorieta de Bilbao", (float) 75, (float) 0.5, (float) 135, (float) 140, (float) 280)));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Fuencarral", (float) 85, (float) 0.5, (float) 155, (float) 160, (float) 320)));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Avenida Felipe II", (float) 95, (float) 0.5, (float) 175, (float) 180, (float) 360)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Caja de Comunidad"));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Serrano", (float) 105, (float) 0.5, (float) 195, (float) 200, (float) 400)));
        this.tablero.aniadeCasilla(new Casilla("Parking Gratuito"));

        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Avenida de America", (float) 115, (float) 0.5, (float) 215, (float) 220, (float) 440)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Suerte"));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Calle de Cea Bermudez", (float) 125, (float) 0.5, (float) 235, (float) 240, (float) 480)));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Avenida de los Reyes Catolicos", (float) 135, (float) 0.5, (float) 255, (float) 260, (float) 520)));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Plaza de Espana", (float) 145, (float) 0.5, (float) 275, (float) 280, (float) 560)));
        this.tablero.aniadeJuez();

        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Puerta del Sol", (float) 155, (float) 0.5, (float) 295, (float) 300, (float) 600)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Caja de Comunidad"));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Gran Via", (float) 165, (float) 0.5, (float) 315, (float) 320, (float) 640)));
        this.tablero.aniadeCasilla(new CasillaSorpresa(this.mazo, "Suerte"));
        this.tablero.aniadeCasilla(new CasillaImpuesto((float) 100, "Impuesto de Lujo"));
        this.tablero.aniadeCasilla(new CasillaCalle(new TituloPropiedad("Paseo del Prado", (float) 205, (float) 0.5, (float) 395, (float) 400, (float) 800)));
    }
    
    private void pasarTurno() {
        this.indiceJugadorActual = (this.indiceJugadorActual + 1) % this.jugadores.size();
    }
    
    private ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ranking = new ArrayList<>(this.jugadores);
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
