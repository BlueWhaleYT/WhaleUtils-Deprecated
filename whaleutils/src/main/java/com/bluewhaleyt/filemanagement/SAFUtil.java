package com.bluewhaleyt.filemanagement;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.documentfile.provider.DocumentFile;

import com.anggrayudi.storage.file.DocumentFileCompat;
import com.anggrayudi.storage.file.DocumentFileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SAFUtil extends FileUtil {

    public static final String DIRECTORY_URI = "directoryUri";
    public static final String DIRECT_DIRECTORY_URI = "directDirectoryUri";

    public static final String MAP_FILE_DOC_ID = "fileDocId";
    public static final String MAP_FILE_NAME = "fileName";
    public static final String MAP_FILE_MIME = "fileMime";
    public static final String MAP_FILE_SIZE = "fileSize";
    public static final String MAP_FILE_LAST_MODIFIED_TIME = "fileLastModifiedTime";

    public static boolean isDirectory(String mimeType) {
        return DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType);
    }

    public static Boolean listDirectories(Context context, List<HashMap<String, Object>> list, Uri uri, String str) throws IOException {
        list.clear();
        HashMap<String, Object> map;
        ContentResolver contentResolver = context.getContentResolver();

        Uri childrenUri;
        if (str == null) childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
        else childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, str);

        Cursor childCursor = contentResolver.query(childrenUri,
                new String[] {
                        DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                        DocumentsContract.Document.COLUMN_MIME_TYPE,
                        DocumentsContract.Document.COLUMN_SIZE,
                        DocumentsContract.Document.COLUMN_LAST_MODIFIED
                }, null, null, null
        );

        try {
            while (childCursor.moveToNext()) {
                final String docId = childCursor.getString(0);
                final String name = childCursor.getString(1);
                final String mime = childCursor.getString(2);
                final String size = childCursor.getString(3);
                final String lastModified = childCursor.getString(4);

                map = new HashMap<>();
                map.put(MAP_FILE_DOC_ID, docId);
                map.put(MAP_FILE_NAME, name);
                map.put(MAP_FILE_MIME, mime);
                map.put(MAP_FILE_SIZE, size);
                map.put(MAP_FILE_LAST_MODIFIED_TIME, lastModified);

                list.add(map);

            }
        } finally {
            closeAllResources(childCursor);
        }

        return true;

    }

    public static void closeAllResources(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

}
