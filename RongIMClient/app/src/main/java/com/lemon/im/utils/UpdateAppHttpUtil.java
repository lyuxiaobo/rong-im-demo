package com.lemon.im.utils;

import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.vector.update_app.HttpManager;

import java.io.File;
import java.util.Map;

/**
 * @author lyubo
 * @date 2021/8/26
 */
public class UpdateAppHttpUtil implements HttpManager {
    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {
        OkGo.<String>get(url).params(params).execute(new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                callBack.onError("异常");
            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {
        OkGo.<String>post(url).params(params).execute(new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                callBack.onResponse(response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                callBack.onError("异常");
            }
        });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback) {
        OkGo.<File>get(url).execute(new com.lzy.okgo.callback.FileCallback(path, fileName) {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.onBefore();
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                callback.onError("异常");
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);

                callback.onProgress(progress.fraction, progress.totalSize);
            }
        });
    }
}
