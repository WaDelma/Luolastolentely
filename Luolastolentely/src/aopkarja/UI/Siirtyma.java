package aopkarja.UI;

import aopkarja.hoitajat.KirjanpidonHoitaja;
import aopkarja.kasittely.Kasittely;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Siirtymä toiseen moodiin
 * 
 * @author Antti
 */
public class Siirtyma {

    private final Object[] parametrit;
    private Constructor<? extends Moodi> konstruktori;

    public Siirtyma(Class<? extends Moodi> moodiLuokka, Object... parametrit) {
        this.parametrit = parametrit;

        try {
            for (Constructor konstrutori : moodiLuokka.getDeclaredConstructors()) {
                if (this.parametrit.length == konstrutori.getParameterTypes().length) {
                    boolean pystyy = true;
                    for (int i = 0; i < this.parametrit.length; i++) {
                        if (!konstrutori.getParameterTypes()[i].isAssignableFrom(this.parametrit[i].getClass())) {
                            pystyy = false;
                        }
                    }
                    if (pystyy) {
                        this.konstruktori = konstrutori;
                    }
                }
            }
            if (konstruktori == null) {
                String taulukkoMerkkijono = Arrays.toString(getLuokkaTaulukko(this.parametrit));
                taulukkoMerkkijono = taulukkoMerkkijono.substring(1, taulukkoMerkkijono.length() - 1);
                throw new IllegalArgumentException("Couln't find constructor with parameters assignable to: " + vaihdaVika(", ", " and ", taulukkoMerkkijono));
            }
        } catch (SecurityException ex) {
            KirjanpidonHoitaja.ilmoita(ex);
        }
    }

    public void siirry(Komponentti komponentti) {
        komponentti.teeKasittelijoille(UIKasittelija.class, new Kasittely<UIKasittelija>() {
            @Override
            public void tee(UIKasittelija kasittelija) {
                try {
                    kasittelija.setMoodi(konstruktori.newInstance(parametrit));
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    KirjanpidonHoitaja.ilmoita(ex);
                }
            }
        });
    }

    private String vaihdaVika(String mista, String mihin, String merkkijono) {
        int indeksi = merkkijono.lastIndexOf(mista);
        if (indeksi == -1) {
            return merkkijono;
        }
        return merkkijono.substring(0, indeksi) + mihin + merkkijono.substring(indeksi + mista.length());
    }

    private Object[] getLuokkaTaulukko(Object[] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            String[] leikattu = taulukko[i].getClass().getName().split("\\.");
            taulukko[i] = leikattu[leikattu.length - 1];
        }
        return taulukko;
    }
}
