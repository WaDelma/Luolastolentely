package aopkarja;

import aopkarja.UI.UIKasittelija;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aopkarja
 */
public class Luolastolentely {

    private static Luolastolentely instanssi;
    private static VirheidenKasittelija virheidenKasittelija;
    private Map<Class, Object> kasittelijat;
    private boolean kaynnissa;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        virheidenKasittelija = new VirheidenKasittelija();
        try {
            instanssi = new Luolastolentely();
            instanssi.initialisoi();
            instanssi.aja();
        } catch (Exception e) {
            virheidenKasittelija.ilmoita(e);
        } finally {
            instanssi.sulje();
        }
    }

    private void initialisoi() {
        kasittelijat = new HashMap<>();
        lisaaKasittelija(new UIKasittelija());
        kasittele(KasittelyTyyppi.KAYNNISTA);
        kaynnissa = true;
    }

    private void aja() {
        do{
            kasittele(KasittelyTyyppi.AJA);
        }while(kaynnissa);
    }

    private void sulje() {
        kasittele(KasittelyTyyppi.LOPETA);
    }
    
    public void lopetaPeli(){
        kaynnissa = false;
    }

    public static Luolastolentely getInstanssi() {
        return instanssi;
    }

    public void lisaaKasittelija(Object kasittelija) {
        kasittelijat.put(kasittelija.getClass(), kasittelija);
    }

    public Object getKasittelija(Class luokka) {
        return kasittelijat.get(luokka);
    }

    public static VirheidenKasittelija getVirheidenKasittelija() {
        return virheidenKasittelija;
    }

    private void kasittele(KasittelyTyyppi kasittelyTyyppi) {
        for (Object kasittelija : kasittelijat.values()) {
            for (Method metodi : kasittelija.getClass().getDeclaredMethods()) {
                Kasittelija annotaatio = metodi.getAnnotation(Kasittelija.class);
                if (annotaatio != null && annotaatio.value() == kasittelyTyyppi) {
                    try {
                        metodi.invoke(kasittelija);
                        break;
                    } catch (ReflectiveOperationException e) {
                        virheidenKasittelija.ilmoita(e);
                    }
                }
            }
        }
    }
}
