package aopkarja.kasittelijat.fysiikka;

import aopkarja.Alue;
import aopkarja.Koordinaatti;

/**
 *
 * @author aopkarja
 */
public class FyysinenKappale {

    private Koordinaatti voima;
    private Koordinaatti rotaatioVoima;
    private final Alue alue;
    private double paino;
    private double kitka;
    private boolean poistettu;

    public double getPaino() {
        return paino;
    }

    public Koordinaatti getRotaatioVoima() {
        return rotaatioVoima;
    }

    public Koordinaatti getVoima() {
        return voima;
    }

    public FyysinenKappale(Alue alue) {
        this.alue = alue;
        voima = new Koordinaatti();
        rotaatioVoima = new Koordinaatti();
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

    @Override
    public String toString() {
        StringBuilder rakentaja = new StringBuilder();
        rakentaja.append(poistettu).append(": ");
        rakentaja.append(voima).append(' ');
        rakentaja.append(paino).append(' ');
        rakentaja.append(kitka).append(' ');
        rakentaja.append(alue);
        return rakentaja.toString();
    }

    public void setKitka(double kitka) {
        this.kitka = kitka;
    }

    public void poista() {
        poistettu = true;
    }

    public boolean isPoistettu() {
        return poistettu;
    }
}
