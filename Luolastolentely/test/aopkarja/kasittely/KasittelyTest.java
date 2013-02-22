package aopkarja.kasittely;


import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class KasittelyTest {

    private KasittelynHoitaja hoitaja;

    @Before
    public void saato() {
        hoitaja = new KasittelynHoitaja();
        tehdyt = new ArrayList<>();
    }

    @Test
    public void kasittelynHoitajanLuonti() {
        assertNotNull(hoitaja.getKasittelijat());
    }

    @Test
    public void lisaaminen() {
        Object o = new Object();
        hoitaja.lisaa(o);
        assertTrue(hoitaja.lisatty(o));
    }

    @Test
    public void kasittely() {
        Object o = new TestiLuokka();
        hoitaja.lisaa(o);
        hoitaja.kasittele(KasittelyTyyppi.KAYNNISTA, o);
        String arvo = "TestiLuokkaKAYNNISTA1";
        assertEquals(arvo, tehdyt.get(0));
    }

    @Test
    public void kasittelyUseampaanKertaan() {
        Object o = new TestiLuokka();
        hoitaja.lisaa(o);
        hoitaja.kasittele(KasittelyTyyppi.KAYNNISTA, o);
        String arvo = "TestiLuokkaKAYNNISTA2";
        assertEquals(arvo, tehdyt.get(1));
    }

    @Test
    public void kasitteleKaikki() {
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.kasittele(KasittelyTyyppi.AJA);
        String arvo = "TestiLuokkaAJA1";
        tehdyt.remove(arvo);
        assertEquals(arvo, tehdyt.get(0));
    }

    @Test
    public void kasitteleJarjestyksessa() {
        hoitaja.lisaa(new EpaPriorisoituTestiLuokka());
        hoitaja.lisaa(new PriorisoituTestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.kasittele(KasittelyTyyppi.LOPETA);
        assertEquals("PriorisoituTestiLuokkaLOPETA1", tehdyt.get(0));
        assertEquals("TestiLuokkaLOPETA1", tehdyt.get(1));
        assertEquals("EpaPriorisoituTestiLuokkaLOPETA1", tehdyt.get(2));
    }

    @Test
    public void kasitteleMetodiJarjestyksessa() {
        hoitaja.lisaa(new EpaPriorisoituTestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.kasittele(KasittelyTyyppi.AJA);
        assertEquals("EpaPriorisoituTestiLuokkaAJA1", tehdyt.get(0));
        assertEquals("TestiLuokkaAJA1", tehdyt.get(1));
    }

    @Test
    public void kasitteleMetodiJarjestyksessa2() {
        hoitaja.lisaa(new PriorisoituTestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.kasittele(KasittelyTyyppi.KAYNNISTA);
        assertEquals("PriorisoituTestiLuokkaKAYNNISTA1", tehdyt.get(0));
        assertEquals("TestiLuokkaKAYNNISTA1", tehdyt.get(1));
        assertEquals("PriorisoituTestiLuokkaKAYNNISTA2", tehdyt.get(2));
        assertEquals("TestiLuokkaKAYNNISTA2", tehdyt.get(3));
    }

    @Test
    public void tyhjennysToimii() {
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.tyhjenna();
        assertEquals(0, hoitaja.getKasittelijat().size());
    }

    @Test
    public void tietyntyyppisetKasittelijat() {
        hoitaja.lisaa(new PriorisoituTestiLuokka());
        hoitaja.lisaa(new PriorisoituTestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        hoitaja.lisaa(new TestiLuokka());
        assertEquals(3, hoitaja.getKasittelijat(TestiLuokka.class).size());
        assertEquals(2, hoitaja.getKasittelijat(PriorisoituTestiLuokka.class).size());
    }

    public static class TestiLuokka {

        @Kasittelija(KasittelyTyyppi.KAYNNISTA)
        @Prioriteetti(2)
        public void kaynnista() {
            add(KasittelyTyyppi.KAYNNISTA, 1);
        }

        @Kasittelija(KasittelyTyyppi.KAYNNISTA)
        public void kaynnista2() {
            add(KasittelyTyyppi.KAYNNISTA, 2);
        }

        @Kasittelija(KasittelyTyyppi.AJA)
        public void aja() {
            add(KasittelyTyyppi.AJA, 1);
        }

        @Kasittelija(KasittelyTyyppi.LOPETA)
        public void lopeta() {
            add(KasittelyTyyppi.LOPETA, 1);
        }

        public void add(KasittelyTyyppi tyyppi, int code) {
            String name = this.getClass().getName();
            name = name.split("\\$")[1];
            tehdyt.add(name + tyyppi + code);
        }
    }
    private static List<String> tehdyt;

    @Prioriteetti(1)
    public static class PriorisoituTestiLuokka extends TestiLuokka {
    }

    @Prioriteetti(-1)
    public static class EpaPriorisoituTestiLuokka extends TestiLuokka {

        @Kasittelija(KasittelyTyyppi.AJA)
        @Prioriteetti(2)
        @Override
        public void aja() {
            add(KasittelyTyyppi.AJA, 1);
        }
    }
}
