/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.Casilla;
import civitas.CasillaCalle;
import civitas.CasillaImpuesto;
import civitas.CasillaJuez;
import civitas.CasillaSorpresa;

/**
 *
 * @author thejoker
 */
public class CasillaPanel extends javax.swing.JPanel {
    Casilla casilla;
        
    /**
     * Creates new form CasillaPanel
     */
    public CasillaPanel() {
        initComponents();
    }

    void setCasilla(Casilla casilla) {
        this.removeAll();
        
        if (casilla instanceof CasillaCalle) {
            nombreTextField.setText(casilla.getNombre());
        } else if (casilla instanceof CasillaImpuesto) {
            nombreTextField.setText(casilla.getNombre());
        } else if (casilla instanceof CasillaJuez) {
            nombreTextField.setText(casilla.getNombre());
        } else if (casilla instanceof CasillaSorpresa) {
            nombreTextField.setText(casilla.getNombre());
        } else {
            nombreTextField.setText(casilla.getNombre());
        }
        
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        importeTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Casilla Actual"));

        jLabel1.setText("Nombre:");
        jLabel1.setToolTipText("nombre");
        add(jLabel1);

        nombreTextField.setToolTipText("nombre");
        nombreTextField.setEnabled(false);
        nombreTextField.setPreferredSize(new java.awt.Dimension(90, 26));
        add(nombreTextField);

        jLabel2.setText("Importe:");
        jLabel2.setToolTipText("importe");
        add(jLabel2);

        importeTextField.setToolTipText("importe");
        importeTextField.setEnabled(false);
        importeTextField.setPreferredSize(new java.awt.Dimension(90, 26));
        add(importeTextField);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField importeTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nombreTextField;
    // End of variables declaration//GEN-END:variables
}
