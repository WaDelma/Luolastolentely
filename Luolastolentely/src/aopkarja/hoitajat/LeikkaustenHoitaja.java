package aopkarja.hoitajat;

import aopkarja.Alue;
import aopkarja.Koordinaatti;
import java.util.List;

/**
 * Auttaa alueiden leikkauksen havaitsemisessa
 * 
 * @author aopkarja
 */
public class LeikkaustenHoitaja {

    public static boolean leikkaako(Alue alue1, Alue alue2) {
        List<Koordinaatti> koordinaatit1 = alue1.getKoordinaatit();
        List<Koordinaatti> koordinaatit2 = alue2.getKoordinaatit();
        if (koordinaatit1.size() == 4) {
            switch (aabbTesti(koordinaatit1, koordinaatit2)) {
                case 0:
                    return false;
                case 1:
                    return true;
            }
        } else if (koordinaatit2.size() == 4) {
            switch (aabbTesti(koordinaatit2, koordinaatit1)) {
                case 0:
                    return false;
                case 1:
                    return true;
            }
        }
        return alue1.getKeskipiste().etaisyys(alue2.getKeskipiste())
                < alue1.getIsoimmanSisallaOlevanYmpyranSade()
                + alue2.getIsoimmanSisallaOlevanYmpyranSade();
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

    private LeikkaustenHoitaja() {
    }
}
