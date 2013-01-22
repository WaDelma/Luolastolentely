package aopkarja.UI;

import aopkarja.Komponentti;

/**
 *
 * @author aopkarja
 */
public interface Renderoija {

    public void renderoi(Komponentti komponentti);

    public void initialisoi(Komponentti komponentti);
}
