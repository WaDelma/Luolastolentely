
package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.kasittely.UI.Renderoija;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class PelaajaRenderoija implements Renderoija{

    @Override
    public void renderoi(Komponentti komponentti) {
        GL11.glColor3f(0.5f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            for (Koordinaatti koordinaatti : komponentti.getAlue().getKoordinaatit()) {
                double[] koordinaattiTaulukko = koordinaatti.getKoordinaatti();
                GL11.glVertex2d(koordinaattiTaulukko[0], koordinaattiTaulukko[1]);
            }
        }
        GL11.glEnd();
    }

    @Override
    public void initialisoi(Komponentti komponentti) {
        
    }
    
}
