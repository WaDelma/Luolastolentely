package aopkarja.fysiikka;

import aopkarja.fysiikka.asiat.Alue;
import aopkarja.fysiikka.asiat.Koordinaatti;
import java.util.ArrayList;
import java.util.List;

/**
 * Auttaa alueiden leikkauksen havaitsemisessa
 *
 * @author aopkarja
 */
public class LeikkaustenHoitaja {

    private static final double TARKKUUS = 10;

    /**
     * Testaa leikkaako kaksi kolmioista koostuvaa aluetta
     *
     * @param alue1 Alue
     * @param alue2 Alue
     * @return leikkaako?
     */
    public static boolean leikkaako(Alue alue1, Alue alue2) {
        //Jos tämä palaa emme ainakaan törmää. Ilman tätä FPS kuolee :P
        double etaisyys = alue1.getKeskipiste().etaisyys(alue2.getKeskipiste());
        double max = alue1.getPienimmanUlkonaOlevanYmpyranSade()
                + alue2.getPienimmanUlkonaOlevanYmpyranSade();
        if (etaisyys > max) {
            return false;
        }

        //Pitää tehdä tarkempi testi
        List<Koordinaatti> koordinaatit1 = alue1.getKoordinaatit();
        List<Koordinaatti> koordinaatit2 = alue2.getKoordinaatit();
        List<Koordinaatti> kolmio = new ArrayList<>();
        if (koordinaatit1.size() >= 3) {
            for (int i = 0; i < Math.floor(koordinaatit1.size() / 3); i++) {
                kolmio.add(koordinaatit1.get(i * 3 + 0));
                kolmio.add(koordinaatit1.get(i * 3 + 1));
                kolmio.add(koordinaatit1.get(i * 3 + 2));
                if (testaaKolmioJaKuvio(kolmio, koordinaatit2)) {
                    return true;
                }
                kolmio.clear();
            }
        }
        if (koordinaatit2.size() >= 3) {
            for (int i = 0; i < Math.floor(koordinaatit2.size() / 3); i++) {
                kolmio.add(koordinaatit2.get(i * 3 + 0));
                kolmio.add(koordinaatit2.get(i * 3 + 1));
                kolmio.add(koordinaatit2.get(i * 3 + 2));
                if (testaaKolmioJaKuvio(kolmio, koordinaatit1)) {
                    return true;
                }
                kolmio.clear();
            }
        }
        return false;
    }

    /**
     * Testaa leikkaako kolmio ja kuvio
     *
     * @param kolmio Alue, jolla on kolme koordinaattia eli kolmio
     * @param kuvio Alue
     * @return leikkaako?
     */
    public static boolean testaaKolmioJaKuvio(List<Koordinaatti> kolmio, List<Koordinaatti> kuvio) {
        Koordinaatti eka = kuvio.get(0);
        for (int i = 1; i <= kuvio.size(); i++) {
            Koordinaatti toka = kuvio.get(i % kuvio.size());

            //Laske kuinka usein testi tehdään
            Koordinaatti siirto = new Koordinaatti(toka);
            siirto.siirra(eka.vastaKohta());
//            int maara = 1;
            double etaisyys = siirto.etaisyys(toka);
            double maara = Math.floor(etaisyys / TARKKUUS);
            siirto.skaalaa(1 / maara);

            //Suorita testit
            Koordinaatti ekaTemp = new Koordinaatti(eka);
            if (pisteKolmioSisalla(ekaTemp, kolmio)) {
                return true;
            }
            for (int j = 0; j < maara; j++) {
                ekaTemp.siirra(siirto);
                if (pisteKolmioSisalla(ekaTemp, kolmio)) {
                    return true;
                }
            }
            eka = toka;
        }
        return false;
    }

    /**
     * Testaa onko piste kolmio sisällä
     *
     * @param piste Koordinaatti
     * @param kolmio Alue, jolla on kolme koordinaattia eli kolmio
     * @return Onko sisällä?
     */
    public static boolean pisteKolmioSisalla(Koordinaatti piste, List<Koordinaatti> kolmio) {
        //Laske vektorit  
        Koordinaatti vastaA = kolmio.get(0).vastaKohta();
        Koordinaatti CpoisA = new Koordinaatti(kolmio.get(1));
        CpoisA.siirra(vastaA);
        Koordinaatti BpoisA = new Koordinaatti(kolmio.get(2));
        BpoisA.siirra(vastaA);
        Koordinaatti PpoisA = new Koordinaatti(piste);
        PpoisA.siirra(vastaA);

        //Laske pistetulot
        double tuloCC = CpoisA.pisteTulo(CpoisA);
        double tuloCB = CpoisA.pisteTulo(BpoisA);
        double tuloCP = CpoisA.pisteTulo(PpoisA);
        double tuloBB = BpoisA.pisteTulo(BpoisA);
        double tuloBP = BpoisA.pisteTulo(PpoisA);

        //Laske barysentriset koordinaatit
        double temp = 1 / (tuloCC * tuloBB - tuloCB * tuloCB);
        double u = (tuloBB * tuloCP - tuloCB * tuloBP) * temp;
        double v = (tuloCC * tuloBP - tuloCB * tuloCP) * temp;

        //Onko piste kolmiossa?
        return (u >= 0) && (v >= 0) && (u + v <= 1);

    }

    /**
     * Testaa leikkaako alue listassa olevien alueiden kanssa lukuun ottamatta
     * mahdollista itseään.
     *
     * @param alue Alue
     * @param alueet Lista alueista
     * @return Alue, jonka kanssa leikkaa tai null jo ei leikkaa
     */
    public static Alue leikkaako(Alue alue, List<Alue> alueet) {
        for (Alue alueTemp : alueet) {
            if (alue != alueTemp && leikkaako(alue, alueTemp)) {
                return alueTemp;
            }
        }
        return null;
    }

    private LeikkaustenHoitaja() {
    }
}
