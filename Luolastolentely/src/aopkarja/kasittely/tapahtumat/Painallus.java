package aopkarja.kasittely.tapahtumat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.Tapahtuma;

/**
 * {@link Tapahtuma}, jonka hiiri luo kun sen painiketta painetaan
 * 
 * @author Antti
 */
public class Painallus extends HiiriTapahtuma {
    public Painallus(int painike, Koordinaatti koordinaatti) {
        super(painike, koordinaatti);
    }
}
