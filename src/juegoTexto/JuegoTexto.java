/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;
import civitas.Dado;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author thejoker
 */
public class JuegoTexto {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaTextual vista = new VistaTextual();
        ArrayList<String> nombres = new ArrayList<String>();
        nombres.add("Pepe");
        nombres.add("Paco");
        nombres.add("MJ");
        nombres.add("Fernando");
        
        CivitasJuego civitas = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(civitas, vista);
        Dado.getInstance().setDebug(true);
        
        controlador.juega();
    }
}
