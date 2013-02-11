
package aopkarja.kasittely.tapahtumat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 * {@link Tapahtuma}, jonka hiiri luo kun sen painikkeesta päästetään irti
 * 
 * @author Antti
 */
public class PainalluksenPaasto extends HiiriTapahtuma {
    public PainalluksenPaasto(int painike, Koordinaatti koordinaatti) {
        super(painike, koordinaatti);
    }
}
