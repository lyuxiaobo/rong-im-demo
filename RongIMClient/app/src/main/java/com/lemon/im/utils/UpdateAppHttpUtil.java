package com.lemon.im.utils;

import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
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
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                callBack.onResponse(response.body());
            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback) {
        OkGo.<File>get(url).execute(new com.lzy.okgo.callback.FileCallback(path, fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                callback.onResponse(response.body());
            }
        });
    }
}
