package aopkarja;

import aopkarja.UI.Painallus;
import aopkarja.UI.Tapahtuma;
import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

/**
 *
 * @author aopkarja
 */
public class HiirenKasittelija {

    private Thread hiirenSaie;
    private List<Tapahtuma> tapahtumat;//TODO: Tapahtuman käsittelijä
    private boolean[] painikkeidenTilat;

    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void kaynnnista() throws LWJGLException {
        Mouse.create();
        tapahtumat = new ArrayList<>();
        painikkeidenTilat = new boolean[Mouse.getButtonCount()]; 
        hiirenSaie = new Thread() {
            @Override
            public void run() {
                while (Luolastolentely.getInstanssi().kaynnissa()) {
                    Mouse.poll();
                    while (Mouse.next()) {
                        int painike = Mouse.getEventButton();
                        if(painike == -1){
                            continue;
                        }
                        boolean painettu = Mouse.getEventButtonState();
                        if (painettu && !painikkeidenTilat[painike]) {
                            Koordinaatit koordinaatit = new Koordinaatit(Mouse.getEventX(), Mouse.getEventY());
                            tapahtumat.add(new Painallus(getPainike(painike), koordinaatit));
                        }
                        painikkeidenTilat[painike] = painettu;
                    }
                    try {
                        Thread.sleep(10);
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
