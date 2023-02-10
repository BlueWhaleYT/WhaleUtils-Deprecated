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

/**
 * This class is to simplify the configuration of applying TextMate for Sora Editor library.
 */

public class SyntaxHighlightUtil {

    private String[] themeFiles;
    private String lang, theme, themeDir, languageDir, languageBasePath;

    public SyntaxHighlightUtil() {

    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setThemes(String[] themeFiles) {
        this.themeFiles = themeFiles;
    }

    public void setThemeDirectory(String themeDir) {
        this.themeDir = themeDir;
    }

    public void setLanguageDirectory(String languageDir) {
        this.languageDir = languageDir;
    }

    public void setLanguageBase(String path) {
        this.languageBasePath = path;
    }

    public void setup(Context context, CodeEditor editor, String path) throws Exception {
        setupHighlight(context, editor, path);
    }

    private void setupHighlight(Context context, CodeEditor editor, String path) throws Exception {

        switch (FileUtil.getFileExtensionOfPath(path.toLowerCase())) {

            case "css":
                lang = "source.css";
                break;
            case "htm":
            case "html":
                lang = "text.html.basic";
                break;
            case "kt":
                lang = "source.kotlin";
                break;
            case "md":
                lang = "text.html.markdown";
                break;
            case "java":
                lang = "source.java";
                break;
            case "js":
                lang = "source.js";
                break;
            case "json":
                lang = "source.json";
                break;
            case "log":
                lang = "text.log";
                break;
            case "php":
                lang = "source.php";
                break;
            case "xml":
                lang = "text.xml";
                break;
            case "txt":
            default:
                lang = "";
        }

        loadDefaultThemes(context);
        loadDefaultLanguages();

        ensureTextmateTheme(editor);

        applyThemes();
        if (!lang.equals("")) {
            applyLanguages(editor);
        }

    }

    private void loadDefaultThemes(Context ctx) throws Exception {
        FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(ctx.getAssets()));
        var themeRegistry = ThemeRegistry.getInstance();
        for (int i = 0; i < themeFiles.length; ) {
            var path = themeDir + themeFiles[i];
            themeRegistry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(FileProviderRegistry.getInstance().tryGetInputStream(path), path, null), path));
            i++;
        }
    }

    private void loadDefaultLanguages() {
        GrammarRegistry.getInstance().loadGrammars(languageDir + languageBasePath);
    }

    private void ensureTextmateTheme(CodeEditor editor) throws Exception {
        var editorColorScheme = editor.getColorScheme();
        if (!(editorColorScheme instanceof TextMateColorScheme)) {
            editorColorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance());
            editor.setColorScheme(editorColorScheme);
        }
    }

    private void applyThemes() {
        ThemeRegistry.getInstance().setTheme(themeDir + theme);
    }

    private void applyLanguages(CodeEditor editor) throws Exception {
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

}

