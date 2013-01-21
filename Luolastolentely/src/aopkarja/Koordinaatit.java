package aopkarja;

import java.util.Arrays;

/**
 *
 * @author Antti
 */
public class Koordinaatit {

    private int[] koordinaatit;

    public Koordinaatit(int... koordinaatit) {
        muuta(koordinaatit);
    }

    public Koordinaatit() {
    }

    public void muuta(Koordinaatit koordinaatit) {
        muuta(koordinaatit.koordinaatit);
    }

    public final void muuta(int[] koordinaatit) {
        if (this.koordinaatit == null || koordinaatit.length >= this.koordinaatit.length) {
            this.koordinaatit = koordinaatit;
            return;
        }
        System.arraycopy(koordinaatit, 0, this.koordinaatit, 0, koordinaatit.length);
    }

    public int getX() {
        return koordinaatit[0];
    }

    public int getY() {
        return koordinaatit[1];
    }

    public int getZ() {
        return koordinaatit[2];
    }

    public void siirra(Koordinaatit koordinaatit) {
        siirra(koordinaatit.koordinaatit);
    }

    public void siirra(int... koordinaatit) {
        if (this.koordinaatit == null) {
            this.koordinaatit = new int[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatit.length) {
            System.arraycopy(this.koordinaatit, 0, new int[koordinaatit.length], 0, this.koordinaatit.length);
        }
        for (int n = 0; n < koordinaatit.length; n++) {
            this.koordinaatit[n] += koordinaatit.length;
        }
    }

    public double etaisyys(Koordinaatit koordinaatit) {
        return etaisyys(koordinaatit.koordinaatit);
    }

    public int etaisyysToiseen(Koordinaatit koordinaatit) {
        return etaisyysToiseen(koordinaatit.koordinaatit);
    }

    public double etaisyys(int[] koordinaatit) {
        return Math.sqrt(etaisyysToiseen(koordinaatit));
    }

    public int etaisyysToiseen(int[] koordinaatit) {
        if (this.koordinaatit == null) {
            this.koordinaatit = new int[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatit.length) {
            System.arraycopy(this.koordinaatit, 0, new int[koordinaatit.length], 0, this.koordinaatit.length);
        }
        int result = 0;
        for (int n = 0; n < koordinaatit.length; n++) {
            int temp = koordinaatit[n] - this.koordinaatit[n];
            result += temp * temp;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return 83 * 3 + Arrays.hashCode(this.koordinaatit);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.koordinaatit, ((Koordinaatit) o).koordinaatit);
    }
}
