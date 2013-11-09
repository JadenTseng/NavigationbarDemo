package com.cheney.android.common;

public class T {
    public static int ABANDON = 0;

    public static int CARRY = 1;

    public static int getInt(double d, int require) {
        int i = 0;
        if (ABANDON == require) {
            i = (int) d;
        } else if (CARRY == require) {
            int ii = (int) d;
            if (ii != 0) {
                i = ii + ((d % ii > 0.0D) ? 1 : 0);
            } else if (d == 0.0D)
                i = 0;
            else {
                i = 1;
            }

        }

        return i;
    }

}
