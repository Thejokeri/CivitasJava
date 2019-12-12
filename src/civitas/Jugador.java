/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.lang.Float;
import java.util.ArrayList;
import GUI.Dado;

/**
 *
 * @author thejoker
 */
public class Jugador implements Comparable<Jugador> {
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    private ArrayList<TituloPropiedad> propiedades = new ArrayList<TituloPropiedad>();
    private SorpresaSalirCarcel salvoconducto;
    
    
    Jugador(String nombre) {
        this.nombre = nombre;
        this.numCasillaActual = 0;
        this.saldo = Jugador.SaldoInicial;
        this.encarcelado = false;
    }
    
    protected Jugador(Jugador otro) {
        this.encarcelado = otro.encarcelado;
        this.nombre = otro.nombre;
        this.numCasillaActual = otro.numCasillaActual;
        this.puedeComprar = otro.puedeComprar;
        this.saldo = otro.saldo;
        this.encarcelado = otro.encarcelado;
        this.propiedades = (ArrayList<TituloPropiedad>)otro.propiedades.clone();
    }
    
    boolean cancelarHipoteca(int ip) {
        boolean result = false;
        
        if (this.encarcelado) {
            return result;
        }
        
        if (this.existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = this.propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = this.puedoGastar(cantidad);
            
            if (puedoGastar) {
                result = propiedad.cancelarHipoteca(this);
                
                if (result) {
                    Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " cancela la hipoteca de la propiedad " + ip);
                }
            }
        }
        
        return result;
    }
    
    int cantidadCasasHoteles() {
        int cantidad = 0;
        
        for (TituloPropiedad propiedad: this.propiedades) {
            cantidad += propiedad.cantidadCasasHoteles();
        }
        
        return cantidad;
    }
    
    public int compareTo(Jugador otro) {
        Float f1 = this.saldo;
        Float f2 = otro.saldo;
        
        if (f1.compareTo(f2) > 0.0) {
            return 1;
        } else if (f1.compareTo(f2) == 0.0) {
            return 0;
        } else {
            return -1;
        }
    }
    
    boolean comprar(TituloPropiedad titulo) {
        boolean result = false;
        
        if (this.encarcelado) {
            return result;
        }
        
        if (this.puedeComprar) {
            float precio = titulo.getPrecioCompra();
            
            if (this.puedoGastar(precio)) {
                result = titulo.comprar(this);
                
                if (result) {
                    this.propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador "+ this.nombre +" compra la propiedad " + titulo.getNombre());
                }
            }
        }
        
        return result;
    } 
    
    boolean construirCasa(int ip) {
        boolean result = false;
        
        if (this.encarcelado) {
            return result;
        }
        
        if (this.existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = this.propiedades.get(ip);
            boolean puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
            
            if (puedoEdificarCasa) {
                result = propiedad.construirCasa(this);
                
                if (result) {
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye casa en la propiedad "+ ip);
                }
            }
        }
        
        return result;
    }
    
    boolean construirHotel(int ip) {
        boolean result = false;
        
        if (this.encarcelado) {
            return result;
        }
        
        if (this.existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = this.propiedades.get(ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            
            if (puedoEdificarHotel) {
                result = propiedad.construirHotel(this);
                int casasPorHotel = Jugador.CasasPorHotel;
                propiedad.derruirCasas(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye hotel en la propiedad "+ ip);
            }
        }
        
        return result;
    }
    
    protected boolean debeSerEncarcelado() {
        boolean salida = false;
        
        if(!this.encarcelado && !this.tieneSalvoconducto()) {
            salida = true;
        } else if (this.tieneSalvoconducto()) {
            this.perderSalvoconducto();
            Diario.getInstance().ocurreEvento("El jugador se ha librado de la carcel");
        }
        
        return salida;
    }
    
    boolean enBancarrota() {
        return this.saldo <= 0;
    }
    
    boolean encarcelar(int numCasillaCarcel) {
        if(this.debeSerEncarcelado()) {
            this.moverACasilla(numCasillaCarcel);
            this.encarcelado = true;
            Diario.getInstance().ocurreEvento("El jugador ha sido encarcelado");
        }
        
        return this.encarcelado;
    }
    
    private boolean existeLaPropiedad(int ip) {
        return this.propiedades.get(ip) != null;
    }
    
    public int getCasaMax() {
        return Jugador.CasasMax;
    }
    
    int getCasasPorHotel() {
        return Jugador.CasasPorHotel;
    }
    
    public int getHotelesMax() {
        return Jugador.HotelesMax;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    int getNumCasillaActual() {
        return this.numCasillaActual;
    }
    
    private float getPrecioLibertad() {
        return Jugador.PrecioLibertad;
    }
    
    private float getPremioPasoSalida() {
        return Jugador.PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades() {
        return this.propiedades;
    }
    
    boolean getPuedeComprar() {
        return this.puedeComprarCasilla();
    }
    
    public float getSaldo() {
        return this.saldo;
    }
    
    boolean hipotecar(int ip) {
        boolean result = false;

        if (this.encarcelado) {
            return result;
        }

        if (this.existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = this.propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }

        if (result) {
            Diario.getInstance().ocurreEvento("El jugador #{nombre} hipoteca la propiedad #{ip}");
        }

        return result;
    }
    
    public boolean isEncarcelado() {
        return this.encarcelado;
    }
    
    boolean modificarSaldo(float cantidad) {
        this.saldo += cantidad;
        Diario.getInstance().ocurreEvento("Se ha modificado el saldo");
        
        return true;
    }
    
    boolean moverACasilla(int numCasilla) {
        boolean salida = false;
        
        if(!this.encarcelado) {
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario.getInstance().ocurreEvento("Se ha movido el jugador: " + this.nombre + " casillas: " + numCasilla);
            salida = true;
        }
        
        return salida;
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa) {
        boolean salida = false;
        
        if(!this.encarcelado) {
            salida = true;
            this.salvoconducto = sorpresa;
        }
        
        return salida;
    }
    
    boolean paga(float cantidad) {
        return this.modificarSaldo(-1*cantidad);
    }
    
    boolean pagaAlquiler(float cantidad) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = this.paga(cantidad);
        }
        
        return salida;
    }
    
    boolean pagaImpuesto(float cantidad) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = this.paga(cantidad);
        }
        
        return salida;
    }
    
    boolean pasaPorSalida() {
        this.modificarSaldo(10000);
        Diario.getInstance().ocurreEvento("El jugador ha pasado por la salida");
        
        return true;
    }
    
    private void perderSalvoconducto() {
        this.salvoconducto.usada();
        this.salvoconducto = null;
    }
    
    boolean puedeComprarCasilla() {
        this.puedeComprar = !this.encarcelado ;
        
        return this.puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando() {
        return this.saldo >= 200;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean puedoEdificarCasa = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if (this.puedoGastar(precio)) {
            if (propiedad.getNumCasas() < Jugador.CasasMax) {
                puedoEdificarCasa = true;
            }
        }
        
        return puedoEdificarCasa;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean puedoEdificarHotel = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if (this.puedoGastar(precio)) {
            if (propiedad.getNumHoteles() < Jugador.HotelesMax) {
                if (propiedad.getNumCasas() >= Jugador.CasasPorHotel) {
                    puedoEdificarHotel = true;
                }
            }
        }
        
        return puedoEdificarHotel;
    }
   
    public boolean puedoGastar(float precio) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = (this.saldo >= precio);
        }
        
        return salida;
    }
    
    boolean recibe(float cantidad) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = this.modificarSaldo(cantidad);
        }
        
        return salida;
    }
    
    boolean salirCarcelPagando() {
        boolean salida = false;
        
        if(this.encarcelado && this.puedeSalirCarcelPagando()) {
            salida = true;
            this.paga(200);
            this.encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la carcel");
        }
        
        return salida;
    }
    
    boolean salirCarcelTirando() {
        boolean salida = Dado.getInstance().salgoDeLaCarcel();
        
        if(salida){
            this.encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la carcel");
        }
        
        return salida;
    }
    
    boolean tieneAlgoQueGestionar() {
        return this.propiedades.size() > 0;
    }
    
    boolean tieneSalvoconducto() {
        return this.salvoconducto != null;
    }
    
    boolean vender(int ip) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            if (this.existeLaPropiedad(ip) && this.propiedades.get(ip).vender(this)) {
                this.propiedades.remove(ip);
                Diario.getInstance().ocurreEvento("Se ha vendido la propiedad");
                salida = true;
            }
        }
        
        return salida;
    }
    
    public String toString() {
        String salida = "\n *---* Nombre: " + this.nombre + " Casilla actual: " + this.numCasillaActual + " *---* \n\n";
        salida += " *---* Propiedades *---*\n";
        
        if (!this.propiedades.isEmpty()) {
            int count = 1;
            for(TituloPropiedad propiedades: this.propiedades) {
                salida += "      " + count + ". " + propiedades.toString() + "\n";
                count ++;
            }
        } else {
            salida += "      No tiene propiedades\n\n";
        }
        
        
        return salida + "\n";
    }
}
