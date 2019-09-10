package cn.tencent.DiscuzMob.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.utils.Tools;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.model.BaseVariables;

public class LoginPost implements Runnable {

    private static final String TAG = "LoginPost";
    private HttpURLConnection connection = null;
    private DataOutputStream outputStream = null;

    private String endLine = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "----kDdwDwoddGegowwdSmoqdaAesgjk";
    private static final String CHARSET = "utf-8"; //设置编码

    //正式环境
//    private final String URL = "http://bbs.rednet.cn/api/mobile/";
    private final String URL = AppNetConfig.BASEURL;
    //测试环境
//    private final String URL = "http://rednet.pm.comsenz-service.com/api/mobile/";

    private HashMap<String, String> params;

    private LoginResult result;
    private Map<String, List<String>> newHeaderFields;
    private String answer;

    public LoginPost(HashMap<String, String> data, LoginResult result) {
        this.params = data;
        this.result = result;
    }

    @Override
    public void run() {
        if (Tools.checkNetworkState()) {
            try {
//                String url = URL + "?module=login&debug=1&loginsubmit=yes&version=1&android=1";
                String url = URL + "?module=login&version=1&loginsubmit=yes&loginfield=auto";
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                String sechash = params.get("sechash");
                if (sechash != null && !sechash.equals("")) {
                    sb.append("&sechash=" + sechash);
                }
                String seccodeverify = params.get("seccodeverify");
                if (seccodeverify != null && !seccodeverify.equals("")) {
                    sb.append("&seccodeverify=" + seccodeverify);
                }

                URL loginUrl = new URL(sb.toString());
                connection = (HttpURLConnection) loginUrl.openConnection();

                // Allow Inputs & Outputs
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Enable POST method
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charset", CHARSET);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-type", "multipart/form-data;boundary=" + boundary);

                String cookie = params.get("vcodeCookie");
                String saltkey = params.get("saltkey");
                if (cookie != null && !cookie.equals("")) {
                    connection.setRequestProperty("Cookie", cookie + ";" + saltkey);
                }

                outputStream = new DataOutputStream(connection.getOutputStream());
                // username
                String username = params.get("username");
                if (!TextUtils.isEmpty(username)) {
                    username = new String(username.getBytes(), Charset.forName("UTF-8"));
                }
                outputStream.writeBytes(twoHyphens + boundary + endLine);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"username\"" + endLine);
                outputStream.writeBytes(endLine);
                outputStream.writeBytes(URLEncoder.encode(username) + endLine);
                // password
                String password = params.get("password");
                outputStream.writeBytes(twoHyphens + boundary + endLine);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"password\"" + endLine);
                outputStream.writeBytes(endLine);
                outputStream.writeBytes(URLEncoder.encode(password) + endLine);
                //questionid
                String questionid = params.get("questionId");
                if (questionid != null) {
                    answer = params.get("answer");
                    outputStream.writeBytes(twoHyphens + boundary + endLine);
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"questionid\"" + endLine);
                    outputStream.writeBytes(endLine);
                    outputStream.writeBytes(URLEncoder.encode(questionid) + endLine);
                    outputStream.writeBytes(twoHyphens + boundary + endLine);
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"answer\"" + endLine);
                    outputStream.writeBytes(endLine);
                    outputStream.writeBytes(URLEncoder.encode(answer) + endLine);
                }

                outputStream.writeBytes(endLine);
                outputStream.writeBytes(twoHyphens + boundary + endLine);

                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                StringBuffer resultsb = new StringBuffer();
                if (serverResponseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        resultsb.append(inputLine);
                    }
                    String resultStr = resultsb.toString();
                    Map<String, List<String>> headerFields = connection.getHeaderFields();
//                    BaseModel<BaseVariables> value = BaseVariablesModel.parse(resultStr);
//                    saltkey = value.getVariables().getSaltkey();
                    if (saltkey != null) {
                        String saltkeyStr = saltkey + ";path=/";
                        Set<String> set = headerFields.keySet();
                        Iterator itor = set.iterator();
                        newHeaderFields = new HashMap<>();
                        while (itor.hasNext()) {
                            String key = (String) itor.next();
                            newHeaderFields.put(key, headerFields.get(key));
                        }
                        List oldcookieList = newHeaderFields.get("Set-Cookie");
                        List cookieList = new ArrayList();
                        for (int i = 0; i < oldcookieList.size(); i++) {
                            cookieList.add(oldcookieList.get(i));
                        }
                        cookieList.add(saltkeyStr);
                        newHeaderFields.remove("Set-Cookie");
                        newHeaderFields.put("Set-Cookie", cookieList);
                    }

                    BaseModel<BaseVariables> data = new Gson().fromJson(resultStr, new TypeToken<BaseModel<BaseVariables>>() {
                    }.getType());
                    String messageval = data.getMessage().getMessageval();
                    if (messageval.contains("login_succeed") || messageval.contains("success")) {
                        ((CookieManager) RedNet.mHttpClient.getCookieHandler()).getCookieStore().removeAll();
                        CookieHandler handler = RedNet.mHttpClient.getCookieHandler();
                        if (handler != null) {
                            if (saltkey != null) {
                                handler.put(URI.create(url), newHeaderFields);
                            } else {
                                handler.put(URI.create(url), headerFields);
                            }
                            RedNet.mHttpClient.setCookieHandler(handler);
                        }
                    }
                    result.onResult(resultStr);
                    in.close();
                } else {
                    String errorStr = "ServerResponseMessage:"
                            + serverResponseMessage + " responseCode:"
                            + serverResponseCode;
                }
                outputStream.flush();
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Exception ex = new Exception("网络出现异常");
        }

    }

    public interface LoginResult {
        void onResult(Object loginResult);

        void onError(Exception ex, String path);
    }

}
