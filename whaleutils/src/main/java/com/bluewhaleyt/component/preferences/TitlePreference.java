package com.bluewhaleyt.component.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.bluewhaleyt.whaleutils.R;

public class TitlePreference extends Preference {

    public TitlePreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setView();
    }

    public TitlePreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    public TitlePreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    public TitlePreference(@NonNull Context context) {
        super(context);
        setView();
    }

    public void setView() {
        setLayoutResource(R.layout.layout_preference_screen_title_headline_large);
        setIconSpaceReserved(false);
        setSelectable(false);
    }

}
