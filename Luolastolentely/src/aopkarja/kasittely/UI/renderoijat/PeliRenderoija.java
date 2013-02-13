
package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.kasittely.UI.Renderoija;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class PeliRenderoija implements Renderoija{
    @Override
    public void initialisoi(Komponentti komponentti) {
        GL11.glClearColor(0.34f, 0.35f, 0.33f, 1.0f);
    }
    
    @Override
    public void renderoi(Komponentti komponentti) {
        
    }
}
