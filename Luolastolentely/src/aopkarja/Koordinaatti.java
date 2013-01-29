package aopkarja;

import java.util.Arrays;

/**
 *
 * @author Antti
 */
public class Koordinaatti {

    private int[] koordinaatti;

    public Koordinaatti(int... koordinaatit) {
        muuta(koordinaatit);
    }

    public Koordinaatti() {
    }

    public void muuta(Koordinaatti koordinaatit) {
        muuta(koordinaatit.koordinaatti);
    }

    public final void muuta(int[] koordinaatit) {
        if (this.koordinaatti == null || koordinaatit.length >= this.koordinaatti.length) {
            this.koordinaatti = koordinaatit.clone();
            return;
        }
        System.arraycopy(koordinaatit, 0, this.koordinaatti, 0, koordinaatit.length);
    }

    public int getX() {
        return koordinaatti[0];
    }

    public int getY() {
        return koordinaatti[1];
    }

    public int getZ() {
        return koordinaatti[2];
    }

    public void siirra(Koordinaatti koordinaatit) {
        siirra(koordinaatit.koordinaatti);
    }

    public void siirra(int... koordinaatit) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new int[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatti.length) {
            System.arraycopy(this.koordinaatti, 0, new int[koordinaatit.length], 0, this.koordinaatti.length);
        }
        for (int n = 0; n < koordinaatit.length; n++) {
            this.koordinaatti[n] += koordinaatit.length;
        }
    }

    public double etaisyys(Koordinaatti koordinaatit) {
        return etaisyys(koordinaatit.koordinaatti);
    }

    public int etaisyysToiseen(Koordinaatti koordinaatit) {
        return etaisyysToiseen(koordinaatit.koordinaatti);
    }

    public double etaisyys(int[] koordinaatit) {
        return Math.sqrt(etaisyysToiseen(koordinaatit));
    }

    public int etaisyysToiseen(int[] koordinaatit) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new int[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatti.length) {
            System.arraycopy(this.koordinaatti, 0, new int[koordinaatit.length], 0, this.koordinaatti.length);
        }
        int result = 0;
        for (int n = 0; n < koordinaatit.length; n++) {
            int temp = koordinaatit[n] - this.koordinaatti[n];
            result += temp * temp;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return 83 * 3 + Arrays.hashCode(this.koordinaatti);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.koordinaatti, ((Koordinaatti) o).koordinaatti);
    }

    public int[] getKoordinaatti() {
        return koordinaatti;
    }
}
