package org.openhab.binding.windhagerbiowin.helper;

public class WindhagerBioWinHelper {

    public static int ggt(int[] zahlen) {
        if (zahlen.length < 2)
            return 0;

        int a = zahlen[0];
        int b = zahlen[1];
        int ggt_res = ggt(a, b);

        for (int i = 2; i < zahlen.length; i++) {
            ggt_res = ggt(ggt_res, zahlen[i]);
        }

        return ggt_res;
    }

    public static int ggt(int a, int b) {
        if (a == 0) {
            return b;
        }

        if (b == 0) {
            return a;
        }

        return ggt(b % a, a);
    }
}
