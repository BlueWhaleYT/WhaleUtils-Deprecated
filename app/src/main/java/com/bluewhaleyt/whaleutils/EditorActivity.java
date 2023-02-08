package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bluewhaleyt.codeeditor.textmate.syntaxhighlight.SyntaxHighlightUtil;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileIconUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.databinding.ActivityEditorBinding;
import com.bluewhaleyt.whaleutils.databinding.ActivityFileManagerBinding;

import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;

public class EditorActivity extends AppCompatActivity {

    private ActivityEditorBinding binding;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashDebugger.init(this);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_save_file:
                saveFile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataFromFile() {
        filePath = IntentUtil.intentGetString(this, "file_path");
    }

    private void init() {

        CommonUtil.setStatusBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_DEFAULT_TOOLBAR);
        CommonUtil.setNavigationBarColorWithSurface(this, CommonUtil.SURFACE_FOLLOW_WINDOW_BACKGROUND);

        try {
            getDataFromFile();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setTitle(FileUtil.getFileNameOfPath(filePath));
            getSupportActionBar().setSubtitle(filePath);

            setupEditor();
            setupSyntaxHighlight();
        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage(), e.toString());
        }

    }

    private void setupEditor() {

        var margin = 30;
        var editor = binding.editor;
        var font = Typeface.createFromAsset(getAssets(), "fonts/JetBrainsMono-Medium.ttf");

        editor.setText(FileUtil.readFile(filePath));

        editor.setTypefaceText(font);
        editor.setTypefaceLineNumber(font);
        editor.setLineSpacing(2f, 1.5f);

        editor.setLineNumberMarginLeft(margin);
        editor.setDividerMargin(margin);

        editor.getComponent(EditorAutoCompletion.class).setEnabled(false);

    }

    private void setupSyntaxHighlight() {

        String themeDir = "textmate/themes/";
        String languageDir = "textmate/languages/";

        String themeDark = "material_custom.json";
        String themeLight = "material_lighter.json";

        String[] themes = {themeLight, themeDark};

        try {
            SyntaxHighlightUtil syntaxHighlight = new SyntaxHighlightUtil();
            syntaxHighlight.setLanguageBase("languages.json");
            syntaxHighlight.setLanguageDirectory(languageDir);
            syntaxHighlight.setThemeDirectory(themeDir);
            syntaxHighlight.setThemes(themes);

            var theme = CommonUtil.isInDarkMode(this) ? themeDark : themeLight;
            syntaxHighlight.setTheme(theme);

            syntaxHighlight.setup(this, binding.editor, filePath);
        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage(), e.toString());
        }

    }

    private void saveFile() {

        if (FileUtil.isFileExist(filePath)) {
            FileUtil.writeFile(filePath, binding.editor.getText().toString());
        }

    }

}