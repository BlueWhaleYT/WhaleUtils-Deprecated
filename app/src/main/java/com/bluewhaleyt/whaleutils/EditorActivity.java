package com.bluewhaleyt.whaleutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;

import com.bluewhaleyt.codeeditor.textmate.syntaxhighlight.SyntaxHighlightUtil;
import com.bluewhaleyt.common.CommonUtil;
import com.bluewhaleyt.common.IntentUtil;
import com.bluewhaleyt.component.snackbar.SnackbarUtil;
import com.bluewhaleyt.crashdebugger.CrashDebugger;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.databinding.ActivityEditorBinding;
import com.bluewhaleyt.whaleutils.databinding.ActivityFileManagerBinding;

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

        getDataFromFile();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(FileUtil.getFileNameOfPath(filePath));
        getSupportActionBar().setSubtitle(filePath);

        setupEditor();
        setupSyntaxHighlight();

    }

    private void setupEditor() {

        var editor = binding.editor;
        var font = Typeface.createFromAsset(getAssets(), "fonts/JetBrainsMono-Medium.ttf");

        editor.setText(FileUtil.readFile(filePath));

        editor.setTypefaceText(font);
        editor.setTypefaceLineNumber(font);
        editor.setLineSpacing(2f, 1.5f);

        editor.setLineNumberMarginLeft(40);
        editor.setDividerMargin(40);

    }

    private void setupSyntaxHighlight() {

        String THEME_DIR = "textmate/themes/";
        String LANGUAGE_DIR = "textmate/langauges/";

        String[] themes = {"material_lighter", "material_palenight"};

        try {

            SyntaxHighlightUtil.set(this, binding.editor, filePath);

        } catch (Exception e) {
            SnackbarUtil.makeErrorSnackbar(this, e.getMessage(), e.toString());
        }

    }

}