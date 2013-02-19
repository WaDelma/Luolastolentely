package aopkarja.kasittelijat.fysiikka;

import aopkarja.Alue;
import aopkarja.Koordinaatti;

/**
 *
 * @author aopkarja
 */
public class FyysinenKappale {

    private Koordinaatti voima;
    private final Alue alue;
    private double paino;
    private double kitka;
    private boolean poistettu;

    public double getPaino() {
        return paino;
    }

    public Koordinaatti getVoima() {
        return voima;
    }

    public FyysinenKappale(Alue alue) {
        this.alue = alue;
        voima = new Koordinaatti();
    }

    public Alue getAlue() {
        return alue;
    }

    public void setPaino(double paino) {
        this.paino = paino;
    }

    public void lisaaVoimanLahde(Koordinaatti koordinaatti) {
        voima.siirra(koordinaatti);
    }

    boolean isPainoton() {
        return paino == 0;
    }

    double getKitka() {
        return kitka;
    }

    public void setKitka(double kitka) {
        this.kitka = kitka;
    }

    public void poista() {
        poistettu = true;
    }
    
    public boolean isPoistettu(){
        return poistettu;
    }
}
