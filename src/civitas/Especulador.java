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
public class Especulador extends Jugador {
    protected static int FactorEspeculador = 2;
    private int fianza;
    
    public Especulador(Jugador otro, int fianza) {
        super(otro);
        this.fianza = fianza;
        
        for(TituloPropiedad propiedad : otro.getPropiedades()) {
            propiedad.actualizarPropietarioPorConversion(this);
        }
    }
    
    @Override
    public int getCasaMax() {
        return super.getCasaMax() * Especulador.FactorEspeculador;
    }
    
    @Override
    public int getHotelesMax() {
        return super.getHotelesMax() * Especulador.FactorEspeculador;
    }
    
    private boolean pagarFianza(){
        boolean puedePagar = super.puedoGastar(fianza);
        
        if (puedePagar){
            modificarSaldo(-fianza);
        }
        
        return puedePagar;
    }
    
    @Override
    boolean encarcelar(int numCasillaCarcel) {
        if(!super.tieneSalvoconducto()) {
            if (!this.pagarFianza()){
                this.moverACasilla(numCasillaCarcel);
                this.encarcelado = true;
                Diario.getInstance().ocurreEvento("El jugador ha sido encarcelado");
            }
        }
        
        return this.encarcelado;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = this.paga(cantidad/2);
        }
        
        return salida;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean puedoEdificarCasa = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if (this.puedoGastar(precio)) {
            if (propiedad.getNumHoteles() < this.getCasaMax()) {
                puedoEdificarCasa = true;
            }
        }
        
        return puedoEdificarCasa;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean puedoEdificarHotel = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if (this.puedoGastar(precio)) {
            if (propiedad.getNumHoteles() < this.getHotelesMax()) {
                if (propiedad.getNumCasas() >= Jugador.CasasPorHotel) {
                    puedoEdificarHotel = true;
                }
            }
        }
        
        return puedoEdificarHotel;
    }
    
    @Override
    public String toString() {
        String salida = "*+++*+++* Jugador Especulador *+++*+++*\n";
        salida += super.toString();
        
        return salida;
    }
}
