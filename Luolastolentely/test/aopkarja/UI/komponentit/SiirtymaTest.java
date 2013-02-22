package aopkarja.UI.komponentit;

import aopkarja.UI.Siirtyma;
import aopkarja.UI.Moodi;
import aopkarja.UI.Renderoija;
import aopkarja.kasittely.Tapahtuma;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Antti
 */
public class SiirtymaTest {

    @Before
    public void ennen() {
    }

    @Test
    public void minimiKonstruktoriLoytyy() {
        Siirtyma siirtyma = new Siirtyma(DummyMoodi.class, new DummyRenderoija());
    }

    @Test
    public void toinenKonstruktoriLoytyy() {
        Siirtyma siirtyma = new Siirtyma(DummyMoodi.class, new DummyRenderoija(), 42);
    }

    private static class DummyMoodi extends Moodi {

        public DummyMoodi(Renderoija renderoija) {
            super(renderoija);
        }

        public DummyMoodi(Renderoija renderoija, Integer value) {
            super(renderoija);
        }

        @Override
        public void tapahtuu(Tapahtuma tapahtuma) {
        }
    }

    private static class DummyRenderoija extends Renderoija {
    }
}
