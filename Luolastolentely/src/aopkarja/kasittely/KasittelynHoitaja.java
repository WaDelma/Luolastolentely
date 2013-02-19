package aopkarja.kasittely;

import aopkarja.hoitajat.LokiHoitaja;
import aopkarja.kasittely.tapahtumat.TyhjaTapahtuma;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hoitaa {@link Kasittelija}n/t, jotka on lisätty siihen.
 *
 * @author aopkarja
 */
public class KasittelynHoitaja<T> {

    private List<T> kasittelijaOliot;
    private Map<KasittelyTyyppi, List<KasittelijaKapseli>> kasittelijat;
    private static final Tapahtuma tyhjaTapahtuma = new TyhjaTapahtuma();

    public List<T> getKasittelijat() {
        return Collections.unmodifiableList(kasittelijaOliot);
    }

    public KasittelynHoitaja() {
        kasittelijaOliot = new ArrayList<>();
        kasittelijat = new HashMap<>();
    }

    public void kasittele(KasittelyTyyppi tyyppi) {
        kasittele(kasittelijat.get(tyyppi), tyhjaTapahtuma);
    }

    public void kasittele(Kasittely<T> kasittely) {
        for (T t : kasittelijaOliot) {
            kasittely.tee(t);
        }
    }

    public void kasittele(KasittelyTyyppi tyyppi, Tapahtuma tapahtuma) {
        kasittele(kasittelijat.get(tyyppi), tapahtuma);
    }

    public void kasittele(Class<T> luokka, Kasittely<T> kasittely) {
        for (T t : getKasittelijat(luokka)) {
            kasittely.tee(t);
        }
    }

    /**
     * Käsittelee tietyn tyyppisen {@link Kasittelija}n/t oliossa.
     *
     * @param tyyppi
     * @param kasittelija
     */
    public void kasittele(KasittelyTyyppi tyyppi, T kasittelija) {
        kasittele(tyyppi, kasittelija, tyhjaTapahtuma);
    }

    /**
     * Käsittelee tietyn tyyppisen tapahtuman {@link Kasittelija}n/t oliossa.
     *
     * @param tyyppi
     * @param kasittelija
     * @param tapahtuma
     */
    public void kasittele(KasittelyTyyppi tyyppi, T kasittelija, Tapahtuma tapahtuma) {
        if (kasittelijat == null || kasittelijat.isEmpty()) {
            return;
        }
        List<KasittelijaKapseli> lista = kasittelijat.get(tyyppi);
        if (lista == null) {
            return;
        }
        for (KasittelijaKapseli kapseli : lista) {
            if (kasittelija == null || kasittelija.equals(kapseli.olio)) {
                kapseli.kayta(tapahtuma);
            }
        }
    }

    /**
     * Käsittelee tietyn tyyppiset tapahtuman {@link Kasittelija}t.
     *
     * @param kasittelijat
     * @param tapahtuma
     */
    public void kasittele(List<KasittelijaKapseli> kasittelijat, Tapahtuma tapahtuma) {
        if (kasittelijat == null || kasittelijat.isEmpty()) {
            return;
        }
        for (KasittelijaKapseli kapseli : kasittelijat) {
            kapseli.kayta(tapahtuma);
        }
    }

    /**
     * Lisää {@link Kasittelija}n.
     *
     * @param kasittelija
     */
    public void lisaa(T kasittelija) {
        int prioriteetti = 0;
        Class<?> luokka = kasittelija.getClass();
        Prioriteetti prioriteettiAnnotaation = luokka.getAnnotation(Prioriteetti.class);
        if (prioriteettiAnnotaation != null) {
            prioriteetti = prioriteettiAnnotaation.value();
        }
        for (Method metodi : getMetodit(luokka)) {
            Kasittelija annotaatio = metodi.getAnnotation(Kasittelija.class);
            if (annotaatio != null) {
                int valiaikainenPrioriteetti = prioriteetti;
                if (metodi.isAnnotationPresent(Prioriteetti.class)) {
                    valiaikainenPrioriteetti += metodi.getAnnotation(Prioriteetti.class).value();
                }
                lisaa(annotaatio.value(), new KasittelijaKapseli(kasittelija, metodi, valiaikainenPrioriteetti));
            }
        }
        kasittelijaOliot.add(kasittelija);
        Collections.sort(kasittelijaOliot, new PrioriteettiOlioKomparaattori());
    }

    private void lisaa(KasittelyTyyppi tyyppi, KasittelijaKapseli kapseli) {
        List<KasittelijaKapseli> kapselit = kasittelijat.get(tyyppi);
        if (kapselit == null) {
            kapselit = new ArrayList<>();
        }
        kapselit.add(kapseli);
        Collections.sort(kapselit, new PrioriteettiKomparaattori());
        kasittelijat.put(tyyppi, kapselit);
    }

    /**
     * Palauttaa kaikki tietyn luokan {@link Kasittelija}t.
     *
     * @param luokka
     * @return {@link Kasittelija} lista
     */
    public List<T> getKasittelijat(Class<T> luokka) {
        List<T> vastaus = new ArrayList<>();
        for (T olio : kasittelijaOliot) {
            if (luokka.equals(olio.getClass())) {
                vastaus.add(olio);
            }
        }
        return vastaus;
    }

    /**
     *
     * @param kasittelija
     * @return Onko {@link Kasittelija} lisätty.
     */
    public boolean lisatty(T kasittelija) {
        return kasittelijaOliot.contains(kasittelija);
    }

    /**
     * Tyhjentää lisätyt {@link Kasittelija}t.
     *
     */
    public void tyhjenna() {
        kasittelijaOliot = new ArrayList<>();
        kasittelijat = new HashMap<>();
    }
    private Set<Method> metodit;

    private void getMetoditRekursio(Class luokka) {
        metodit.addAll(Arrays.asList(luokka.getDeclaredMethods()));
        metodit.addAll(Arrays.asList(luokka.getMethods()));
        Class yliLuokka = luokka.getSuperclass();
        if (yliLuokka != null) {
            getMetoditRekursio(yliLuokka);
        }
    }

    private Set<Method> getMetodit(Class luokka) {
        metodit = new HashSet();
        getMetoditRekursio(luokka);
        return metodit;
    }

    /**
     * Kapseli, jolla {@link Kasittelija}t tallennetaan.
     *
     * @param <T>
     */
    protected static class KasittelijaKapseli<T> {

        private final Method metodi;
        private final int prioriteetti;
        private final T olio;
        private boolean tapahtumiaKasitteleva;

        private KasittelijaKapseli(T olio, Method metodi, int prioriteetti) {
            this.olio = olio;
            this.metodi = metodi;
            metodi.setAccessible(true);
            this.prioriteetti = prioriteetti;
            Class<?>[] parametrit = metodi.getParameterTypes();
            if (parametrit.length > 1) {
                throw new IllegalArgumentException("Wrong amount of parameters (" + parametrit.length + ") in method " + metodi.getName());
            } else if (parametrit.length == 1) {
                if (parametrit[0].equals(Tapahtuma.class)) {
                    tapahtumiaKasitteleva = true;
                } else {
                    throw new IllegalArgumentException("Method with no parameters or with parameter type of Tapahtuma expected. Not type of " + parametrit[0].getName());
                }
            }
        }

        /**
         * Käyttää kapselin sisällä olevaa {@link Kasittelija}a.
         *
         * @param tapahtuma Voi olla null
         */
        public void kayta(Tapahtuma tapahtuma) {
            try {
                if (tapahtumiaKasitteleva) {
                    metodi.invoke(olio, tapahtuma);
                } else {
                    metodi.invoke(olio);
                }
            } catch (ReflectiveOperationException e) {
                System.out.println(metodi);
                LokiHoitaja.ilmoita(e);
            }
        }

        /**
         * @return {@link Kasittelija}n prioriteetti.
         */
        public int getPrioriteetti() {
            return prioriteetti;
        }

        @Override
        public String toString() {
            return olio.getClass().getName() + "." + metodi.getName() + ": " + prioriteetti;
        }
    }
}
