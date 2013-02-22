package aopkarja.UI.renderoijat;

import aopkarja.UI.Renderoija;
import aopkarja.UI.komponentit.Alus;
import aopkarja.fysiikka.asiat.Alue;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class PelaajaRenderoija extends Renderoija<Alus> {

    @Override
    public void renderoi(Alus alus) {
        Alue alue = alus.getAlue();
        double pieninYmparoivaYmpyra = alue.getPienimmanUlkonaOlevanYmpyranSade();
        double x = alue.getKeskipiste().get(0);
        double y = alue.getKeskipiste().get(1) + pieninYmparoivaYmpyra;
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(x - alus.getEnergia(), y);
            GL11.glVertex2d(x + alus.getEnergia(), y);
            GL11.glVertex2d(x + alus.getEnergia(), y + 5);
            GL11.glVertex2d(x - alus.getEnergia(), y + 5);

        }
        GL11.glEnd();
    }
}
