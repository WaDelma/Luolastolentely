/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aopkarja.fysiikka;

import aopkarja.Koordinaatti;
import aopkarja.kasittelijat.fysiikka.LeikkaustenHoitaja;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void toimiikoSisallaOlevaPisteKolmioPisteTestissa(){
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
}
