package aopkarja.UI;

import aopkarja.Komponentti;
import aopkarja.Luolastolentely;
import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;
import aopkarja.kasitttely.Prioriteetti;
import aopkarja.tapahtuma.Tapahtuma;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
@Prioriteetti(-100)
public class UIKasittelija {

    private KasittelynHoitaja<Komponentti> moodit;
    private Komponentti moodi;
    private Komponentti edellinenMoodi;
    private List<Tapahtuma> tapahtumat;

    public UIKasittelija(Komponentti moodi) {
        this.moodit = new KasittelynHoitaja();
        this.moodi = moodi;
        moodit.lisaa(moodi);
        tapahtumat = new ArrayList<>();
    }

    public UIKasittelija setMoodi(Komponentti moodi) {
        if (!moodit.lisatty(moodi)) {
            moodit.lisaa(moodi);
        }
        this.moodi = moodi;
        return this;
    }

    @Prioriteetti(100)
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
        if (moodi != edellinenMoodi) {
            moodit.kasittele(KasittelyTyyppi.LOPETA, edellinenMoodi);
            edellinenMoodi = moodi;
            moodit.kasittele(KasittelyTyyppi.KAYNNISTA, moodi);
        }
        for (Tapahtuma tapahtuma : tapahtumat) {
            moodi.tapahtuu(tapahtuma);
        }
        tapahtumat.clear();
        moodit.kasittele(KasittelyTyyppi.AJA, moodi);
//        GL11.glColor3f(1.0f, 1.0f, 0.0f);
//        GL11.glBegin(GL11.GL_QUADS);
//        {
//            GL11.glVertex2f(100, 100);
//            GL11.glVertex2f(100 + 200, 100);
//            GL11.glVertex2f(100 + 200, 100 + 200);
//            GL11.glVertex2f(100, 100 + 200);
//        }
//        GL11.glEnd();
        Display.update();
        Display.sync(60);
        if (Display.isCloseRequested()) {
            Luolastolentely.getInstanssi().lopetaPeli();
        }
    }

    @Kasittelija(KasittelyTyyppi.LOPETA)
    public void sulje() {
        Display.destroy();
    }

    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        tapahtumat.add(tapahtuma);
    }
}
