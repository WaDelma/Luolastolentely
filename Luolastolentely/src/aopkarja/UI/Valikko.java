package aopkarja.UI;

import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class Valikko {

    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        GL11.glColor3f(1.0f, 1.0f, 0.0f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(100, 100);
            GL11.glVertex2f(100 + 200, 100);
            GL11.glVertex2f(100 + 200, 100 + 200);
            GL11.glVertex2f(100, 100 + 200);
        }
        GL11.glEnd();
    }
}
