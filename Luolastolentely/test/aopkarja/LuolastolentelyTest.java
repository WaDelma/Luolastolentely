package aopkarja;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class LuolastolentelyTest {

    private Luolastolentely peli;
    private Method method;

    @Before
    public void saato() {
        peli = new Luolastolentely();
    }

    @Test
    public void intialisointi() {
        try {
            method = peli.getClass().getDeclaredMethod("initialisoi");
            method.setAccessible(true);
            method.invoke(peli);
            Field field = peli.getClass().getDeclaredField("kasittelijat");
            field.setAccessible(true);
            assertNotNull(field.get(peli));
        } catch (NoSuchFieldException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(LuolastolentelyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}