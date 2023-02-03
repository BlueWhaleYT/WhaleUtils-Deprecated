package com.bluewhaleyt.debug;

import android.os.Build;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassExtractUtil {

    private String className;
    private ArrayList<String> list = new ArrayList<>();

    private final int CLASS = 0;
    private final int CLASS_DECLARED = 1;
    private final int METHOD = 2;
    private final int METHOD_DECLARED = 3;
    private final int CONSTRUCTOR = 4;
    private final int CONSTRUCTOR_DECLARED = 5;
    private final int FIELD = 6;
    private final int FIELD_DECLARED = 7;

    public ClassExtractUtil(String className) {
        this.className = className;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getStringArray() {
        return Arrays.toString(list.toArray());
    }

    public Class<?> getClassName() throws ClassNotFoundException {
        return Class.forName(className);
    }

    public String getSimpleName() throws ClassNotFoundException {
        return getClassName().getSimpleName();
    }

    public String getCanonicalName() throws ClassNotFoundException {
        return getClassName().getCanonicalName();
    }

    public String getPackageName() throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return getClassName().getPackageName();
        }
        return null;
    }

    public String getTypeName() throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getClassName().getTypeName();
        }
        return null;
    }

    public ArrayList<String> getClasses() throws ClassNotFoundException {
        extract(CLASS);
        return list;
    }

    public ArrayList<String> getDeclaredClasses() throws ClassNotFoundException {
        extract(CLASS_DECLARED);
        return list;
    }

    public ArrayList<String> getMethods() throws ClassNotFoundException {
        extract(METHOD);
        return list;
    }

    public ArrayList<String> getDeclaredMethods() throws ClassNotFoundException {
        extract(METHOD_DECLARED);
        return list;
    }

    public ArrayList<String> getFields() throws ClassNotFoundException {
        extract(FIELD);
        return list;
    }

    public ArrayList<String> getDeclaredFields() throws ClassNotFoundException {
        extract(FIELD_DECLARED);
        return list;
    }

    private void extract(int cls) throws ClassNotFoundException {
        switch (cls) {
            case CLASS:
                for (Class<?> m : getClassName().getClasses()) {
                    list.add(m.getName());
                }
                break;
            case CLASS_DECLARED:
                for (Class<?> m : getClassName().getDeclaredClasses()) {
                    list.add(m.getName());
                }
                break;
            case METHOD:
                for (Method m : getClassName().getMethods()) {
                    list.add(m.getName());
                }
                break;
            case METHOD_DECLARED:
                for (Method m : getClassName().getDeclaredMethods()) {
                    list.add(m.getName());
                }
                break;
            case CONSTRUCTOR:
                for (Constructor<?> m : getClassName().getConstructors()) {
                    list.add(m.getName());
                }
                break;
            case CONSTRUCTOR_DECLARED:
                for (Constructor<?> m : getClassName().getDeclaredConstructors()) {
                    list.add(m.getName());
                }
                break;
            case FIELD:
                for (Field m : getClassName().getFields()) {
                    list.add(m.getName());
                }
                break;
            case FIELD_DECLARED:
                for (Field m : getClassName().getDeclaredFields()) {
                    list.add(m.getName());
                }
                break;
        }
    }

}
