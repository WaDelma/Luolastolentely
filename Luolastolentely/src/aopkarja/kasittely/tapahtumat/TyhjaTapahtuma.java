

package aopkarja.kasittely.tapahtumat;

import aopkarja.kasitttely.Tapahtuma;

/**
 *
 * @author aopkarja
 */
public class TyhjaTapahtuma implements Tapahtuma{
    private static final Object[] tyhjaData = new Object[0];

    @Override
    public Object[] getTieto() {
        return tyhjaData;
    }
    
}
