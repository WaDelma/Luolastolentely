package aopkarja.UI;

import aopkarja.fysiikka.asiat.Koordinaatti;
import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 * Tämän toteuttavat luokat renderöivät jotain.
 *
 * @author aopkarja
 */
public class Renderoija<T extends Komponentti> {
    private Vari moodiVari;

    /**
     *
     * @param komponentti
     */
    public void renderoi(T komponentti) {
        if(komponentti instanceof Moodi && moodiVari != komponentti.getVari()){
            moodiVari = komponentti.getVari();
            GL11.glClearColor((float)moodiVari.getVari(0), (float)moodiVari.getVari(1), (float)moodiVari.getVari(2), 1.0f);
        }
    }

    public void renderoiAlue(Komponentti komponentti) {
        Vari vari = komponentti.getVari();
        GL11.glColor3d(vari.getVari(0), vari.getVari(1), vari.getVari(2));
        List<Koordinaatti> koordinaatit = komponentti.getAlue().getKoordinaatit();
        int n = 1;
        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int i = 0; i < koordinaatit.size(); i++) {
            GL11.glVertex2d(koordinaatit.get(i).get(0), koordinaatit.get(i).get(1));
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
