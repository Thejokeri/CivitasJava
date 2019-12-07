/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 * Clase Tablero Responsabilidad de representar el tablero de juego imponiendo
 * las restricciones existentes sobre el mismo en las reglas de juego.
 *
 * @author thejoker
 */
public class Tablero {

    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;

    Tablero(int indice) {
        this.numCasillaCarcel = 1;

        if (indice > 1) {
            this.numCasillaCarcel = indice;
        }

        this.casillas = new ArrayList<Casilla>();
        Casilla e = new Casilla("Salida");
        this.casillas.add(e);

        this.porSalida = 0;
        this.tieneJuez = false;
    }
    
    private boolean correcto() {
        return (this.casillas.size() > this.numCasillaCarcel && this.tieneJuez);
    }

    private boolean correcto(int numCasilla) {
        return (0 <= numCasilla && numCasilla < this.casillas.size() && this.correcto());
    }

    int getCarcel() {
        return numCasillaCarcel;
    }

    int getPorSalida() {
        int valor = this.porSalida;
        
        if(this.porSalida > 0)
            this.porSalida--;
        
        return valor;
    }

    public void aniadeCasilla(Casilla casilla) {
        Casilla carcel = new Casilla("Carcel");
        
        if (this.casillas.size() == this.numCasillaCarcel){
            this.casillas.add(carcel);
        }
        
        this.casillas.add(casilla);
        
        if (this.casillas.size() == this.numCasillaCarcel){
            this.casillas.add(carcel);
        }
    }

    public void aniadeJuez() {
        CasillaJuez juez = new CasillaJuez(this.numCasillaCarcel, "Ve a la carcel");
        
        if (!this.tieneJuez) {
            this.aniadeCasilla(juez);
            this.tieneJuez = true;
        }
    }
    
    Casilla getCasilla(int numCasilla) {
        Casilla salida = null;
        
        if(this.correcto(numCasilla)) {
            salida = this.casillas.get(numCasilla);
        }
        
        return salida;
    }
    
    int nuevaPosicion(int actual, int tirada) {
        int posicion = -1;

        if (this.correcto()) {
            posicion = (actual + tirada) % this.casillas.size();
            if (posicion != (actual + tirada)) {
                this.porSalida++;
            }
        }

        return posicion;
    }
    
    int calcularTirada(int origen, int destino) {
        return (destino - origen) + this.casillas.size();
    }
}
