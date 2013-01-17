package aopkarja.kasitttely;

import aopkarja.VirheidenHoitaja;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class KasittelynHoitaja {

    private List<Object> kasittelijat;

    public List<Object> getKasittelijat() {
        return kasittelijat;
    }

    public KasittelynHoitaja() {
        this.kasittelijat = new ArrayList<>();
    }

    public void kasittele(KasittelyTyyppi tyyppi) {
        for (Object kasittelija : kasittelijat) {
            kasittele(tyyppi, kasittelija);
        }
    }

    public void kasittele(KasittelyTyyppi tyyppi, Object kasittelija) {
        for (Method metodi : kasittelija.getClass().getMethods()) {
            Kasittelija annotaatio = metodi.getAnnotation(Kasittelija.class);
            if (annotaatio != null && annotaatio.value() == tyyppi) {
                try {
                    metodi.invoke(kasittelija);
                } catch (ReflectiveOperationException e) {
                    VirheidenHoitaja.ilmoita(e);
                }
            }
        }
    }

    public void lisaa(Object kasittelija) {
        kasittelijat.add(kasittelija);
        Collections.sort(kasittelijat, new PrioriteettiKomparaattori());
    }

    public List<Object> getKasittelijat(Class luokka) {
        List<Object> vastaus = new ArrayList<>();
        for (Object o : kasittelijat) {
            if (luokka.equals(o.getClass())) {
                vastaus.add(o);
            }
        }
        return vastaus;
    }

    public boolean lisatty(Object moodi) {
        return kasittelijat.contains(moodi);
    }
}
