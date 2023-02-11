package com.bluewhaleyt.component.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.bluewhaleyt.whaleutils.R;

public class ButtonPreference extends Preference {

    public ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setView();
    }

    public ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    public ButtonPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    public ButtonPreference(@NonNull Context context) {
        super(context);
        setView();
    }

    public void setView() {
//        setLayoutResource(R.layout.layout_preference_screen_item_1);
    }

}
