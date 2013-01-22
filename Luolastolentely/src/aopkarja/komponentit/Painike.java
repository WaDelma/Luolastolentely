
package aopkarja.komponentit;

import aopkarja.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.tapahtuma.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class Painike extends Komponentti{
    
    public Painike(Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
    }
    
    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
    }
}
