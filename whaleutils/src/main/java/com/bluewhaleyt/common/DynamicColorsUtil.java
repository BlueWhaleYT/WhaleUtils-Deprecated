package com.bluewhaleyt.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.bluewhaleyt.whaleutils.R;
import com.google.android.material.color.DynamicColors;

public class DynamicColorsUtil {

    TypedArray typedArray;

    int
    primary, onPrimary, primaryContainer, onPrimaryContainer,
    secondary, onSecondary, secondaryContainer, onSecondaryContainer,
    tertiary, onTertiary, tertiaryContainer, onTertiaryContainer,
    error, onError, errorContainer, onErrorContainer,
    background, onBackground,
    surface, onSurface,
    surfaceVariant, onSurfaceVariant,
    outline, outlineVariant;

    @SuppressLint("ResourceType")
    public DynamicColorsUtil(Context context) {
        if (DynamicColors.isDynamicColorAvailable()) {
            Context dynamicColorContext = DynamicColors.wrapContextIfAvailable(context);
            int[] attrsToResolve = {
                    com.google.android.material.R.attr.colorPrimary,
                    com.google.android.material.R.attr.colorOnPrimary,
                    com.google.android.material.R.attr.colorPrimaryContainer,
                    com.google.android.material.R.attr.colorOnPrimaryContainer,

                    com.google.android.material.R.attr.colorSecondary,
                    com.google.android.material.R.attr.colorOnSecondary,
                    com.google.android.material.R.attr.colorSecondaryContainer,
                    com.google.android.material.R.attr.colorOnSecondaryContainer,

                    com.google.android.material.R.attr.colorTertiary,
                    com.google.android.material.R.attr.colorOnTertiary,
                    com.google.android.material.R.attr.colorTertiaryContainer,
                    com.google.android.material.R.attr.colorOnTertiaryContainer,

                    com.google.android.material.R.attr.colorError,
                    com.google.android.material.R.attr.colorOnError,
                    com.google.android.material.R.attr.colorErrorContainer,
                    com.google.android.material.R.attr.colorOnErrorContainer,

                    com.google.android.material.R.attr.backgroundColor,
                    com.google.android.material.R.attr.colorOnBackground,
                    com.google.android.material.R.attr.colorSurface,
                    com.google.android.material.R.attr.colorOnSurface,
                    com.google.android.material.R.attr.colorSurfaceVariant,
                    com.google.android.material.R.attr.colorOnSurfaceVariant,
                    com.google.android.material.R.attr.colorOutline,
                    com.google.android.material.R.attr.colorOutlineVariant,
            };

            typedArray = dynamicColorContext.obtainStyledAttributes(attrsToResolve);

            primary = typedArray.getColor(0, 0);
            onPrimary = typedArray.getColor(1, 0);
            primaryContainer = typedArray.getColor(2, 0);
            onPrimaryContainer = typedArray.getColor(3, 0);

            secondary = typedArray.getColor(4, 0);
            onSecondary = typedArray.getColor(5, 0);
            secondaryContainer = typedArray.getColor(6, 0);
            onSecondaryContainer = typedArray.getColor(7, 0);

            tertiary = typedArray.getColor(8, 0);
            onTertiary = typedArray.getColor(9, 0);
            tertiaryContainer = typedArray.getColor(10, 0);
            onTertiaryContainer = typedArray.getColor(11, 0);

            error = typedArray.getColor(12, 0);
            onError = typedArray.getColor(13, 0);
            errorContainer = typedArray.getColor(14, 0);
            onErrorContainer = typedArray.getColor(15, 0);

            background = typedArray.getColor(16, 0);
            onBackground = typedArray.getColor(17, 0);
            surface = typedArray.getColor(18, 0);
            onSurface = typedArray.getColor(19, 0);
            surfaceVariant = typedArray.getColor(20, 0);
            onSurfaceVariant = typedArray.getColor(21, 0);
            outline = typedArray.getColor(22, 0);
            outlineVariant = typedArray.getColor(23, 0);

            typedArray.recycle();
        } else {
            if (isDarkMode(context)) {

                primary = ContextCompat.getColor(context, R.color.md_theme_dark_primary);
                onPrimary = ContextCompat.getColor(context, R.color.md_theme_dark_onPrimary);
                primaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_primaryContainer);
                onPrimaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_onPrimaryContainer);

                secondary = ContextCompat.getColor(context, R.color.md_theme_dark_secondary);
                onSecondary = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondary);
                secondaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_secondaryContainer);
                onSecondaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_onSecondaryContainer);

                tertiary = ContextCompat.getColor(context, R.color.md_theme_dark_tertiary);
                onTertiary = ContextCompat.getColor(context, R.color.md_theme_dark_onTertiary);
                tertiaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_tertiaryContainer);
                onTertiaryContainer = ContextCompat.getColor(context, R.color.md_theme_dark_onTertiaryContainer);

                error = ContextCompat.getColor(context, R.color.md_theme_dark_error);
                onError = ContextCompat.getColor(context, R.color.md_theme_dark_onError);
                errorContainer = ContextCompat.getColor(context, R.color.md_theme_dark_errorContainer);
                onErrorContainer = ContextCompat.getColor(context, R.color.md_theme_dark_onErrorContainer);

                background = ContextCompat.getColor(context, R.color.md_theme_dark_background);
                onBackground = ContextCompat.getColor(context, R.color.md_theme_dark_onBackground);
                surface = ContextCompat.getColor(context, R.color.md_theme_dark_surface);
                onSurface = ContextCompat.getColor(context, R.color.md_theme_dark_onSurface);
                surfaceVariant = ContextCompat.getColor(context, R.color.md_theme_dark_surfaceVariant);
                onSurfaceVariant = ContextCompat.getColor(context, R.color.md_theme_dark_onSurfaceVariant);
                outline = ContextCompat.getColor(context, R.color.md_theme_dark_outline);
                outlineVariant = ContextCompat.getColor(context, R.color.md_theme_dark_outlineVariant);

            } else {

                primary = ContextCompat.getColor(context, R.color.md_theme_light_primary);
                onPrimary = ContextCompat.getColor(context, R.color.md_theme_light_onPrimary);
                primaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_primaryContainer);
                onPrimaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_onPrimaryContainer);

                secondary = ContextCompat.getColor(context, R.color.md_theme_light_secondary);
                onSecondary = ContextCompat.getColor(context, R.color.md_theme_light_onSecondary);
                secondaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_secondaryContainer);
                onSecondaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_onSecondaryContainer);

                tertiary = ContextCompat.getColor(context, R.color.md_theme_light_tertiary);
                onTertiary = ContextCompat.getColor(context, R.color.md_theme_light_onTertiary);
                tertiaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_tertiaryContainer);
                onTertiaryContainer = ContextCompat.getColor(context, R.color.md_theme_light_onTertiaryContainer);

                error = ContextCompat.getColor(context, R.color.md_theme_light_error);
                onError = ContextCompat.getColor(context, R.color.md_theme_light_onError);
                errorContainer = ContextCompat.getColor(context, R.color.md_theme_light_errorContainer);
                onErrorContainer = ContextCompat.getColor(context, R.color.md_theme_light_onErrorContainer);

                background = ContextCompat.getColor(context, R.color.md_theme_light_background);
                onBackground = ContextCompat.getColor(context, R.color.md_theme_light_onBackground);
                surface = ContextCompat.getColor(context, R.color.md_theme_light_surface);
                onSurface = ContextCompat.getColor(context, R.color.md_theme_light_onSurface);
                surfaceVariant = ContextCompat.getColor(context, R.color.md_theme_light_surfaceVariant);
                onSurfaceVariant = ContextCompat.getColor(context, R.color.md_theme_light_onSurfaceVariant);
                outline = ContextCompat.getColor(context, R.color.md_theme_light_outline);
                outlineVariant = ContextCompat.getColor(context, R.color.md_theme_light_outlineVariant);

            }
        }
    }

    public int getColorPrimary() { return primary; }

    public int getColorOnPrimary() { return onPrimary; }

    public int getColorPrimaryContainer() { return primaryContainer; }

    public int getColorOnPrimaryContainer() { return onPrimaryContainer; }

    public int getColorSecondary() { return secondary; }

    public int getColorOnSecondary() { return onSecondary; }

    public int getColorSecondaryContainer() { return secondaryContainer; }

    public int getColorOnSecondaryContainer() { return onSecondaryContainer; }

    public int getColorTertiary() { return tertiary; }

    public int getColorOnTertiary() { return onTertiary; }

    public int getColorTertiaryContainer() { return tertiaryContainer; }

    public int getColorOnTertiaryContainer() { return onTertiaryContainer; }

    public int getColorError() { return error; }

    public int getColorOnError() { return onError; }

    public int getColorErrorContainer() { return errorContainer; }

    public int getColorOnErrorContainer() { return onErrorContainer; }

    public int getColorBackground() { return background; }

    public int getColorOnBackground() { return onBackground; }

    public int getColorSurface() { return surface; }

    public int getColorOnSurface() { return onSurface; }

    public int getColorSurfaceVariant() { return surfaceVariant; }

    public int getColorOnSurfaceVariant() { return onSurfaceVariant; }

    public int getColorOutline() { return outline; }

    public int getColorOutlineVariant() { return outlineVariant; }

    public static boolean isDarkMode(Context context) {
        return CommonUtil.isInDarkMode(context);
    }

    public static boolean isDynamicColorAvailable() {
        return SDKUtil.isAtLeastSDK31() && DynamicColors.isDynamicColorAvailable();
    }

    public static void setDynamicColorsIfAvailable(Application application) {
        if (isDynamicColorAvailable())
            DynamicColors.applyToActivitiesIfAvailable(application);
    }

}
