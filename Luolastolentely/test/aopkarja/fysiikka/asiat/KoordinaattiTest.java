package aopkarja.fysiikka.asiat;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class KoordinaattiTest {
    private Koordinaatti koordinaatti;
    private Koordinaatti koordinaatti2;
    private Koordinaatti koordinaatti3;
    private Koordinaatti k1;
    private Koordinaatti k2;
    private Koordinaatti k3;
    private Koordinaatti k4;
    private Koordinaatti k5;
    private Koordinaatti k6;
    
    @Before
    public void saato() {
        koordinaatti = new Koordinaatti(new double[]{2.0, 2.0, 2.0});
        koordinaatti2 = new Koordinaatti(new double[]{1.0, 1.0, 1.0});
        koordinaatti3 = new Koordinaatti(new double[]{3.0, 3.0, 3.0});
        k1 = new Koordinaatti(new double[]{-1, 0});
        k2 = new Koordinaatti(new double[]{0, 0});
        k3 = new Koordinaatti(new double[]{1, 0});
        k4 = new Koordinaatti(new double[]{-1, 1});
        k5 = new Koordinaatti(new double[]{0, 2});
        k6 = new Koordinaatti(new double[]{1, 0});
    }

    @Test
    public void muuttaminenToimii(){
        koordinaatti.muuta(koordinaatti2);
        assertTrue(koordinaatti.equals(koordinaatti2));
    }
    
    @Test
    public void siirtaminenToimii(){
        koordinaatti.siirra(koordinaatti2);
        assertTrue(koordinaatti.equals(koordinaatti3));
    }
    
    @Test
    public void etaisyysToiseenToimii(){
        assertEquals(3.0, koordinaatti.etaisyysToiseen(koordinaatti2), 0.0);
    }
    
    @Test
    public void etaisyysToimii(){
        assertEquals(Math.sqrt(3.0), koordinaatti.etaisyys(koordinaatti2), 0.001);
    }
    
    @Test
    public void equalsToimii(){
        assertTrue(koordinaatti.equals(new Koordinaatti(koordinaatti)));
    }
    
}
