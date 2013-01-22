package aopkarja.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.UI.Renderoija;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class ValikkoRenderoija implements Renderoija {
    @Override
    public void initialisoi(Komponentti komponentti) {
        GL11.glClearColor(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    @Override
    public void renderoi(Komponentti komponentti) {
        
    }
}
