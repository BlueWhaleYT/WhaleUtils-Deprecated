package com.bluewhaleyt.common;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bluewhaleyt.whaleutils.R;

public class AnimationUtil {

    public static void setIntentActivityEnterAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.swipe_enter_right_to_left, R.anim.swipe_exit_right_to_left);
    }

    public static void setIntentActivityExitAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.swipe_enter_left_to_right, R.anim.swipe_exit_left_to_right);
    }

    public static void setIntentFragmentEnterExitAnimation(FragmentActivity activity, Fragment fragment) {
        setFragmentAnimation(activity, fragment);
    }

    private static void setFragmentAnimation(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.swipe_enter_right_to_left, R.anim.swipe_exit_right_to_left,
                        R.anim.swipe_enter_left_to_right, R.anim.swipe_exit_left_to_right)
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}
