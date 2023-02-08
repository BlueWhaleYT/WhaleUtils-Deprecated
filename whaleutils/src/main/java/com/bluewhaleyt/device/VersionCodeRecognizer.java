package com.bluewhaleyt.device;

import java.util.Locale;

public class VersionCodeRecognizer {

    public static String getVersionCode(int sdkVersion) {
        var s = "";
        switch (sdkVersion) {
            case 1:
                s = "BASE"; // Android 1.0
                break;
            case 2:
                s = "BASE_1_1"; // Android 1.1
                break;
            case 3:
                s = "CUPCAKE"; // Android 1.5
                break;
            case 4:
                s = "DONUT"; // Android 1.6
                break;
            case 5:
                s = "ECLAIR"; // Android 2.0
                break;
            case 6:
                s = "ECLAIR_0_1"; // Android 2.0.1
                break;
            case 7:
                s = "ECLAIR_MR1"; // Android 2.1
                break;
            case 8:
                s = "FROYO"; // Android 2.2
                break;
            case 9:
                s = "GINGERBREAD"; // Android 2.3
                break;
            case 10:
                s = "GINGERBREAD_MR1"; // Android 2.3.3
                break;
            case 11:
                s = "HONEYCOMB"; // Android 3.0
                break;
            case 12:
                s = "HONEYCOMB_MR1"; // Android 3.1
                break;
            case 13:
                s = "HONEYCOMB_MR2"; // Android 3.2
                break;
            case 14:
                s = "ICE_CREAM_SANDWICH"; // Android 4.0
                break;
            case 15:
                s = "ICE_CREAM_SANDWICH_MR1"; // Android 4.03
                break;
            case 16:
                s = "JELLY_BEAN"; // Android 4.1
                break;
            case 17:
                s = "JELLY_BEAN_MR1"; // Android 4.2
                break;
            case 18:
                s = "JELLY_BEAN_MR2"; // Android 4.3
                break;
            case 19:
                s = "KITKAT"; // Android 4.4
                break;
            case 20:
                s = "KITKAT_WATCH"; // Android 4.4W
            case 21:
                s = "LOLLIPOP"; // Android 5.0
                break;
            case 22:
                s = "LOLLIPOP_MR1"; // Android 5.1
                break;
            case 23:
                s = "M"; // Android 6.0
                break;
            case 24:
                s = "N"; // Android 7.0
                break;
            case 25:
                s = "N_MR1"; // Android 7.1
                break;
            case 26:
                s = "O"; // Android 8.0
                break;
            case 27:
                s = "O_MR1"; // Android 8.1
                break;
            case 28:
                s = "P"; // Android 9
                break;
            case 29:
                s = "Q"; // Android 10
                break;
            case 30:
                s = "R"; // Android 11
                break;
            case 31:
                s = "S"; // Android 12
                break;
            case 32:
                s = "S_V2"; // Android 12L
                break;
            case 33:
                s = "TIRAMISU"; // Android 13
                break;
            default:
                s = "unknown";
        }
        return s.toUpperCase(Locale.ROOT);
    }

}
