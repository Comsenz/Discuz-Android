package cn.tencent.DiscuzMob.base;

import android.content.Context;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AiWei on 2016/6/12.
 * Appending cookie inside Request.Header
 */
public class AppendCookieManager extends CookieManager {

    public AppendCookieManager(Context context) {
        this(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
    }

    public AppendCookieManager(CookieStore store, CookiePolicy cookiePolicy) {
        super(store, cookiePolicy);
    }

    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        Map<String, List<String>> cookies = super.get(uri, requestHeaders);
        if (requestHeaders != null && requestHeaders.containsKey("Cookie")) {
            List<String> source = requestHeaders.get("Cookie");
            if (source != null && !source.isEmpty()) {
                if (cookies == null || !cookies.containsKey("Cookie")) {
                    cookies = new HashMap<>();
                    cookies.put("Cookie", new ArrayList<>(source));
                } else {
                    List<String> n = cookies.get("Cookie");
                    List<String> append = new ArrayList<>(n);
                    int nszie = n.size(), ssize = source.size();
                    int i = 0;
                    while (true) {
                        if (i < nszie && i < ssize) {
                            append.set(i, new StringBuilder(n.get(i)).append(";").append(source.get(i)).toString());//Cookie
                        } else if (nszie < ssize && i < ssize) {
                            append.set(i, String.valueOf(source.get(i)));
                        } else {
                            break;
                        }
                        i++;
                    }
                    cookies = new HashMap<>();
                    cookies.put("Cookie", append);
                }
            }
        }
        return cookies;
    }

}
