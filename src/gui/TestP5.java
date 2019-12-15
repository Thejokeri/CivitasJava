/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author thejoker
 */
public class TestP5 {
    
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        CapturaNombres cn = new CapturaNombres(vista, true);
        
        Dado.createInstance(vista);
        
        Dado dado = Dado.getInstance();
        dado.setDebug(true);

        System.out.println("Nombre " + cn.getNombres().get(0));
        CivitasJuego civitas = new CivitasJuego(cn.getNombres());
        Controlador controlador = new Controlador(civitas, vista);
        vista.setCivitasJuego(civitas);
    }
}
