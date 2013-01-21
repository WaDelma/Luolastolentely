package aopkarja;

import aopkarja.kasitttely.KasittelynHoitaja;
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
    public void setUp() {
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

    @Test
    public void ajaminen() {
        try {
            Field field = peli.getClass().getDeclaredField("kaynnissa");
            field.setAccessible(true);
            field.setBoolean(peli, true);
            
            field = peli.getClass().getDeclaredField("kasittelijat");
            field.setAccessible(true);
            field.set(peli, new KasittelynHoitaja());

            method = peli.getClass().getDeclaredMethod("aja");
            method.setAccessible(true);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        method.invoke(peli);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ex) {
                        fail(ex.getMessage());
                    }
                }
            };
            thread.start();
            assertTrue(thread.isAlive());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                fail(ex.getMessage());
            }
            assertTrue(thread.isAlive());
            peli.lopetaPeli();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                fail(ex.getMessage());
            }
            assertFalse(thread.isAlive());
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException | SecurityException ex) {
            fail(ex.getMessage());
        }
    }
}