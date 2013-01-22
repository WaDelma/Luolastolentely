package aopkarja;

import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.tapahtuma.TapahtumienKasittelija;
import aopkarja.tapahtuma.tapahtumat.Painallus;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/**
 *
 * @author aopkarja
 */
public class HiirenKasittelija {

    private Thread hiirenSaie;
    private boolean[] painikkeidenTilat;
    public static final int POLLAUKSEN_AIKAVALI = 10;
    private TapahtumienKasittelija kasittelija;

    public HiirenKasittelija(TapahtumienKasittelija kasittelija) {
        this.kasittelija = kasittelija;
    }

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void kaynnnista() throws LWJGLException {
        Mouse.create();
        painikkeidenTilat = new boolean[Mouse.getButtonCount()];
        hiirenSaie = new Thread() {
            @Override
            public void run() {
                while (Luolastolentely.getInstanssi().kaynnissa()) {
                    Mouse.poll();
                    while (Mouse.next()) {
                        int painike = Mouse.getEventButton();
                        if (painike == -1) {
                            continue;
                        }
                        boolean painettu = Mouse.getEventButtonState();
                        if (painettu && !painikkeidenTilat[painike]) {
                            Koordinaatti koordinaatit = new Koordinaatti(Mouse.getEventX(), Mouse.getEventY());
                            kasittelija.lisaa(new Painallus(getPainike(painike), koordinaatit));
                        }
                        painikkeidenTilat[painike] = painettu;
                    }
                    try {
                        Thread.sleep(POLLAUKSEN_AIKAVALI);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        hiirenSaie.start();
    }

    @Kasittelija(KasittelyTyyppi.LOPETA)
    public void lopeta() {
        Mouse.destroy();
    }

    public static int getPainike(int painike) {
        return painike;
    }
}
