/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author thejoker
 */
public class MazoSorpresa {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
   
    private void init() {
        this.sorpresas = new ArrayList<Sorpresa>();
        this.cartasEspeciales = new ArrayList<Sorpresa>();
        this.barajada = false;
        this.usadas = 0;
    }
    
    MazoSorpresa() {
        this.debug = false;
        this.init();
    }
    
    MazoSorpresa(boolean debug) {
        this.debug = debug;
        this.init();
        
        Diario diario = Diario.getInstance();
        
        if (this.debug) {
            diario.ocurreEvento("Activado modo debug");
        } else {
            diario.ocurreEvento("Desactivado modo debug");
        }
    }
    
    Sorpresa getUltimaSorpresa() {
        return this.ultimaSorpresa;
    }
    
    void alMazo(Sorpresa s) {
        if(!this.barajada) {
            this.sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente(){
        Sorpresa s = null;
        
        if((!this.barajada || this.usadas == this.sorpresas.size()) && !this.debug) {
            Collections.shuffle(this.sorpresas);
            this.usadas = 0;
            this.barajada = true;
        }
        
        this.usadas++;
        s = this.sorpresas.remove(0);
        this.sorpresas.add(s);
        this.ultimaSorpresa = s;
        
        return s;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa) {
        Diario diario = Diario.getInstance();
        
        if(this.sorpresas.remove(sorpresa)) {
            this.cartasEspeciales.add(sorpresa);
        }
        
        if(!this.sorpresas.contains(sorpresa) && this.cartasEspeciales.contains(sorpresa)){
            diario.ocurreEvento("Carta sorpresa inhabilitada: " + sorpresa);
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa) {
        Diario diario = Diario.getInstance();
        
        if(this.cartasEspeciales.remove(sorpresa)) {
            this.sorpresas.add(sorpresa);   
        }
        
        if(!this.cartasEspeciales.contains(sorpresa) && this.sorpresas.contains(sorpresa)){
            diario.ocurreEvento("Carta sorpresa habilitada: " + sorpresa);
        }
    }
}
