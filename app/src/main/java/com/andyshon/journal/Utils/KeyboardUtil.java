package com.andyshon.journal.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.reactivex.Single;

public final class KeyboardUtil {
    private KeyboardUtil(){}

    /**
     * Returns Observable that will receive true if the keyboard is closed
     */
    public static Single<Boolean> closeKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            return Single.create(s -> {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(null) {
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                            s.onSuccess(resultCode == InputMethodManager.RESULT_HIDDEN);
                            super.onReceiveResult(resultCode, resultData);
                        }
                    });
                }
            });
        } else {
            return Single.just(false);
        }
    }
}
