package com.bluewhaleyt.component.preferences.material3;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.SwitchPreferenceCompat;

import com.bluewhaleyt.whaleutils.R;

public class Material3SwitchPreference extends SwitchPreferenceCompat {

    public Material3SwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setView();
    }

    public Material3SwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    public Material3SwitchPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    public Material3SwitchPreference(@NonNull Context context) {
        super(context);
        setView();
    }

    private void setView() {
        setWidgetLayoutResource(R.layout.layout_preference_material3_switch);
    }

}
