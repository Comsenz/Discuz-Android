package cn.tencent.DiscuzMob.ui.fragment.thread;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.js.IRednetJavascript;
import cn.tencent.DiscuzMob.widget.NestedSwipeRefreshLayout;
import cn.tencent.DiscuzMob.widget.RednetInput;
import cn.tencent.DiscuzMob.js.RednetJavascriptManager;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by AiWei on 2016/7/5.
 */
public abstract class SimpleWebFragment extends Fragment implements IRednetJavascript {

    ProgressBar mProgressbar;
    TextView mTip;
    NestedSwipeRefreshLayout mRefreshView;
    WebView mWebView;
    RednetInput mRednetInput;

    public static class RednetWebViewClient extends WebViewClient {

        SimpleWebFragment f;

        public RednetWebViewClient(SimpleWebFragment f) {
            this.f = f;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (f != null && f.mProgressbar != null) {
                f.mProgressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.indexOf("file:///android_asset/") != -1) {
            }
            if (f != null && f.mProgressbar != null) {
                f.mProgressbar.setVisibility(View.GONE);
            }
        }

    }

    public static Fragment newInstance(int special, String fid, String tid, String initJSONString) {
        Fragment f;
        if (special == 1)
            f = new VoteThreadDetailsFragment();
        else if (special == 4)
            f = new ActivityThreadDetailsFragment();
        else
            f = new ThreadDetailsFragment();
        Bundle args = new Bundle();
        args.putString("fid", fid);
        args.putString("tid", tid);
        args.putString("init", initJSONString);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_thread_details, container, false);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progress);
        mTip = (TextView) view.findViewById(R.id.label);
        mRefreshView = (NestedSwipeRefreshLayout) view.findViewById(R.id.refresh);
        mWebView = (WebView) view.findViewById(R.id.webview);
        mRednetInput = (RednetInput) view.findViewById(R.id.input);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RednetJavascriptManager.settings(mWebView);
    }

    /**
     * @param id share,more
     */
    public void onOptionsItemClick(int id) {
    }

    /**
     * @param json
     */
    public void onInitData(String json) {
    }

    static Bundle parse(Fragment f, String value) {
        try {
            JSONObject json = new JSONObject(value);
            JSONObject message = json.optJSONObject("Message");
            if (message != null) {
                RednetUtils.toast(f.getActivity(), message.optString("messagestr"));
                f.getActivity().finish();
            } else {
                JSONObject variables = json.optJSONObject("Variables");
                JSONObject thread = variables.optJSONObject("thread");
                RedNetApp.setFormHash(variables.optString("formhash"));
                Bundle bundle = new Bundle();
                bundle.putString("fid", thread.optString("fid"));
                bundle.putString("subject", thread.optString("subject"));
                bundle.putString("authorid", thread.optString("authorid"));
                bundle.putString("shareText", thread.optString("author") + "发表于" + thread.optString("dateline"));
                JSONArray postList = variables.optJSONArray("postlist");
                if (postList != null && postList.length() > 0) {
                    bundle.putString("rid", postList.optJSONObject(0).optString("pid"));
                    JSONArray attachList = postList.optJSONObject(0).optJSONArray("attchlist");
                    if (attachList != null && attachList.length() > 0) {
                        bundle.putString("thumburl", attachList.optJSONObject(0).optString("thumburl"));
                    }
                }
                return bundle;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
