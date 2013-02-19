package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.kasittely.UI.Renderoija;
import org.lwjgl.opengl.GL11;

/**
 * Renderöi valikon
 * 
 * @author aopkarja
 */
public class ValikkoRenderoija extends Renderoija {

    /**
     *
     * @param komponentti
     */
    @Override
    public void initialisoi(Komponentti komponentti) {
        GL11.glClearColor(1.0f, 0.5f, 0.0f, 1.0f);
    }
}
