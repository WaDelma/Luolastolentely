package aopkarja.kasittely.UI.renderoijat;

import aopkarja.alueet.Ympyra;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.komponentit.EnergiaPallo;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Antti
 */
public class EnergiaPalloRenderoija extends Renderoija<EnergiaPallo> {

    @Override
    public void renderoi(EnergiaPallo komponentti) {
//        GL11.glColor3f(0.0f, 0.0f, 1.0f);
//
//        double[] keskipiste = komponentti.getAlue().getKeskipiste().getKoordinaatti();
//        double sade = komponentti.getAlue().getPienimmanUlkonaOlevanYmpyranSade();
//        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
//        {
//            GL11.glVertex2d(keskipiste[0], keskipiste[1]);
//            for (int i = 0; i <= Ympyra.TARKKUUS + 1; i++) {
//                double angle = Math.PI * 2 * i / 10;//SUBDIVISIONS;
//                GL11.glVertex2d(keskipiste[0] + Math.cos(angle) * sade, keskipiste[1] + Math.sin(angle) * sade);
//            }
//        }
//        GL11.glEnd();
    }
}
