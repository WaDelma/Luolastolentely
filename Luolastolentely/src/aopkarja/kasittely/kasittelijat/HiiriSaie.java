package aopkarja.kasittely.kasittelijat;

import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.tapahtumat.HiiriTapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/**
 *
 * @author aopkarja
 */
public class HiiriSaie extends SisaantuloSaie {

    public HiiriSaie(TapahtumienKasittelija kasittelija) {
        super(kasittelija);
    }

    @Override
    public void initialisoi() throws LWJGLException {
        Mouse.create();
    }

    @Override
    public void looppi() throws LWJGLException {
        Mouse.poll();
        while (Mouse.next()) {
            int painike = Mouse.getEventButton();
            if (painike == -1) {
                continue;
            }
            boolean painettu = Mouse.getEventButtonState();
            Koordinaatti koordinaatit = new Koordinaatti(Mouse.getEventX(), Mouse.getEventY());
            getKasittelija().lisaa(new Painallus(new HiiriTapahtuma(koordinaatit), getPainike(painike), painettu));
        }
    }

    @Override
    public void lopeta() {
        Mouse.destroy();
    }

    /**
     *
     * @param painike
     * @return Painikkeen muuttumaton koodi muuttuvasta
     */
    public static int getPainike(int painike) {
        return painike;
    }
}
