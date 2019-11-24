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
import java.util.Scanner;

/**
 *
 * @author thejoker
 */
public class JuegoTexto {
    private static Scanner in = new Scanner (System.in);
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaTextual vista = new VistaTextual();
        ArrayList<String> nombres = new ArrayList<String>();
        System.out.println("*-*-*-*-*-*-*-*-*Bienvenido al juego*-*-*-*-*-*-*-*-*");
        System.out.println("   _____ _       _ _            \n"       +
                        "  / ____(_)     (_) |           \n"        +
                        " | |     ___   ___| |_ __ _ ___ \n"        +
                        " | |    | \\ \\ / / | __/ _` / __|\n"      +
                        " | |____| |\\ V /| | || (_| \\__ \\\n"     +
                        "  \\_____|_| \\_/ |_|\\__\\__,_|___/\n"    +
                        "                                \n"        +
                        "                                "); 
        
        System.out.println("Empezemos, en primer lugar introduce los jugadores");
        
        for (int i = 1; i <= 4; i++){
            System.out.print("Ingrese el nombre del Jugador " + i + ": ");  
            String nombre = in.nextLine();  
            nombres.add(nombre);
        }
        
        CivitasJuego civitas = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(civitas, vista);
        Dado.getInstance().setDebug(true);
        
        controlador.juega();
    }
}
