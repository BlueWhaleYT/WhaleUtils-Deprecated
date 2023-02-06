package com.bluewhaleyt.filemanagement;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<String> {
    @Override
    public int compare(String file1, String file2) {
        File o1 = new File(file1);
        File o2 = new File(file2);
        if (o1.isDirectory() && o2.isDirectory()) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        } else if (o1.isDirectory() && !o2.isDirectory()) {
            return -1;
        } else if (!o1.isDirectory() && o2.isDirectory()) {
            return 1;
        } else {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }
}