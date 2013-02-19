package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.kasittely.UI.Renderoija;
import org.lwjgl.opengl.GL11;

/**
 * Renderöi valikon
 *
 * @author aopkarja
 */
public class PelinLoppuRenderoija extends Renderoija {

    /**
     *
     * @param komponentti
     */
    @Override
    public void initialisoi(Komponentti komponentti) {
        GL11.glClearColor(0.2f, 0.2f, 0.4f, 1.0f);
    }
}
