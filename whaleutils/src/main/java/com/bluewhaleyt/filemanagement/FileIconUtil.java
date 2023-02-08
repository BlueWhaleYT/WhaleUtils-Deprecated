/**
 * Material File Icons are provided from https://github.com/PKief/vscode-material-icon-theme.
 */

package com.bluewhaleyt.filemanagement;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.R;

public class FileIconUtil {

    private ImageView imageView;
    private int fileIcon;

    public FileIconUtil(Context context, ImageView imageView, String path) {
        this.imageView = imageView;
        set(path);
        setFileIcon(imageView, fileIcon);
    }

    public void setFileIconColorForAll(int color) {
        imageView.setColorFilter(color);
    }

    private void set(String path) {

        if (FileUtil.isDirectory(path)) {
            var fileName = FileUtil.getFileNameOfPath(path);
            if (FileUtil.isFileHidden(fileName)) {
                fileIcon = R.drawable.ic_material_folder_hidden;
            } else {
                fileIcon = R.drawable.ic_material_folder;
            }
        } else {

            switch (FileUtil.getFileExtensionOfPath(path.toLowerCase())) {

                // compress files
                case "7z":
                case "rar":
                case "tar":
                case "xz":
                case "zip":
                    fileIcon = R.drawable.ic_material_zip;
                    break;

                // image files
                case "bmp":
                case "jpg":
                case "jpeg":
                case "png":
                case "tiff":
                case "webp":
                    fileIcon = R.drawable.ic_material_image;
                    break;
                case "ai":
                case "swf":
                case "svg":
                    fileIcon = R.drawable.ic_material_svg;
                    break;

                // font files
                case "otf":
                case "ttc":
                case "ttf":
                    fileIcon = R.drawable.ic_material_font;
                    break;

                // text files
                case "css":
                    fileIcon = R.drawable.ic_material_css;
                    break;
                case "gradle":
                    fileIcon = R.drawable.ic_material_gradle;
                    break;
                case "htm":
                case "html":
                    fileIcon = R.drawable.ic_material_html;
                    break;
                case "java":
                    fileIcon = R.drawable.ic_material_java;
                    break;
                case "js":
                    fileIcon = R.drawable.ic_material_javascript;
                    break;
                case "json":
                    fileIcon = R.drawable.ic_material_json;
                    break;
                case "kt":
                    fileIcon = R.drawable.ic_material_kotlin;
                    break;
                case "less":
                    fileIcon = R.drawable.ic_material_less;
                    break;
                case "log":
                    fileIcon = R.drawable.ic_material_log;
                    break;
                case "md":
                    fileIcon = R.drawable.ic_material_markdown;
                    break;
                case "pdf":
                    fileIcon = R.drawable.ic_material_pdf;
                    break;
                case "php":
                    fileIcon = R.drawable.ic_material_php;
                    break;
                case "pug":
                    fileIcon = R.drawable.ic_material_pug;
                    break;
                case "py":
                    fileIcon = R.drawable.ic_material_python;
                    break;
                case "sass":
                    fileIcon = R.drawable.ic_material_sass;
                    break;
                case "xml":
                    fileIcon = R.drawable.ic_material_xml;
                    break;
                case "yaml":
                    fileIcon = R.drawable.ic_material_yaml;
                    break;

                default:
                    fileIcon = R.drawable.ic_material_document;

            }

        }

    }

    private void setFileIcon(ImageView imageView, int fileIcon) {
        imageView.setImageResource(fileIcon);
    }

}
