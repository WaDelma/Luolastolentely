package aopkarja.UI;

import aopkarja.Koordinaatit;
import aopkarja.kasitttely.Kasittelija;
import aopkarja.kasitttely.KasittelyTyyppi;
import aopkarja.kasitttely.KasittelynHoitaja;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author aopkarja
 */
public class Valikko implements Komponentti{
    private KasittelynHoitaja<Komponentti> komponentit;
    //private List<Komponentti> komponentit;

    public Valikko() {
        komponentit = new KasittelynHoitaja();
    }
    
    @Kasittelija(KasittelyTyyppi.KAYNNISTA)
    public void kaynnista(){
        GL11.glClearColor(0.9f, 0.60f, 0.3f, 1.0f);
    }

    @Kasittelija(KasittelyTyyppi.AJA)
    public void aja() {
        komponentit.kasittele(KasittelyTyyppi.AJA);
//        GL11.glColor3f(1.0f, 1.0f, 0.0f);
//        GL11.glBegin(GL11.GL_QUADS);
//        {
//            GL11.glVertex2f(100, 100);
//            GL11.glVertex2f(100 + 200, 100);
//            GL11.glVertex2f(100 + 200, 100 + 200);
//            GL11.glVertex2f(100, 100 + 200);
//        }
//        GL11.glEnd();
    }

    @Override
    public Koordinaatit getKoordinaatit() {
        return null;
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if(tapahtuma instanceof Painallus){
            Komponentti piste = new Piste((Koordinaatit)tapahtuma.getTieto()[1]);
            for (Komponentti komponentti : komponentit.getKasittelijat()) {
                if(komponentti.leikkaa(piste)){
                    komponentti.tapahtuu(tapahtuma);
                    return;
                }
            }
        }
    }

    @Override
    public void lisaaKomponentti(Komponentti komponentti) {
        komponentit.lisaa(komponentti);
    }

    @Override
    public Komponentti getOmistaja() {
        return null;
    }

    @Override
    public boolean leikkaa(Komponentti komponentti) {
        return true;
    }
}
