package aopkarja.kasittelijat.fysiikka;

import aopkarja.Alue;
import aopkarja.Koordinaatti;
import java.util.ArrayList;
import java.util.List;

/**
 * Auttaa alueiden leikkauksen havaitsemisessa
 *
 * @author aopkarja
 */
public class LeikkaustenHoitaja {

    private static final double TARKKUUS = 10;

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
        for (int i = 0; i < koordinaatit1.size() / 3; i++) {
            kolmio.add(koordinaatit1.get(i * 3 + 0));
            kolmio.add(koordinaatit1.get(i * 3 + 1));
            kolmio.add(koordinaatit1.get(i * 3 + 2));
            if (testaaKolmioJaKuvio(kolmio, koordinaatit2)) {
                return true;
            }
            kolmio.clear();
        }
        return false;
//        if (koordinaatit1.size() == 3) {
//            return testaaKolmioJaKuvio(koordinaatit1, koordinaatit2);
//        } else if (koordinaatit2.size() == 3) {
//            return testaaKolmioJaKuvio(koordinaatit2, koordinaatit1);
//        } else if (koordinaatit1.size() == 4) {
//            switch (aabbTesti(koordinaatit1, koordinaatit2)) {
//                case 0:
//                    return false;
//                case 1:
//                    return true;
//            }
//        } else if (koordinaatit2.size() == 4) {
//            switch (aabbTesti(koordinaatit2, koordinaatit1)) {
//                case 0:
//                    return false;
//                case 1:
//                    return true;
//            }
//        }
//        return true;
    }

    public static boolean testaaKolmioJaKuvio(List<Koordinaatti> kolmio, List<Koordinaatti> kuvio) {
        Koordinaatti eka = kuvio.get(0);
        for (int i = 1; i < kuvio.size(); i++) {
            Koordinaatti toka = kuvio.get(i);

            //Laske kuinka usein testi tehdään
            Koordinaatti siirto = new Koordinaatti(toka);
            siirto.siirra(eka.vastaKohta());
            double etaisyys = siirto.etaisyys(toka);
            double maara = Math.floor(etaisyys / TARKKUUS);
            siirto.kerro(1 / maara);

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

    private static int aabbTesti(List<Koordinaatti> ehkaAABB, List<Koordinaatti> toinen) {
        Koordinaatti k1 = ehkaAABB.get(0);
        Koordinaatti k2 = ehkaAABB.get(1);
        Koordinaatti k3 = ehkaAABB.get(2);
        Koordinaatti k4 = ehkaAABB.get(3);
        if (Math.abs(k1.etaisyysToiseen(k2) - k3.etaisyysToiseen(k4)) > .0000001) {
            return -1;
        }
        if (Math.abs(k1.etaisyysToiseen(k3) - k2.etaisyysToiseen(k4)) > .0000001) {
            return -1;
        }
        for (Koordinaatti koordinaatti : toinen) {
            if (koordinaatti.valissako(k1, k2, false, true) && koordinaatti.valissako(k1, k3, true, false)) {
                return 1;
            }
            if (koordinaatti.valissako(k1, k2, true, false) && koordinaatti.valissako(k1, k3, false, true)) {
                return 1;
            }
        }
        return 0;

    }

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
