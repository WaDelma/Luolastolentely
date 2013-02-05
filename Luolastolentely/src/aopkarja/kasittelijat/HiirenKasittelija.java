package aopkarja.kasittelijat;

import aopkarja.Koordinaatti;
import aopkarja.Luolastolentely;
import aopkarja.kasittely.Kasittelija;
import aopkarja.kasittely.KasittelyTyyppi;
import aopkarja.kasittely.tapahtumat.Painallus;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/**
 * Lukee ja lähettää hiiren aiheuttamia {@link aopkarja.kasittely.Tapahtuma} olioita.
 * 
 * @author aopkarja
 */
public class HiirenKasittelija {

    private Thread hiirenSaie;
    private boolean[] painikkeidenTilat;
    /**
     *
     */
    public static final int POLLAUKSEN_AIKAVALI = 10;
    private TapahtumienKasittelija kasittelija;

    public HiirenKasittelija(TapahtumienKasittelija kasittelija) {
        this.kasittelija = kasittelija;
    }

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    private void kaynnnista() throws LWJGLException {
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

    /**
     *
     */
    @Kasittelija(KasittelyTyyppi.LOPETA)
    private void lopeta() {
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
