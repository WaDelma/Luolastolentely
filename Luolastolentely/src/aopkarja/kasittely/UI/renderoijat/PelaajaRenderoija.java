package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Alue;
import aopkarja.kasittely.UI.Renderoija;
import aopkarja.komponentit.Alus;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class PelaajaRenderoija extends Renderoija<Alus> {

    @Override
    public void renderoi(Alus alus) {
        Alue alue = alus.getAlue();
//        List<Koordinaatti> koordinaatit = alue.getKoordinaatit();
//        GL11.glColor3f(0.5f, 1.0f, 1.0f);

//        GL11.glBegin(GL11.GL_QUADS);
//        {
//            for (Koordinaatti koordinaatti : koordinaatit) {
//                double[] koordinaattiTaulukko = koordinaatti.getKoordinaatti();
//                GL11.glVertex2d(koordinaattiTaulukko[0], koordinaattiTaulukko[1]);
//            }
//        }
//        GL11.glEnd();

        double[] keskipiste = alue.getKeskipiste().getKoordinaatti();
        double pieninYmparoivaYmpyra = alue.getPienimmanUlkonaOlevanYmpyranSade();
        double x = keskipiste[0];
        double y = keskipiste[1] + pieninYmparoivaYmpyra;
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
