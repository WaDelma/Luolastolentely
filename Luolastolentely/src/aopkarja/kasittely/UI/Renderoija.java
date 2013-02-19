package aopkarja.kasittely.UI;

import aopkarja.Komponentti;

/**
 * Tämän toteuttavat luokat renderöivät jotain.
 *
 * @author aopkarja
 */
public abstract class Renderoija<T extends Komponentti> {

    /**
     *
     * @param komponentti
     */
    public void renderoi(T komponentti) {
    }

    /**
     *
     * @param komponentti
     */
    public void initialisoi(T komponentti) {
    }

    @Override
    public String toString() {
        String name = this.getClass().getName();
        if (name == null) {
            return super.toString();
        }
        String[] split = name.split("\\.");
        return split[split.length - 1];
    }
}
