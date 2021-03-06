package com.yurkiv.weatherforecast;

import android.content.Context;

/**
 * An interface that provides some callback functions.
 * @author inikolov
 *
 */
public interface Notifier {
    /**
     * Notifies when an operation is finished.
     * 
     * @param res The resource that the operation was called for.
     * @param activity The activity that initiated the operation.
     */
    void operationFinished(Context context, int city, String cityName);
}