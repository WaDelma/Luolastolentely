package aopkarja.UI;

import aopkarja.Koordinaatit;

/**
 *
 * @author aopkarja
 */
public class Painike implements Komponentti{
    private Koordinaatit koordinaatit;

    @Override
    public Koordinaatit getKoordinaatit() {
        return koordinaatit;
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if(tapahtuma instanceof Painallus){
            
        }
    }

    @Override
    public boolean leikkaa(Komponentti komponentti) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void lisaaKomponentti(Komponentti komponentti) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Komponentti getOmistaja() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
