package com.bluewhaleyt.component.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.bluewhaleyt.common.DynamicColorsUtil;

public class CustomPreference extends Preference {
    public CustomPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPreference(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        DynamicColorsUtil dynamicColors = new DynamicColorsUtil(holder.itemView.getContext());

        ImageView iconView = (ImageView) holder.itemView.findViewById(android.R.id.icon);
        iconView.setColorFilter(dynamicColors.getColorPrimary());
    }
}
