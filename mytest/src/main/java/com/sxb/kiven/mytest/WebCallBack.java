package com.sxb.kiven.mytest;

import org.xutils.common.Callback;

/**
 * Created by kiven on 16/3/30.
 */
public class WebCallBack implements Callback.ProgressCallback<String> {
    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }
}
