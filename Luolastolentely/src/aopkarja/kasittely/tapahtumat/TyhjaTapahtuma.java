

package aopkarja.kasittely.tapahtumat;

import aopkarja.kasittely.Tapahtuma;


/**
 * {@link Tapahtuma}, joka ei tee mitään
 * 
 * @author aopkarja
 */
public class TyhjaTapahtuma extends Tapahtuma{
    private static final Object[] tyhjaData = new Object[0];

    @Override 
    public Object[] getTieto() {
        return tyhjaData;
    }
    
}
