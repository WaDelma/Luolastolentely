
package aopkarja.UI;

/**
 *
 * @author aopkarja
 */
public class Vari {
    private double[] vari;

    public Vari(double... vari) {
        this.vari = vari.clone();
    }
    
    public Vari(){
        vari = new double[0];
    }

    public double getVari(int n) {
        if(n > vari.length){
            return 0;
        }
        return vari[n];
    }
}
