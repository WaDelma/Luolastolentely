package aopkarja.kasittely.UI;

import aopkarja.Komponentti;

/**
 * Tämän toteuttavat luokat renderöivät jotain.
 * 
 * @author aopkarja
 */
public interface Renderoija<T extends Komponentti> {

    /**
     *
     * @param komponentti
     */
    public void renderoi(T komponentti);

    /**
     *
     * @param komponentti
     */
    public void initialisoi(T komponentti);
}
