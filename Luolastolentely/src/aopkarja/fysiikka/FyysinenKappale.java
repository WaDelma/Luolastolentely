package aopkarja.fysiikka;

import aopkarja.UI.Komponentti;
import aopkarja.fysiikka.asiat.Koordinaatti;

/**
 * Kappale, jolla on fysiikka.
 * @author aopkarja
 */
public class FyysinenKappale {

    private Koordinaatti voima;
//    private Koordinaatti rotaatioVoima;
    private double paino;
    private double kitka;
    private boolean poistettu;
    private final Komponentti komponentti;

    public double getPaino() {
        return paino;
    }

//    public Koordinaatti getRotaatioVoima() {
//        return rotaatioVoima;
//    }

    public Koordinaatti getVoima() {
        return voima;
    }

    public FyysinenKappale(Komponentti komponentti) {
       this.komponentti = komponentti;
        voima = new Koordinaatti();
//        rotaatioVoima = new Koordinaatti();
    }

    public Komponentti getKomponentti() {
        return komponentti;
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
        rakentaja.append(komponentti);
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
//
//    public void lisaaRotaatioVoimanLahde(Koordinaatti koordinaatti) {
//        rotaatioVoima.siirra(koordinaatti);
//    }
}
