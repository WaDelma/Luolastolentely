package aopkarja.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import aopkarja.UI.Renderoija;
import aopkarja.komponentit.Painike;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class PainikeRenderoija implements Renderoija<Painike> {

    @Override
    public void renderoi(Painike komponentti) {
        if (komponentti.painettu) {
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
            for (Koordinaatti koordinaatit : komponentti.getAlue().getKoordinaatit()) {
                //System.out.println(koordinaatit.getX() + " " + koordinaatit.getY());
                GL11.glVertex2f(koordinaatit.getX(), koordinaatit.getY());
//                GL11.glVertex2f(100 + 200, 100);
//                GL11.glVertex2f(100 + 200, 100 + 200);
//                GL11.glVertex2f(100, 100 + 200);
            }
        }
        GL11.glEnd();
    }

    @Override
    public void initialisoi(Painike komponentti) {
    }
}
