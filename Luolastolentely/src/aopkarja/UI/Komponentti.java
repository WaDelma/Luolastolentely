package aopkarja.UI;

import aopkarja.Koordinaatit;

/**
 *
 * @author aopkarja
 */
public interface Komponentti {
    public Koordinaatit getKoordinaatit();
    
    public void tapahtuu(Tapahtuma tapahtuma);
}
