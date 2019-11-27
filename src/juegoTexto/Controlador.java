/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;

/**
 *
 * @author thejoker
 */

public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega() {
        this.vista.setCivitasJuego(juego);
        
        while(!this.juego.finalDelJuego()) {
            this.vista.pausa();
            
            OperacionesJuego siguientePaso = this.juego.siguientePaso();
            this.vista.mostrarSiguienteOperacion(siguientePaso);
            
            if (siguientePaso != OperacionesJuego.PASAR_TURNO) {
                this.vista.mostrarEventos();
            }
            
            if (!this.juego.finalDelJuego()) {
                switch(siguientePaso) {
                    case COMPRAR:
                        Respuestas res = this.vista.comprar();
                        
                        if (res == Respuestas.SI) {
                           this.juego.comprar();
                        }
                        
                        break;
                    case GESTIONAR:
                        this.vista.gestionar();
                        
                        int gest = this.vista.getGestion();
                        int ip = this.vista.getPropiedad();
                        
                        GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[gest];
                        
                        OperacionInmobiliaria oi = new OperacionInmobiliaria(gestion, ip);
                        
                        if (gestion == GestionesInmobiliarias.VENDER) {
                            this.juego.vender(ip);
                        } else if (gestion == GestionesInmobiliarias.HIPOTECAR) {
                            this.juego.hipotecar(ip);
                        } else if (gestion == GestionesInmobiliarias.CANCELAR_HIPOTECA) {
                            this.juego.cancelarHipoteca(ip);
                        } else if (gestion == GestionesInmobiliarias.CONSTRUIR_CASA) {
                            this.juego.construirCasa(ip);
                        } else if (gestion == GestionesInmobiliarias.CONSTRUIR_HOTEL) {
                            this.juego.construirHotel(ip);
                        }
                        
                        
                        break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = this.vista.salirCarcel();
                        
                        if (salida == SalidasCarcel.PAGANDO) {
                            this.juego.salirCarcelPagando();
                        } else {
                            this.juego.salirCarcelTirando();
                        }
                        
                        break;
                }
                this.juego.siguientePasoCompletado(siguientePaso);
                this.vista.actualizarVista();
            }
            
        }
        
        this.juego.infoJugadorTexto();
    }
}
