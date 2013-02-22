package aopkarja.fysiikka;

import aopkarja.fysiikka.asiat.Alue;
import aopkarja.fysiikka.asiat.Koordinaatti;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class LeikkaustenHoitajaTest {

    private List<Koordinaatti> kolmio;
    private List<Koordinaatti> sisallaOlevaKuvio;
    private List<Koordinaatti> hipovaKuvioBC;
    private List<Koordinaatti> hipovaKuvioAC;
    private List<Koordinaatti> hipovaKuvioAB;

    public LeikkaustenHoitajaTest() {
    }

    @Before
    public void setUp() {
        hipovaKuvioAB = new ArrayList<>();
        hipovaKuvioAB.add(new Koordinaatti(0, 0, 0));
        hipovaKuvioAB.add(new Koordinaatti(1, 0, 0));
        hipovaKuvioAB.add(new Koordinaatti(-1, -1, 0));

        hipovaKuvioAC = new ArrayList<>();
        hipovaKuvioAC.add(new Koordinaatti(0, 0, 0));
        hipovaKuvioAC.add(new Koordinaatti(1, 1, 0));
        hipovaKuvioAC.add(new Koordinaatti(0, 1, 0));

        hipovaKuvioBC = new ArrayList<>();
        hipovaKuvioBC.add(new Koordinaatti(1, 0, 0));
        hipovaKuvioBC.add(new Koordinaatti(1, 1, 0));
        hipovaKuvioBC.add(new Koordinaatti(0, 2, 0));

        sisallaOlevaKuvio = new ArrayList<>();
        sisallaOlevaKuvio.add(new Koordinaatti(0.1, 0.1, 0));
        sisallaOlevaKuvio.add(new Koordinaatti(0.1, 0.1, 0));
        sisallaOlevaKuvio.add(new Koordinaatti(0.1, 0.1, 0));

        kolmio = new ArrayList<>();
        kolmio.add(new Koordinaatti(0, 0, 0));
        kolmio.add(new Koordinaatti(1, 0, 0));
        kolmio.add(new Koordinaatti(1, 1, 0));
    }

    @Test
    public void kolmioPisteTestiTehdaanOikeasti() {
        assertFalse(LeikkaustenHoitaja.pisteKolmioSisalla(new Koordinaatti(0, 1, 0), kolmio));
    }

    @Test
    public void toimiikoSisallaOlevaPisteKolmioPisteTestissa() {
        assertTrue("A", LeikkaustenHoitaja.pisteKolmioSisalla(new Koordinaatti(0.5, 0.2, 0), kolmio));
    }

    @Test
    public void toimiikoKolmionKulmienOloKolmionSisalla() {
        assertTrue("A", LeikkaustenHoitaja.pisteKolmioSisalla(kolmio.get(0), kolmio));
        assertTrue("B", LeikkaustenHoitaja.pisteKolmioSisalla(kolmio.get(1), kolmio));
        assertTrue("C", LeikkaustenHoitaja.pisteKolmioSisalla(kolmio.get(2), kolmio));
    }

    @Test
    public void toimiikoKolmioPisteTestiKaikillaSivuilla() {
        assertTrue("AB", LeikkaustenHoitaja.pisteKolmioSisalla(new Koordinaatti(0.5, 0.0, 0), kolmio));
        assertTrue("AC", LeikkaustenHoitaja.pisteKolmioSisalla(new Koordinaatti(0.5, 0.5, 0), kolmio));
        assertTrue("BC", LeikkaustenHoitaja.pisteKolmioSisalla(new Koordinaatti(1.0, 0.5, 0), kolmio));
    }

    @Test
    public void toimiikoKolmioAlueHipovallaKuviolla() {
        assertTrue("AB", LeikkaustenHoitaja.testaaKolmioJaKuvio(kolmio, hipovaKuvioAB));
        assertTrue("AC", LeikkaustenHoitaja.testaaKolmioJaKuvio(kolmio, hipovaKuvioAC));
        assertTrue("BC", LeikkaustenHoitaja.testaaKolmioJaKuvio(kolmio, hipovaKuvioBC));
    }

    @Test
    public void toimiikoKolmioAlueSisallaOlevallaKuviolla() {
        assertTrue(LeikkaustenHoitaja.testaaKolmioJaKuvio(kolmio, sisallaOlevaKuvio));
    }

    @Test
    public void toimiikoKolmioAlueKolmiollaItsellaan() {
        assertTrue(LeikkaustenHoitaja.testaaKolmioJaKuvio(kolmio, kolmio));
    }

    @Test
    public void toimiikoMonimutkaisemmatKuviot() {
        hipovaKuvioAB.addAll(kolmio);
        assertTrue("AB+kolmio, kolmio", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAB), new Alue(kolmio)));
        assertTrue("kolmio, AB+kolmio", LeikkaustenHoitaja.leikkaako(new Alue(kolmio), new Alue(hipovaKuvioAB)));
    }

    @Test
    public void toimiikoMonimutkaisempiKuvioKaikistaKulmista() {
        hipovaKuvioAC.addAll(kolmio);
        assertTrue("0, 0", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0, 0))));
        assertTrue("1, 0", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(1, 0))));
        assertTrue("1, 1", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(1, 1))));
        assertTrue("0, 1", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0, 1))));
    }

    @Test
    public void toimiikoMonimutkaisempiKuvioKaikistaKulmistaMyosToistepain() {
        hipovaKuvioAC.addAll(kolmio);
        assertTrue("0, 0", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0, 0)), new Alue(hipovaKuvioAC)));
        assertTrue("1, 0", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(1, 0)), new Alue(hipovaKuvioAC)));
        assertTrue("1, 1", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(1, 1)), new Alue(hipovaKuvioAC)));
        assertTrue("0, 1", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0, 1)), new Alue(hipovaKuvioAC)));
    }

    @Test
    public void toimiikoMonimutkaisempiKuvioKaikiltaSivuilta() {
        hipovaKuvioAC.addAll(kolmio);
        assertTrue("0.5, 0", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0.5, 0))));
        assertTrue("0, 0.5", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0, 0.5))));
        assertTrue("0.5, 0.5", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0.5, 0.5))));
        assertTrue("0.5, 1", LeikkaustenHoitaja.leikkaako(new Alue(hipovaKuvioAC), new Alue(new Koordinaatti(0.5, 1))));
    }

    @Test
    public void toimiikoMonimutkaisempiKuvioKaikiltaSivuiltaMyosToistepain() {
        hipovaKuvioAC.addAll(kolmio);
        assertTrue("0.5, 0", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0.5, 0)), new Alue(hipovaKuvioAC)));
        assertTrue("0, 0.5", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0, 0.5)), new Alue(hipovaKuvioAC)));
        assertTrue("0.5, 0.5", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0.5, 0.5)), new Alue(hipovaKuvioAC)));
        assertTrue("0.5, 1", LeikkaustenHoitaja.leikkaako(new Alue(new Koordinaatti(0.5, 1)), new Alue(hipovaKuvioAC)));
    }
}
