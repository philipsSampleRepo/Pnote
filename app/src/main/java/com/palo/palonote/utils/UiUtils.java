package com.palo.palonote.utils;

import android.widget.Toast;

import com.palo.palonote.R;
import com.palo.palonote.ui.BaseActivity;

public class UiUtils {

    private static UiUtils uiUtils = null;

    private UiUtils() {
    }

    public static UiUtils getInstance() {
        if (uiUtils == null) {
            synchronized (UiUtils.class) {
                if (uiUtils == null) {
                    uiUtils = new UiUtils();
                }
            }
        }
        return uiUtils;
    }

    public void showToast(BaseActivity baseActivity) {
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(baseActivity, R.string.success_msg,
                        Toast.LENGTH_LONG).show();
                baseActivity.finish();
            }
        });
    }
}