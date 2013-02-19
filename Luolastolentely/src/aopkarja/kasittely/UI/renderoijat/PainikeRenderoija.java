package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Koordinaatti;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.kasittely.UI.Vari;
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
            GL11.glColor3d(0, 1.0, 1.0);
        } else {
            Vari vari = komponentti.getVari();
            GL11.glColor3d(vari.getPunainen(), vari.getVihrea(), vari.getSininen());
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
