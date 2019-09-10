package cn.tencent.DiscuzMob.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.Tools;

@Deprecated
public class UploadFile implements Runnable {

    private final String FILE_SERVER_URL ="http://bbs.rednet.cn/api/mobile/";
    //private final String FILE_SERVER_URL = "http://rednet.pm.comsenz-service.com/api/mobile/";

    private HttpURLConnection connection = null;
    private DataOutputStream outputStream = null;
    private FileResult result;

    private String pathToOurFile;
    private String uploadhash;
    private String fid;

    private String endLine = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "----kDdwDwoddGegowwdSmoqdaAesgjk";
    private static final String CHARSET = "utf-8"; //设置编码

    public UploadFile(String filePath, String uploadhash, String fid, FileResult result) {
        this.pathToOurFile = filePath;
        this.uploadhash = uploadhash;
        this.fid = fid;
        this.result = result;
    }

    public FileResult getResult() {
        return result;
    }

    @Override
    public void run() {
        if (Tools.checkNetworkState()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new java.io.File(pathToOurFile));
                URL url = new URL(FILE_SERVER_URL + "?module=forumupload&fid=" + fid + "&version=4&simple=1");
                connection = (HttpURLConnection) url.openConnection();

                // Allow Inputs & Outputs
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Enable POST method
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-type", "multipart/form-data;boundary=" + boundary);

                String cookieStr = RedNetApp.getInstance().getUserLogin(false).getCookie().trim();
                connection.setRequestProperty("Cookie", cookieStr);

                outputStream = new DataOutputStream(connection.getOutputStream());

                // hash
                outputStream.writeBytes(twoHyphens + boundary + endLine);
                outputStream
                        .writeBytes("Content-Disposition: form-data; name=\"hash\""
                                + endLine);
                outputStream.writeBytes(endLine);
                outputStream.writeBytes(uploadhash + endLine);

                // uid
                outputStream.writeBytes(twoHyphens + boundary + endLine);
                outputStream
                        .writeBytes("Content-Disposition: form-data; name=\"uid\""
                                + endLine);
                outputStream.writeBytes(endLine);
                outputStream.writeBytes(RedNetApp.getInstance().getUserLogin(false).getMember_uid() + endLine);

                // Read file
                outputStream.writeBytes(twoHyphens + boundary + endLine);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"Filedata\"; type=image/png; filename=\"" + new java.io.File(pathToOurFile).getName() + "\"" + endLine);
                outputStream.writeBytes("Content-Type: application/octet-stream;" + endLine);
                outputStream.writeBytes(endLine);

                Bitmap bit = BitmapFactory.decodeFile(pathToOurFile);
                Bitmap a = scaleBitmap(bit, 1280, 960);
                a.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);

                outputStream.writeBytes(endLine);
                outputStream.writeBytes(twoHyphens + boundary + endLine);

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                StringBuffer sb = new StringBuffer();
                if (serverResponseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    String resultStr = sb.toString();
                    result.onResult(resultStr);
                    in.close();
                } else {
                    if (result != null) {
                        result.onError(new Exception("服务器响应错误:" + serverResponseMessage + " responseCode:" + serverResponseCode)
                                , pathToOurFile);
                    }
                }
                fileInputStream.close();
                outputStream.close();
            } catch (Exception ex) {
                if (result != null) {
                    result.onError(new Exception("连接中断"), pathToOurFile);
                }
            }
        } else {
            if (result != null) {
                result.onError(new Exception("网络出现异常"), pathToOurFile);
            }
        }

    }

    public static Bitmap scaleBitmap(Bitmap srcBitmap, int width, int height) {
        if (srcBitmap == null && width < 1 && height < 1) {
            return null;
        }
        int w = srcBitmap.getWidth();//
        int h = srcBitmap.getHeight();
        if (w > width || h > height) {
            float scaleh = 1f;
            float scalew = 1f;
            scaleh = (height * 1.0f) / h;//
            scalew = (width * 1.0f) / w;
            float sacle = 1f;
            if (scaleh > 1f && scaleh > 1f) {
                sacle = Math.max(scaleh, scalew);
            } else {
                sacle = Math.min(scaleh, scalew);
            }
            Matrix m = new Matrix();//
            m.postScale(sacle, sacle);//
            Bitmap resizedBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, w, h, m, true);
            return resizedBitmap;
        } else {
            return srcBitmap;
        }
    }

    public interface FileResult {

        void onResult(Object fileResult);

        void onError(Exception ex, String path);
    }

}
