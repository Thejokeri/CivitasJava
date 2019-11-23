/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;

/**
 *
 * @author thejoker
 */
public class Dado {
    private static final Dado instance = new Dado();
    private static final int SalidaCarcel = 5;
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    
    public static Dado getInstance() {
        return instance;
    }

    private Dado() {
        this.random = new Random();
        this.ultimoResultado = this.random.nextInt(6)+1;
        this.debug = false;
    }
    
    int tirar() {
        int resultado = 1;
        
        if (!this.debug){
            resultado = this.random.nextInt(6)+1;    
        }
        
        this.ultimoResultado = resultado;
        
        return resultado;
    }
    
    boolean salgoDeLaCarcel() {
        return this.tirar() >= 5;
    }
    
    int quienEmpieza(int n) {
        return this.random.nextInt(n);
    }
    
    public void setDebug(boolean d) {
        this.debug = d;
        Diario diario = Diario.getInstance();
        
        if (this.debug)
            diario.ocurreEvento("Activado modo debug");
        else
            diario.ocurreEvento("Desactivado modo debug");
    }
    
    int getUltimoResultado() {
        return this.ultimoResultado;
    }
}
