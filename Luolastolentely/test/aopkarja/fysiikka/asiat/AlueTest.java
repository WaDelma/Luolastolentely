package aopkarja.fysiikka.asiat;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class AlueTest {

    private Alue alue;

    @Before
    public void saato() {
        alue = new Alue();
    }

    @Test
    public void luontiToimii() {
        assertNotNull(alue.getKoordinaatit());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getterillaEiVoiLisata() {
        alue.getKoordinaatit().add(null);
    }
}
