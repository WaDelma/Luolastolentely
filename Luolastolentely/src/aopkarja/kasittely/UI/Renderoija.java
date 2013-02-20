package aopkarja.kasittely.UI;

import aopkarja.Komponentti;
import aopkarja.Koordinaatti;
import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 * Tämän toteuttavat luokat renderöivät jotain.
 *
 * @author aopkarja
 */
public abstract class Renderoija<T extends Komponentti> {

    /**
     *
     * @param komponentti
     */
    public void renderoi(T komponentti) {
    }

    public void renderoiAlue(Komponentti komponentti) {
        Vari vari = komponentti.getVari();
        GL11.glColor3d(vari.getPunainen(), vari.getVihrea(), vari.getSininen());
        List<Koordinaatti> koordinaatit = komponentti.getAlue().getKoordinaatit();
        int n = 1;
        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int i = 0; i < koordinaatit.size(); i++) {
            double[] koordinaattiTaulukko = koordinaatit.get(i).getKoordinaatti();
            GL11.glVertex2d(koordinaattiTaulukko[0], koordinaattiTaulukko[1]);
            if (n % 3 == 0) {
                GL11.glEnd();
                GL11.glBegin(GL11.GL_TRIANGLES);
            }
            n++;
        }
        GL11.glEnd();
    }

    /**
     *
     * @param komponentti
     */
    public void initialisoi(T komponentti) {
    }

    @Override
    public String toString() {
        String name = this.getClass().getName();
        if (name == null) {
            return super.toString();
        }
        String[] split = name.split("\\.");
        return split[split.length - 1];
    }
}
