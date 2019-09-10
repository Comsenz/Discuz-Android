package cn.tencent.DiscuzMob.manager;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by kurt on 15-6-4.
 */
public class DataLoaderCallback implements Callback {

    InterDataLoaderListener mListener;

    public DataLoaderCallback() {}

    public DataLoaderCallback(InterDataLoaderListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mListener.onError(e.getMessage());
    }
    @Override
    public void onResponse(Response response) throws IOException {
        String result = response != null && response.isSuccessful() ? response.body().string() : null;
        mListener.onLoadFinished(result);
    }

}
