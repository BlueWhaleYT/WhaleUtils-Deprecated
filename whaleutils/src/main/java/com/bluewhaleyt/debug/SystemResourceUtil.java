package com.bluewhaleyt.debug;

import android.content.Context;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SystemResourceUtil {

    private static ClassExtractUtil classExtract;

    private static boolean listWithChild = true;

    public static ArrayList<String> getAvailableResources(Context context) throws ClassNotFoundException {
        classExtract = new ClassExtractUtil(context.getPackageName() + ".R");
        classExtract.getClasses();
        return classExtract.getList();
    }

    public static ArrayList<String> getIdResources(Context context) throws ClassNotFoundException {
        extract(context, "id");
        return classExtract.getList();
    }

    public static ArrayList<String> getStringResources(Context context) throws ClassNotFoundException {
        extract(context, "string");
        return classExtract.getList();
    }

    public static ArrayList<String> getColorResources(Context context) throws ClassNotFoundException {
        extract(context, "color");
        return classExtract.getList();
    }

    public static ArrayList<String> getArrayResources(Context context) throws ClassNotFoundException {
        extract(context, "array");
        return classExtract.getList();
    }

    public static ArrayList<String> getLayoutResources(Context context) throws ClassNotFoundException {
        extract(context, "layout");
        return classExtract.getList();
    }

    public static ArrayList<String> getDrawableResources(Context context) throws ClassNotFoundException {
        extract(context, "drawable");
        return classExtract.getList();
    }

    public static ArrayList<String> getMipmapResources(Context context) throws ClassNotFoundException {
        extract(context, "mipmap");
        return classExtract.getList();
    }

    public static ArrayList<String> getDimenResources(Context context) throws ClassNotFoundException {
        extract(context, "dimen");
        return classExtract.getList();
    }

    public static ArrayList<String> getStyleResources(Context context) throws ClassNotFoundException {
        extract(context, "style");
        return classExtract.getList();
    }

    public static ArrayList<String> getXmlResources(Context context) throws ClassNotFoundException {
        extract(context, "xml");
        return classExtract.getList();
    }

    public static ArrayList<String> getMenuResources(Context context) throws ClassNotFoundException {
        extract(context, "menu");
        return classExtract.getList();
    }

    private static void extract(Context context, String type) throws ClassNotFoundException {
        classExtract = new ClassExtractUtil(context.getPackageName() + ".R$" + type);
        classExtract.getFields();
    }

}
