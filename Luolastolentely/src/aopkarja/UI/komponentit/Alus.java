package aopkarja.UI.komponentit;

import aopkarja.UI.Komponentti;
import aopkarja.UI.Renderoija;
import aopkarja.UI.Vari;
import aopkarja.fysiikka.FysiikanKasittelija;
import aopkarja.fysiikka.FyysinenKappale;
import aopkarja.fysiikka.asiat.Koordinaatti;
import aopkarja.kasittely.Kasittely;
import aopkarja.kasittely.Tapahtuma;
import aopkarja.kasittely.kasittelijat.PeliTilanKasittelija;
import aopkarja.kasittely.tapahtumat.EnergianLisaysTapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;
import org.lwjgl.input.Keyboard;

/**
 * Pelaajan alus
 *
 * @author aopkarja
 */
public class Alus extends Komponentti {

    private static final double PAINETUN_NOPEUS = 0.03;
    private static final double PAINETUN_ENERGIA_HAVIO = 0.03;
    private static final double PAINALLUKSEN_NOPEAUS = 1.0;
    private static final double PAINALLUKSEN_ENERGIA_HAVIO = 0.75;
    private static final double KUOLEMA_AJASTIN_MAX = 200;
    private FyysinenKappale fyysinenKappale;
    private double energia = 50;
    private double kuolemaAjastin = KUOLEMA_AJASTIN_MAX;
    private double[] suunta;
    private int skippausAjastin = 100;

    /**
     * Luo uuden aluksen tiettyyn koordinaattiin.
     *
     * @param koordinaatti
     * @param renderoija
     * @param omistaja
     */
    public Alus(Koordinaatti koordinaatti, Renderoija renderoija, Komponentti omistaja) {
        super(renderoija, omistaja);
        init(koordinaatti);
    }

    /**
     * Luo uuden aluksen origoon.
     *
     * @param renderoija
     * @param omistaja
     */
    public Alus(Renderoija renderoija, Komponentti omistaja) {
        this(Koordinaatti.getOrigo(), renderoija, omistaja);
    }

    public Alus(Alus alus, Komponentti omistaja) {
        super(alus.getRenderoija(), omistaja);
        energia = alus.energia;
        init(alus.getAlue().getKeskipiste());
    }

    private void init(Koordinaatti koordinaatti) {
        suunta = new double[2];
        fyysinenKappale = new FyysinenKappale(this);
        getOmistaja().teeKasittelijoille(FysiikanKasittelija.class, new Kasittely<FysiikanKasittelija>() {
            @Override
            public void tee(FysiikanKasittelija kasittelija) {
                kasittelija.lisaa(fyysinenKappale);
            }
        });
        final Alus instanssi = this;
        getOmistaja().teeKasittelijoille(PeliTilanKasittelija.class, new Kasittely<PeliTilanKasittelija>() {
            @Override
            public void tee(PeliTilanKasittelija kasittelija) {
                kasittelija.lisaa(instanssi);
            }
        });

        fyysinenKappale.setPaino(1.0);
        int leveys = 10;
        int korkeus = 10;
        double x = koordinaatti.get(0);
        double y = koordinaatti.get(1);
        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));

        getAlue().lisaa(new Koordinaatti(x, y));
        getAlue().lisaa(new Koordinaatti(x + leveys, y + korkeus));
        getAlue().lisaa(new Koordinaatti(x, y + korkeus));
    }

    @Override
    public void tapahtuu(Tapahtuma tapahtuma) {
        if (!aktiivinen()) {
            return;
        }
        Object[] tieto = tapahtuma.getTieto();
        if (tapahtuma instanceof EnergianLisaysTapahtuma) {
            energia += (double) tapahtuma.getTieto()[0];
        }
        if (tapahtuma instanceof Painallus) {
            if (tieto[0].equals(Boolean.TRUE)) {
                if (energia >= 0) {
                    switch ((int) tieto[1]) {
                        case Keyboard.KEY_W:
                            suunta[1] = PAINETUN_NOPEUS;
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(0, PAINALLUKSEN_NOPEAUS));
                            energia -= PAINALLUKSEN_ENERGIA_HAVIO;
                            break;
                        case Keyboard.KEY_A:
                            suunta[0] = -PAINETUN_NOPEUS;
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(-PAINALLUKSEN_NOPEAUS, 0));
                            energia -= PAINALLUKSEN_ENERGIA_HAVIO;
                            break;
                        case Keyboard.KEY_S:
                            suunta[1] = -PAINETUN_NOPEUS / 2;
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(0, -PAINALLUKSEN_NOPEAUS / 2));
                            energia -= PAINALLUKSEN_ENERGIA_HAVIO / 2;
                            break;
                        case Keyboard.KEY_D:
                            suunta[0] = PAINETUN_NOPEUS;
                            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(PAINALLUKSEN_NOPEAUS, 0));
                            energia -= PAINALLUKSEN_ENERGIA_HAVIO;
                            break;
                        case Keyboard.KEY_Q:
                            if (skippausAjastin <= 0) {
                                getOmistaja().teeKasittelijoille(PeliTilanKasittelija.class, new Kasittely<PeliTilanKasittelija>() {
                                    @Override
                                    public void tee(PeliTilanKasittelija kasittelija) {
                                        kasittelija.skippaa();
                                    }
                                });
                            }
                            break;
                        default:
                    }
                }
            } else {
                switch ((int) tieto[1]) {
                    case Keyboard.KEY_W:
                        suunta[1] = 0;//-PAINETUN_NOPEUS;
                        break;
                    case Keyboard.KEY_A:
                        suunta[0] = 0;//PAINETUN_NOPEUS;
                        break;
                    case Keyboard.KEY_S:
                        suunta[1] = 0;//PAINETUN_NOPEUS;
                        break;
                    case Keyboard.KEY_D:
                        suunta[0] = 0;//-PAINETUN_NOPEUS;
                        break;
                    default:
                }
            }
        }
    }

    /**
     * @return Paljonko aluksella on energiaa jäljellä
     */
    public double getEnergia() {
        return energia;
    }

    @Override
    protected void aja() {
        if (skippausAjastin > 0) {
            skippausAjastin--;
        }
        if (energia <= 0) {
            kuolemaAjastin--;
            if (kuolemaAjastin <= 0) {
                setAktiivisuus(false);
            }
        } else {
            if (suunta[0] != 0) {
                energia -= PAINETUN_ENERGIA_HAVIO;
            }
            if (suunta[1] != 0) {
                energia -= PAINETUN_ENERGIA_HAVIO;
            }
            fyysinenKappale.lisaaVoimanLahde(new Koordinaatti(suunta[0], suunta[1]));
            kuolemaAjastin = KUOLEMA_AJASTIN_MAX;
            Koordinaatti keski = getAlue().getKeskipiste();
            if (keski.get(0) < 0 || keski.get(1) < 0 || keski.get(0) > 800 || keski.get(1) > 600) {
                energia--;
            }
            if (keski.get(0) < -100 || keski.get(1) < -100 || keski.get(0) > 900 || keski.get(1) > 700) {
                setAktiivisuus(false);
            }

        }
    }

    @Override
    public Vari getVari() {
        return new Vari(0.5, 1, 1);
    }
}
