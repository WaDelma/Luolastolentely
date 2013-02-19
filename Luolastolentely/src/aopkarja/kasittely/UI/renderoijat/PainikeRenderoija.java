package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.komponentit.Painike;
import org.lwjgl.opengl.GL11;

/**
 * Renderöi painikkeen.
 * 
 * @author aopkarja
 */
public class PainikeRenderoija extends Renderoija<Painike> {

    /**
     *
     * @param komponentti
     */
    @Override
    public void renderoi(Painike komponentti) {
        if (komponentti.isPainettu()) {
            GL11.glColor3f(0f, 1.0f, 1.0f);
        } else {
            GL11.glColor3f(0f, 0f, 1.0f);
        }
//        GL11.glBegin(GL11.GL_QUADS);
//        {
//            GL11.glVertex2f(100, 100);
//            GL11.glVertex2f(100 + 200, 100);
//            GL11.glVertex2f(100 + 200, 100 + 200);
//            GL11.glVertex2f(100, 100 + 200);
//        }
//        GL11.glEnd();
        GL11.glBegin(GL11.GL_QUADS);
        {
            for (Koordinaatti koordinaatti : komponentti.getAlue().getKoordinaatit()) {
                //System.out.println(koordinaatit.getX() + " " + koordinaatit.getY());
                double[] koordinaattiTaulukko = koordinaatti.getKoordinaatti();
                GL11.glVertex2d(koordinaattiTaulukko[0], koordinaattiTaulukko[1]);
//                GL11.glVertex2f(100 + 200, 100);
//                GL11.glVertex2f(100 + 200, 100 + 200);
//                GL11.glVertex2f(100, 100 + 200);
            }
        }
        GL11.glEnd();
    }
    
}
