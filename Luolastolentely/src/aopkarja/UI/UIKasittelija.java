package aopkarja.UI;

import aopkarja.Luolastolentely;
import aopkarja.hoitajat.KirjanpidonHoitaja;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.KasittelynHoitaja;
import aopkarja.kasittely.Prioriteetti;
import aopkarja.kasittely.Tapahtuma;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Käsittelee UI puolta.
 *
 * @author aopkarja
 */
@Prioriteetti(-100)
public class UIKasittelija {

    private KasittelynHoitaja<Komponentti> moodit;
    private volatile Moodi uusiMoodi;
    private volatile Moodi moodi;

    /**
     *
     * @param moodi
     */
    public UIKasittelija(Moodi moodi) {
        this.moodit = new KasittelynHoitaja();
        this.uusiMoodi = moodi;
        moodit.lisaa(moodi);
    }

    /**
     * Asettaa moodin missä näyttö on.
     *
     * @param moodi
     * @return this
     */
    public UIKasittelija setMoodi(Moodi moodi) {
        this.uusiMoodi = moodi;
        return this;
    }

    /**
     *
     * @throws LWJGLException
     */
    @Prioriteetti(100)
    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    private void initialisoi() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.setResizable(false);
        Display.create();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     *
     */
    @Kasittelija(KasittelyTyyppi.AJA)
    private void aja() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        if (uusiMoodi != moodi) {
            KirjanpidonHoitaja.ilmoita("Mode changed to " + uusiMoodi);
            moodit.kasittele(KasittelyTyyppi.LOPETA, moodi);
            moodit.poista(moodi);
            moodi = uusiMoodi;
            if (!moodit.lisatty(moodi)) {
                moodit.lisaa(moodi);
            }
            moodit.kasittele(KasittelyTyyppi.KAYNNISTA, moodi);
        }
        moodit.kasittele(KasittelyTyyppi.AJA, moodi);
        moodit.kasittele(KasittelyTyyppi.RENDEROI, moodi);

        Display.update();
        Display.sync(60);
        if (Display.isCloseRequested()) {
            Luolastolentely.getInstanssi().lopetaPeli();
        } else if (Display.wasResized()) {
            //TODO: Add resizing functionality
//            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
//            GL11.glMatrixMode(GL11.GL_PROJECTION);
//            GL11.glLoadIdentity();
//            GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 0, 0);
//            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }
    }

    /**
     *
     */
    @Kasittelija(KasittelyTyyppi.LOPETA)
    private void sulje() {
        Display.destroy();
    }

    /**
     *
     * @param tapahtuma
     */
    @Kasittelija(KasittelyTyyppi.TAPAHTUMA)
    private void tee(Tapahtuma tapahtuma) {
        uusiMoodi.tapahtuu(tapahtuma);
    }

    public Komponentti getMoodi() {
        return uusiMoodi;
    }
}
