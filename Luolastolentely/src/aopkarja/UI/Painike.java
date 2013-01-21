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
    
}
