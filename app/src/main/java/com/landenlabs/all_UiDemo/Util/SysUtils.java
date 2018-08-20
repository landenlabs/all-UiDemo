package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * System Utility methods.
 */
public class SysUtils {

    @NonNull
    public static  <T> T getServiceSafe(Context context, String service) {
        //noinspection unchecked
        return (T) Objects.requireNonNull(context.getSystemService(service));
    }
}
