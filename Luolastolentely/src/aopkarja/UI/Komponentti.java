package aopkarja.UI;

import aopkarja.Koordinaatit;

/**
 *
 * @author aopkarja
 */
public interface Komponentti {
    public Koordinaatit getKoordinaatit();
    
    public void tapahtuu(Tapahtuma tapahtuma);
    
    public boolean leikkaa(Komponentti komponentti);
    
    public void lisaaKomponentti(Komponentti komponentti);
    
    public Komponentti getOmistaja();
}
