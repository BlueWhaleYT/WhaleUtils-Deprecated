package com.bluewhaleyt.codeeditor.textmate.syntaxhighlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.filemanagement.FileUtil;

import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class SyntaxHighlightUtil implements TextWatcher {

    static String tmFileType = "";
    static String tmFileRoot = "textmate/";
    static String tmLanguageBase = "languages.json";

    static String filePath = "";
    static String fileType = "";
    static int fileIcon;

    public static String lang = "";
    static String nonJson = "";
    static String plist = "";

    static SharedPreferences sp;

    public static void set(Context context, CodeEditor editor, String str) throws Exception {

        // check file type / extension
        switch (FileUtil.getFileExtensionOfPath(str.toLowerCase())) {

            // TEXT FILES
            case "css": // CSS
                lang = "source.css";
                break;
            case "htm": // HTML
            case "html":
                lang = "text.html.basic";
                break;
            case "kt": // Kotlin
                break;
            case "md": // Markdown
                lang = "text.html.markdown";
                break;
            case "java": // Java
                lang = "source.java";
                break;
            case "js": // JavaScript
                lang = "source.js";
                break;
            case "json": // JSON
                lang = "source.json";
                break;
            case "log": // Log
                lang = "text.log";
                break;
            case "php": // PHP
                lang = "source.php";
                break;
            case "xml": // XML
                break;
            case "txt": // TXT
            default:
                lang = "";
        }

        loadDefaultThemes(context);
        loadDefaultLanguages();

        ensureTextmateTheme(editor);

        if (!lang.equals("")) {
            applyLanguages(editor);
        }

//      refreshEditorInstantly(editor);

    }

    private static void loadDefaultThemes(Context ctx) {

        try {
            FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(ctx.getAssets()));

            String[] themes = {"material_palenight.json"};
            var themeRegistry = ThemeRegistry.getInstance();

            for (int j = 0; j < themes.length; ) {
                String name = themes[j];

                var path = "textmate/themes/" + name;
                themeRegistry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(FileProviderRegistry.getInstance().tryGetInputStream(path), path, null), name));
                j++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void loadDefaultLanguages() {
        String path = "textmate/languages/languages.json";
        GrammarRegistry.getInstance().loadGrammars(path);
    }

    private static void ensureTextmateTheme(CodeEditor editor) throws Exception {

        var editorColorScheme = editor.getColorScheme();
        if (!(editorColorScheme instanceof TextMateColorScheme)) {
            editorColorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance());
            editor.setColorScheme(editorColorScheme);
        }

    }

    private static void applyLanguages(CodeEditor editor) throws Exception {

        ensureTextmateTheme(editor);

        TextMateLanguage language;
        var editorLanguage = editor.getEditorLanguage();
        if (editorLanguage instanceof TextMateLanguage) {
            language = (TextMateLanguage) editorLanguage;
            language.updateLanguage(lang);
        } else {
            language = TextMateLanguage.create(lang, true);
        }
        editor.setEditorLanguage(language);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

