package aopkarja.kasittely.UI.renderoijat;

import aopkarja.kasittely.UI.Renderoija;
import aopkarja.komponentit.Kivi;

/**
 *
 * @author Antti
 */
public class KiviRenderoija extends Renderoija<Kivi> {

    @Override
    public void renderoi(Kivi komponentti) {
//        GL11.glColor3f(0.5f, 0.4f, 0.5f);
//        int x = 1;
//        GL11.glBegin(GL11.GL_TRIANGLES);
//        List<Koordinaatti> koordinaatit = komponentti.getAlue().getKoordinaatit();
//        for (int i = 0; i < koordinaatit.size(); i++) {
//            double[] koordinaattiTaulukko = koordinaatit.get(i).getKoordinaatti();
//            GL11.glVertex2d(koordinaattiTaulukko[0], koordinaattiTaulukko[1]);
//            if (x % 3 == 0) {
//                GL11.glEnd();
//                GL11.glBegin(GL11.GL_TRIANGLES);
//            }
//            x++;
//        }
//        GL11.glEnd();
    }
}
