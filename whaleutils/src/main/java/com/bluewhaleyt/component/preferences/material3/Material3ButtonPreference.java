package com.bluewhaleyt.component.preferences.material3;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bluewhaleyt.component.preferences.ButtonPreference;
import com.bluewhaleyt.whaleutils.R;

public class Material3ButtonPreference extends ButtonPreference {
    public Material3ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setView();
    }

    public Material3ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    public Material3ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    public Material3ButtonPreference(@NonNull Context context) {
        super(context);
        setView();
    }

    public void setView() {
        setLayoutResource(R.layout.layout_preference_material3_button);
    }

}
