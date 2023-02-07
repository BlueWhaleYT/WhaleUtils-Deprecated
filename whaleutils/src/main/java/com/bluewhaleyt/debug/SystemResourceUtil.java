package com.bluewhaleyt.debug;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SystemResourceUtil {

    private static ClassExtractUtil classExtract;

    public static int getParsedXMLResource(Context context, String identifierName, String defType) throws ClassNotFoundException {
        return context.getResources().getIdentifier(identifierName, defType, classExtract.getPackageName());
    }

    public static List<String> getAvailableResources(Context context) throws ClassNotFoundException {
        classExtract = new ClassExtractUtil(context.getPackageName() + ".R");
        classExtract.getClasses();
        return classExtract.getList();
    }

    public static List<String> getAnimResources(Context context) throws ClassNotFoundException {
        extract(context, "anim");
        return classExtract.getList();
    }

    public static List<String> getAnimatorResources(Context context) throws ClassNotFoundException {
        extract(context, "animator");
        return classExtract.getList();
    }

    public static List<String> getArrayResources(Context context) throws ClassNotFoundException {
        extract(context, "array");
        return classExtract.getList();
    }

    public static List<String> getAttrResources(Context context) throws ClassNotFoundException {
        extract(context, "attr");
        return classExtract.getList();
    }

    public static List<String> getBoolResources(Context context) throws ClassNotFoundException {
        extract(context, "bool");
        return classExtract.getList();
    }

    public static List<String> getColorResources(Context context) throws ClassNotFoundException {
        extract(context, "color");
        return classExtract.getList();
    }

    public static List<String> getDimenResources(Context context) throws ClassNotFoundException {
        extract(context, "dimen");
        return classExtract.getList();
    }

    public static List<String> getDrawableResources(Context context) throws ClassNotFoundException {
        extract(context, "drawable");
        return classExtract.getList();
    }

    public static List<String> getFontResources(Context context) throws ClassNotFoundException {
        extract(context, "font");
        return classExtract.getList();
    }

    public static List<String> getIdResources(Context context) throws ClassNotFoundException {
        extract(context, "id");
        return classExtract.getList();
    }

    public static List<String> getIntegerResources(Context context) throws ClassNotFoundException {
        extract(context, "integer");
        return classExtract.getList();
    }

    public static List<String> getInterpolatorResources(Context context) throws ClassNotFoundException {
        extract(context, "interpolator");
        return classExtract.getList();
    }

    public static List<String> getLayoutResources(Context context) throws ClassNotFoundException {
        extract(context, "layout");
        return classExtract.getList();
    }

    public static List<String> getMenuResources(Context context) throws ClassNotFoundException {
        extract(context, "menu");
        return classExtract.getList();
    }

    public static List<String> getMipmapResources(Context context) throws ClassNotFoundException {
        extract(context, "mipmap");
        return classExtract.getList();
    }

    public static List<String> getPluralsResources(Context context) throws ClassNotFoundException {
        extract(context, "plurals");
        return classExtract.getList();
    }

    public static List<String> getStringResources(Context context) throws ClassNotFoundException {
        extract(context, "string");
        return classExtract.getList();
    }

    public static List<String> getStyleResources(Context context) throws ClassNotFoundException {
        extract(context, "style");
        return classExtract.getList();
    }

    public static List<String> getStyleableResources(Context context) throws ClassNotFoundException {
        extract(context, "styleable");
        return classExtract.getList();
    }

    public static List<String> getXmlResources(Context context) throws ClassNotFoundException {
        extract(context, "xml");
        return classExtract.getList();
    }

    private static void extract(Context context, String type) throws ClassNotFoundException {
        classExtract = new ClassExtractUtil(context.getPackageName() + ".R$" + type);
        classExtract.getFields();
    }

}
