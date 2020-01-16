/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author thejoker
 */
public class TituloPropiedad {

    private float alquilerBase;
    private static float factorInteresesHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe) {
        this.nombre = nom;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc;
        this.precioEdificar = pe;
        this.hipotecado = false;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.propietario = null;
    }

    void actualizarPropietarioPorConversion(Jugador jugador) {
        this.propietario = jugador;
    }

    boolean cancelarHipoteca(Jugador jugador) {
        boolean result = false;
        
        if (this.hipotecado) {
            if (this.esEsteElPropietario(jugador)) {
                this.propietario.paga(this.getImporteCancelarHipoteca());
                this.hipotecado = false;
                result = true;
            }
        }
        
        return result;
    }

    int cantidadCasasHoteles() {
        return this.numCasas + this.numHoteles;
    }

    boolean comprar(Jugador jugador) {
        boolean result = false;
        
        if (!this.tienePropietario()) {
            this.propietario = jugador;
            result = true;
            this.propietario.paga(this.precioCompra);
        }
        
        return result;
    }

    boolean construirCasa(Jugador jugador) {
        boolean result = false;
        
        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.precioEdificar);
            this.numCasas += 1;
            result = true;
        }
        
        return result;
    }

    boolean construirHotel(Jugador jugador) {
        boolean result = false;
        
        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.precioEdificar);
            this.numHoteles += 1;
            result = true;
        }
        
        return result;
    }

    boolean derruirCasas(int n, Jugador jugador) {
        boolean salida = false;
        
        if (this.numCasas >= n && this.esEsteElPropietario(jugador)) {
            this.numCasas -= n;
            salida = true;
        } 
        
        return salida;
    }

    private boolean esEsteElPropietario(Jugador jugador) {
        return this.propietario == jugador;
    }

    public boolean getHipotecado() {
        return this.hipotecado;
    }

    float getImporteCancelarHipoteca() {
        return this.getImporteHipoteca() * TituloPropiedad.factorInteresesHipoteca;
    }

    private float getImporteHipoteca() {
        return (float) (this.hipotecaBase * (1 + (this.numCasas * 0.5) + (this.numHoteles * 2.5)));
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getNumCasas() {
        return this.numCasas;
    }

    public int getNumHoteles() {
        return this.numHoteles;
    }

    private float getPrecioAlquiler() {
        float valor = (float) 0.0;

        if (!this.hipotecado && !this.propietario.isEncarcelado()) {
            valor = (float) (this.alquilerBase * (1 + (this.numCasas * 0.5) + (this.numHoteles * 2.5)));
        }

        return valor;
    }

    float getPrecioCompra() {
        return this.precioCompra;
    }

    float getPrecioEdificar() {
        return this.precioEdificar;
    }

    private float getPrecioVenta() {
        return (float) this.precioCompra + (this.numCasas + this.numHoteles * 5) * this.precioEdificar * this.factorRevalorizacion;
    }

    Jugador getPropietario() {
        return this.propietario;
    }

    boolean hipotecar(Jugador jugador) {
        boolean salida = false;
        
        if (!this.hipotecado && this.esEsteElPropietario(jugador)) {
            Jugador propietario = jugador;
            propietario.recibe(this.getImporteHipoteca());
            this.hipotecado = true;
            salida = true;
        }
        
        return salida;
    }

    private boolean propietarioEncarcelado() {
        boolean salida = false;
        
        if (this.tienePropietario()) {
            salida = this.propietario.isEncarcelado();
        }
        
        return salida;
    }

    boolean tienePropietario() {
        return this.propietario != null && this.propietario.getPropiedades().indexOf(this) != -1;
    }

    public String toString() {
        return "Nombre: " + this.nombre + "\n"
                + "Alquiler Base: " + this.alquilerBase + "\n"
                + "Factor Revalorizacion: " + this.factorRevalorizacion + "\n"
                + "Hipoteca Base: " + this.hipotecaBase + "\n"
                + "Precio de Compra: " + this.precioCompra + "\n"
                + "Precio de Edificacion: " + this.precioEdificar + "\n"
                + "Hipotecado: " + this.hipotecado + "\n"
                + "No. Casas: " + this.numCasas + "\n"
                + "No. Hoteles: " + this.numHoteles;
    }

    void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && !this.esEsteElPropietario(jugador)) {
            float precio = this.getPrecioAlquiler();

            jugador.pagaAlquiler(precio);
            this.propietario.recibe(precio);
        }
    }

    boolean vender(Jugador jugador) {
        boolean salida = false;
        
        if (!this.hipotecado && this.esEsteElPropietario(jugador)) {
            jugador.recibe(this.getPrecioVenta());
            this.propietario = null;
            this.numCasas = 0;
            this.numHoteles = 0;
            salida = true;
        }
        
        return salida;
    }
}
