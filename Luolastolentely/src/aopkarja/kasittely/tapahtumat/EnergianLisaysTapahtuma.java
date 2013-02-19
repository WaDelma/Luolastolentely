package aopkarja.kasittely.tapahtumat;

import aopkarja.kasittely.Tapahtuma;

/**
 *
 * @author Antti
 */
public class EnergianLisaysTapahtuma extends Tapahtuma{
    private final double maara;

    public EnergianLisaysTapahtuma(double maara) {
        this.maara = maara;
    }

    @Override
    public Object[] getTieto() {
        return new Object[]{maara};
    }
    
}
