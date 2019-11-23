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
public class OperacionInmobiliaria {
    private int numPropiedad;
    private GestionesInmobiliarias gestion;
    
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip) {
        this.gestion = gest;
        this.numPropiedad = ip;
    }
    
    public GestionesInmobiliarias getGestion() {
        return this.gestion;
    }
    
    public int getNumPropiedad() {
        return this.numPropiedad;
    }
}
