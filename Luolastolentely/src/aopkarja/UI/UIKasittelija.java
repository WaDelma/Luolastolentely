package aopkarja.UI;

import aopkarja.Kasittelija;
import aopkarja.KasittelyTyyppi;
import java.awt.Canvas;
import javax.swing.JFrame;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author aopkarja
 */
public class UIKasittelija {

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void initialisoi() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(1.0f, 1.0f, 0.0f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(100, 100);
            GL11.glVertex2f(100 + 200, 100);
            GL11.glVertex2f(100 + 200, 100 + 200);
            GL11.glVertex2f(100, 100 + 200);
        }
        GL11.glEnd();
        Display.update();
        Display.sync(60);
    }

    @Kasittelija(KasittelyTyyppi.LOPETA)
    public void sulje() {
        Display.destroy();
    }
}
