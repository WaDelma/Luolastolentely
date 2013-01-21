
package aopkarja.UI;

import aopkarja.Koordinaatit;

/**
 *
 * @author aopkarja
 */
public class Piste implements Komponentti{
    private Koordinaatit koordinaatit;

    public Piste(Koordinaatit koordinaatit) {
        this.koordinaatit = koordinaatit;
    }

    @Override
    public Koordinaatit getKoordinaatit() {
        return koordinaatit;
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        
    }

    @Override
    public boolean leikkaa(Komponentti komponentti) {
        if(komponentti instanceof Piste){
            return koordinaatit.equals(komponentti.getKoordinaatit());
        }
        return false;
    }

    @Override
    public void lisaaKomponentti(Komponentti komponentti) {
    }

    @Override
    public Komponentti getOmistaja() {
        return null;
    }
    
}
