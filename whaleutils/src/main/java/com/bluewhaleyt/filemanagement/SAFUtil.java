package com.bluewhaleyt.filemanagement;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;

import androidx.documentfile.provider.DocumentFile;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;

public class SAFUtil {

    public static boolean isDirectory(DocumentFile documentFile) {
        return documentFile.isDirectory();
    }

    public static boolean isDirectory(String mimeType) {
        return DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType);
    }

    public static String treeToPath(String treePath) {
        return treePath.replace("%2F", "/");
    }

    public static ArrayList<HashMap<String, Object>> getListFiles(Context context, Uri uri, String str) {
        ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
        if (updateDirectoryEntries(context, listmap, uri, str)) {
            return listmap;
        }
        return listmap;
    }

    public static Boolean updateDirectoryEntries(Context context, ArrayList<HashMap<String, Object>> listmap, Uri uri, String str) {
        HashMap<String, Object> map = new HashMap<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri childrenUri;
        if (str == null) childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
        else childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, str);
        Cursor childCursor = contentResolver.query(childrenUri,
                new String[] {
                        DocumentsContract.Document.COLUMN_DOCUMENT_ID, DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE
                }, null, null, null);
        try {
            while (childCursor.moveToNext()) {
                final String docId = childCursor.getString(0);
                final String name = childCursor.getString(1);
                final String mime = childCursor.getString(2);
                map = new HashMap<>();
                map.put("docId", docId);
                map.put("name", name);
                map.put("mime", mime);
                listmap.add(map);
            }
        } finally {
            closeQuietly(childCursor);
        }
        return true;
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException re) {
                throw re;
            } catch (Exception e) {
            }
        }
    }

}
