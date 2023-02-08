package com.bluewhaleyt.unit;

public class UnitUtil {

    private static String floatForm(double d) {
        return String.format(java.util.Locale.US, "%.2f", d);
    }

    public static String byteHumanize(double size) {
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + " B";
        if (size >= Kb && size < Mb) return floatForm(size / Kb) + " KB";
        if (size >= Mb && size < Gb) return floatForm(size / Mb) + " MB";
        if (size >= Gb && size < Tb) return floatForm(size / Gb) + " GB";
        if (size >= Tb && size < Pb) return floatForm(size / Tb) + " TB";
        if (size >= Pb && size < Eb) return floatForm(size / Pb) + " Pb";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "0";
    }

}
