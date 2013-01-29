package aopkarja.UI;

import aopkarja.Komponentti;

/**
 *
 * @author aopkarja
 */
public interface Renderoija<T extends Komponentti> {

    public void renderoi(T komponentti);

    public void initialisoi(T komponentti);
}
