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
public class Especulador extends Jugador {
    protected static int FactorEspeculador = 2;
    private int fianza;
    private Boolean especulador;
    
    public Especulador(Jugador otro, int fianza) {
        super(otro);
        this.fianza = fianza;
        this.especulador = true;
        
        otro.getPropiedades().forEach((propiedad) -> {
            propiedad.actualizarPropietarioPorConversion(this);
        });
    }
    
    public Boolean isEspeculador(){
        return true;
    }
    
    @Override
    public int getCasaMax() {
        return super.getCasaMax() * Especulador.FactorEspeculador;
    }
    
    @Override
    public int getHotelesMax() {
        return super.getHotelesMax() * Especulador.FactorEspeculador;
    }
    
    private boolean pagarFianza(){
        boolean puedePagar = super.puedoGastar(fianza);
        
        if (puedePagar){
            modificarSaldo(-fianza);
            Diario.getInstance().ocurreEvento("El jugador ha pagado la fianza y se ha librado de la carcel");
        }
        
        return puedePagar;
    }
    
    @Override
    boolean encarcelar(int numCasillaCarcel) {
        Boolean resultado = false;
        
        if(!this.encarcelado){
            if(super.tieneSalvoconducto()) {
                super.perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador "+super.getNombre()+" se libra de la carcel y pierde salvoconducto"); 
            } else if(super.puedoGastar(fianza)){ 
                    modificarSaldo(-fianza);
                    Diario.getInstance().ocurreEvento("El jugador ha pagado la fianza y se ha librado de la carcel");
            } else {
                resultado = true;
            } 
        }
        
        return resultado;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad) {
        boolean salida = false;
        
        if (!this.encarcelado) {
            salida = this.paga(cantidad/2);
            Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + " paga " + Float.toString(cantidad/2) + " de impuesto ");
        }
        
        return salida;
    }
    
    @Override
    public String toString() {
        String salida = "*+++*+++* Jugador Especulador *+++*+++*\n";
        salida += super.toString();
        
        return salida;
    }
}
