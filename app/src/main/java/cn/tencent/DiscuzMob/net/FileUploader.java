package cn.tencent.DiscuzMob.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.Callback;
import cn.tencent.DiscuzMob.utils.Md5;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;

/**
 * Created by AiWei on 2016/7/8.
 */
public class FileUploader {

    private static volatile Pattern sPattern = Pattern.compile("DISCUZUPLOAD\\|(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\d+)");

    final HashMap<String, SoftReference<Object>> mRemoved = new HashMap<>();//移除上传成功历史
    final HashMap<String, Object> mQueue = new HashMap<>();//上传成功历史
    final HashMap<String, AsyncTask> mTask = new HashMap<>();//正在进行的任务

    Context mContext;

    public static abstract class CallbackImpl<V> implements Callback<V> {

        public String key;

        public CallbackImpl(String key) {
            this.key = key;
        }

    }

    private final Object NOTIFY;

    private FileUploader(Context context, Object obj) {
        this.mContext = context;
        this.NOTIFY = obj;
    }

    String getKey(String path, String uploadhash) {
        return Md5.md5(new StringBuilder(path).append("_").append(uploadhash).toString());
    }

    public boolean isAllCompleted() {
        return mTask.isEmpty();
    }

    public boolean isAllCompletedSuccessful() {
        if (mQueue.size() > 0) {
            for (Iterator<Map.Entry<String, Object>> iterator = mQueue.entrySet().iterator(); iterator.hasNext(); ) {
                if (iterator.next().getValue() instanceof Exception) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPut(String path, String uploadhash, boolean toast) {
        String key = getKey(path, uploadhash);
        boolean can = !mTask.containsKey(key);
        if (mQueue.containsKey(key) && mQueue.get(key) instanceof Exception) {
            can &= true;
        } else {
            can &= !mQueue.containsKey(key);
        }
        if (!can && toast) {
            RednetUtils.toast(mContext, "已经添加过了");
        }
        return can;
    }

    public void cancel(String path, String uploadhash) {
        String key = getKey(path, uploadhash);
        if (mQueue.containsKey(key)) {
            Object value = mQueue.remove(key);
            if (value instanceof String) {//已经上传成功过的保留历史
                mRemoved.put(key, new SoftReference<>(value));
            }
        }
        if (mTask.containsKey(key)) {
            AsyncTask rt = mTask.get(key);
            if (rt != null) rt.cancel(true);
            mTask.remove(key);
        }
    }

    public void clearAll() {
        for (Iterator<Map.Entry<String, AsyncTask>> iterator = mTask.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, AsyncTask> entry = iterator.next();
            AsyncTask task = entry.getValue();
            if (task != null) {
                task.cancel(true);
            }
        }
        mQueue.clear();
        mTask.clear();
    }

    public ArrayList<String> aidList(String uploadhash, String[] paths) {
        ArrayList<String> aidList = new ArrayList<>();
        if (paths != null && paths.length > 0) {
            for (int i = 0; i < paths.length; i++) {
                String aid = aid(paths[i], uploadhash);
                if (!TextUtils.isEmpty(aid)) {
                    aidList.add(new StringBuilder("attachnew[").append(aid).append("][description]").toString());
                }
            }
        }
        return aidList;
    }

    /**
     * DISCUZUPLOAD|3|0|1|1048576
     * <p/>
     * DISCUZUPLOAD为固定输出
     * 第一个0	代表报错信息，0为正常，非零为出错。
     * 第二个1051301	代表成功上传图片的附件aid
     * 第三个1	代表是否是图片
     * 第四个0	代表导致出错的上传文件容量的尺寸，比如1024000,则限制1024000字节以下的
     */
    public String aid(String path, String uploadhash) {
        return (String) mQueue.get(getKey(path, uploadhash));
    }

    public void submit(String path, String uploadhash, String fid, final CallbackImpl callback) {
        final String key = getKey(path, uploadhash);
        if (!canPut(path, uploadhash, true)) {
            return;
        } else {
            if (mRemoved.containsKey(key)) {
                Object value = mRemoved.remove(key).get();
                if (value != null && !(value instanceof Exception)) {
                    mQueue.put(key, value);
                    if (callback != null) {
                        callback.onCallback(value);
                    }
                    return;
                }
            }
        }
        AsyncTask rt = new AsyncTask<UploadFile, Void, Object>() {
            @Override
            protected Object doInBackground(UploadFile... params) {
                try {
                    return params[0].call();
                } catch (Exception e) {
                    return new Exception(e);
                }
            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
                if (mTask.containsKey(key)) mTask.remove(key);
                if (mQueue.containsKey(key)) mQueue.remove(key);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (callback != null) {
                    callback.onCallback(o);
                }
                if (o instanceof Exception) {
                    String toast;
                    if (!TextUtils.isEmpty(toast = ((Exception) o).getMessage()) && !"error".equals(toast))
                        RednetUtils.toast(mContext, toast);
                }
                mQueue.put(key, o);
                mTask.remove(key);
                synchronized (NOTIFY) {
                    if (isAllCompleted() && NOTIFY != null)
                        NOTIFY.notify();
                }
            }
        }.execute(new UploadFile(path, uploadhash, fid));
        mTask.put(key, rt);
    }

    public void submitPoll(String path, String uploadhash, String fid, final CallbackImpl callback) {
        final String key = getKey(path, uploadhash);
        if (!canPut(path, uploadhash, true)) {
            return;
        } else {
            if (mRemoved.containsKey(key)) {
                Object value = mRemoved.remove(key).get();
                if (value != null && !(value instanceof Exception)) {
                    mQueue.put(key, value);
                    if (callback != null) {
                        callback.onCallback(value);
                    }
                    return;
                }
            }
        }
        AsyncTask rt = new AsyncTask<Uploadpoll, Void, Object>() {

            @Override
            protected Object doInBackground(Uploadpoll... params) {
                try {
                    return params[0].call();
                } catch (Exception e) {
                    return new Exception(e);
                }
            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
                if (mTask.containsKey(key)) mTask.remove(key);
                if (mQueue.containsKey(key)) mQueue.remove(key);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (callback != null) {
                    callback.onCallback(o);
                }
                if (o instanceof Exception) {
                    String toast;
                    if (!TextUtils.isEmpty(toast = ((Exception) o).getMessage()) && !"图片上传失败".equals(toast))
                        RednetUtils.toast(mContext, toast);
                }
                mQueue.put(key, o);
                mTask.remove(key);
                synchronized (NOTIFY) {
                    if (isAllCompleted() && NOTIFY != null)
                        NOTIFY.notify();
                }
            }
        }.execute(new Uploadpoll(path, uploadhash, fid));
        mTask.put(key, rt);
    }

    public void submitAudio(final Context context, String path, String uploadhash, String fid, final CallbackImpl callback) {

        final String key = getKey(path, uploadhash);
        if (!canPut(path, uploadhash, true)) {
            return;
        } else {
            if (mRemoved.containsKey(key)) {
                Object value = mRemoved.remove(key).get();
                if (value != null && !(value instanceof Exception)) {
                    mQueue.put(key, value);
                    if (callback != null) {
                        callback.onCallback(value);
                    }
                    return;
                }
            }
        }
        AsyncTask rt = new AsyncTask<UploadAudio, Void, Object>() {
            @Override
            protected Object doInBackground(UploadAudio... params) {
                try {
                    params[0].setContext(context);
                    return params[0].call();
                } catch (Exception e) {
                    return new Exception(e);
                }
            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
                if (mTask.containsKey(key)) mTask.remove(key);
                if (mQueue.containsKey(key)) mQueue.remove(key);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (callback != null) {
                    callback.onCallback(o);
                }
                if (o instanceof Exception) {
                    String toast;
                    if (!TextUtils.isEmpty(toast = ((Exception) o).getMessage()) && !"error".equals(toast))
                    RednetUtils.toast(mContext, toast);
                }
                mQueue.put(key, o);
                mTask.remove(key);
                synchronized (NOTIFY) {
                    if (isAllCompleted() && NOTIFY != null)
                        NOTIFY.notify();
                }
            }
        }.execute(new UploadAudio(path, uploadhash, fid));
        mTask.put(key, rt);
    }


    public static FileUploader newInstance(Context context, Object obj) {
        return new FileUploader(context, obj);
    }

    final class UploadAudio implements Callable<String> {
        HttpURLConnection connection;
        DataOutputStream outputStream = null;

        String pathToOurFile;
        String uploadhash;
        String fid;

        String endLine = "\r\n";
        String twoHyphens = "--";
        String boundary = "----kDdwDwoddGegowwdSmoqdaAesgjk";
        private Context context;

        public UploadAudio(String filePath, String uploadhash, String fid) {
            this.pathToOurFile = filePath;
            this.uploadhash = uploadhash;
            this.fid = fid;
        }


        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public String call() throws Exception {
            try {
                URL url = new URL(AppNetConfig.BASEURL + "?module=forumupload&fid=" + fid + "&version=5&mobiletype=Android&simple=1");//&simple=1
                Log.e("TAG", "未知url =" + url);
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
                outputStream.writeBytes("Content-Disposition: form-data; name=\"Filedata\"; type=audio/mp3; filename=\"" + new java.io.File(pathToOurFile).getName() + "\"" + endLine);
                outputStream.writeBytes("Content-Type: type=audio/mp3;" + endLine);
                outputStream.writeBytes(endLine);
                final Uri uri = Uri.parse(pathToOurFile);
                final File file = uri2File(context, uri);

                FileInputStream fStream = new FileInputStream(file);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);

                }
                outputStream.writeBytes(endLine);
                fStream.close();
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + endLine);
                outputStream.flush();
                                     /* close streams */

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("TAG", "serverResponseMessage=" + serverResponseMessage);
                StringBuffer sb = new StringBuffer();
                if (serverResponseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    CacheUtils.putString(context, "inputLine", in.readLine());
                    Log.e("TAG", "in.readLine()=" + in.readLine());
                    String inputLine = in.readLine();

                    in.close();
                } else {
                    throw new Exception("服务器响应错误:" + serverResponseMessage + " responseCode:" + serverResponseCode);
                }
                outputStream.close();
                String resultStr = sb.toString();
                Log.e("TAG", "resultStr=" + resultStr);
                String inputLine = CacheUtils.getString(context, "inputLine");
                Log.e("TAG", "inputLine=" + inputLine);
                if (null != inputLine && !TextUtils.isEmpty(inputLine)) {
                    int i1 = inputLine.indexOf("|");
                    String substring2 = inputLine.substring(i1 + 1, inputLine.length());
                    String code = substring2.substring(0, 1);
                    Log.e("TAG", "code=" + code);
//                    String code = resultStr.substring(13, 14);
                    if (code.equals("0")) {
                        String substring = substring2.substring(2, substring2.length());
                        Log.e("TAG", "substring000000=" + substring);
                        int i = substring.indexOf("|");
                        String substring1 = substring.substring(0, i);
                        Log.e("TAG", "substring1=" + substring1);
                        return substring1;
                    } else {
//                                '-1' : '内部服务器错误',
//                                '0' : '上传成功',
//                                '1' : '不支持此类扩展名',
//                                '2' : '服务器限制无法上传那么大的附件',
//                                '3' : '用户组限制无法上传那么大的附件',
//                                '4' : '不支持此类扩展名',
//                                '5' : '文件类型限制无法上传那么大的附件',
//                                '6' : '今日您已无法上传更多的附件',
//                                '7' : '请选择图片文件(' + imgexts + ')',
//                                '8' : '附件文件无法保存',
//                                '9' : '没有合法的文件被上传',
//                                '10' : '非法操作',
//                                '11' : '今日您已无法上传那么大的附件'
                        if (code.equals("-1")) {
                            inputLine = "内部服务器错误";
                        } else if (code.equals("1")) {
                            inputLine = "不支持此类扩展名";
                        } else if (code.equals("2")) {
                            inputLine = "服务器限制无法上传那么大的附件";
                        } else if (code.equals("3")) {
                            inputLine = "用户组限制无法上传那么大的附件";
                        } else if (code.equals("4")) {
                            inputLine = "不支持此类扩展名";
                        } else if (code.equals("5")) {
                            inputLine = "文件类型限制无法上传那么大的附件";
                        } else if (code.equals("6")) {
                            inputLine = "今日您已无法上传更多的附件";
                        } else if (code.equals("7")) {
                            inputLine = "请选择图片文件(' + imgexts + ')";
                        } else if (code.equals("8")) {
                            inputLine = "附件文件无法保存";
                        } else if (code.equals("9")) {
                            inputLine = "没有合法的文件被上传";
                        } else if (code.equals("10")) {
                            inputLine = "非法操作";
                        } else if (code.equals("11")) {
                            inputLine = "今日您已无法上传那么大的附件";
                        }
                        return inputLine;
                    }
                } else {
                    throw new Exception("上传失败");
                }
                /*Matcher m;//DISCUZUPLOAD|3|0|1|1048576
                if (!TextUtils.isEmpty(resultStr) && (m = sPattern.matcher(resultStr)).find() && m.group(1).endsWith("0")) {
                    return m.group(2);
                } else {
                    throw new Exception("error");
                }*/
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }

    static final class UploadFile implements Callable<String> {

        HttpURLConnection connection;
        DataOutputStream outputStream = null;

        String pathToOurFile;
        String uploadhash;
        String fid;

        String endLine = "\r\n";
        String twoHyphens = "--";
        String boundary = "----kDdwDwoddGegowwdSmoqdaAesgjk";

        public UploadFile(String filePath, String uploadhash, String fid) {
            this.pathToOurFile = filePath;
            this.uploadhash = uploadhash;
            this.fid = fid;
        }

        @Override
        public String call() throws Exception {
            try {
                URL url = new URL(AppNetConfig.BASEURL + "?module=forumupload&fid=" + fid + "&version=5&type=image&simple=1");//&simple=1
                Log.e("TAG", "未知url =" + url);
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

                Bitmap result = loadBitmap(pathToOurFile, 1024 * 1024);
                if (result != null) {
                    result.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    throw new Exception("获取图片失败");
                }

                outputStream.writeBytes(endLine);
                outputStream.writeBytes(twoHyphens + boundary + endLine);

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("TAG", "serverResponseCode=" + serverResponseCode);
                Log.e("TAG", "serverResponseMessage=" + serverResponseMessage);
                StringBuffer sb = new StringBuffer();
                if (serverResponseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String inputLine;
//                    Log.e("TAG", "in.readLine()=" + in.readLine());
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    in.close();
                } else {
                    throw new Exception("服务器响应错误:" + serverResponseMessage + " responseCode:" + serverResponseCode);
                }
                outputStream.close();
                String resultStr = sb.toString();
                Log.e("TAG", "resultStr=" + resultStr);
//                DISCUZUPLOAD|0|289|1|0
                if (!TextUtils.isEmpty(resultStr)) {
                    String code = resultStr.substring(13, 14);
                    if (code.equals("0")) {
                        String substring = resultStr.substring(15, resultStr.length());
                        Log.e("TAG", "mmm=" + substring.indexOf("|"));
                        int i = substring.indexOf("|");
                        String substring1 = substring.substring(0, i);
                        return substring1;
                    } else {
//                                '-1' : '内部服务器错误',
//                                '0' : '上传成功',
//                                '1' : '不支持此类扩展名',
//                                '2' : '服务器限制无法上传那么大的附件',
//                                '3' : '用户组限制无法上传那么大的附件',
//                                '4' : '不支持此类扩展名',
//                                '5' : '文件类型限制无法上传那么大的附件',
//                                '6' : '今日您已无法上传更多的附件',
//                                '7' : '请选择图片文件(' + imgexts + ')',
//                                '8' : '附件文件无法保存',
//                                '9' : '没有合法的文件被上传',
//                                '10' : '非法操作',
//                                '11' : '今日您已无法上传那么大的附件'
                        if (code.equals("-1")) {
                            resultStr = "内部服务器错误";
                        } else if (code.equals("1")) {
                            resultStr = "不支持此类扩展名";
                        } else if (code.equals("2")) {
                            resultStr = "服务器限制无法上传那么大的附件";
                        } else if (code.equals("3")) {
                            resultStr = "用户组限制无法上传那么大的附件";
                        } else if (code.equals("4")) {
                            resultStr = "不支持此类扩展名";
                        } else if (code.equals("5")) {
                            resultStr = "文件类型限制无法上传那么大的附件";
                        } else if (code.equals("6")) {
                            resultStr = "今日您已无法上传更多的附件";
                        } else if (code.equals("7")) {
                            resultStr = "请选择图片文件(' + imgexts + ')";
                        } else if (code.equals("8")) {
                            resultStr = "附件文件无法保存";
                        } else if (code.equals("9")) {
                            resultStr = "没有合法的文件被上传";
                        } else if (code.equals("10")) {
                            resultStr = "非法操作";
                        } else if (code.equals("11")) {
                            resultStr = "今日您已无法上传那么大的附件";
                        }
                        return resultStr;
                    }

                } else {
                    throw new Exception("error");
                }
                /*Matcher m;//DISCUZUPLOAD|3|0|1|1048576
                if (!TextUtils.isEmpty(resultStr) && (m = sPattern.matcher(resultStr)).find() && m.group(1).endsWith("0")) {
                    return m.group(2);
                } else {
                    throw new Exception("error");
                }*/
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }

    public static Bitmap loadBitmap(String path, int maxSize) {
        Bitmap b = getScaledBitmap(path, 720f, 1280f);
        if (b != null && Build.VERSION.SDK_INT > 11) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                if (b.getByteCount() > maxSize) {
                    int quality = 100;
                    while (true) {
                        int len = bos.size();
                        if (len == 0 || (len > maxSize && quality > 50)) {
                            bos.reset();
                            if (!b.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
                                break;
                            }
                            quality -= 2;
                        } else
                            break;
                    }
                }
                byte[] bytes = bos.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    Bitmap dest = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    b.recycle();
                    return dest;
                }
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }
        return b;
    }

    public static Bitmap getScaledBitmap(String path, float desWidthPixels, float desHeightPixels) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, ops);
        if (!TextUtils.isEmpty(ops.outMimeType)) {
            if (ops.mCancel || ops.outWidth == -1 || ops.outHeight == -1)
                return null;
            boolean isPortrait = (ops.outHeight - ops.outWidth) >= 0;
            ops.inJustDecodeBounds = false;
            if (isPortrait) {//refer to width
                ops.inSampleSize = Math.max(1, Math.round(ops.outWidth / desWidthPixels));
            } else {//refer to height
                ops.inSampleSize = Math.max(1, Math.round(ops.outHeight / desHeightPixels));
            }
            Bitmap bm;
            try {
                bm = BitmapFactory.decodeFile(path, ops);
            } catch (OutOfMemoryError e) {
                ops.inSampleSize = 2;
                bm = BitmapFactory.decodeFile(path, ops);
            }
            if (bm != null) {
                if (isPortrait) {
                    if (bm.getWidth() > desWidthPixels) {
                        Matrix m = new Matrix();
                        float scale = desWidthPixels / bm.getWidth();
                        m.postScale(scale, scale);
                        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                    }
                } else {
                    if (bm.getHeight() > desHeightPixels) {
                        Matrix m = new Matrix();
                        float scale = desHeightPixels / bm.getHeight();
                        m.postScale(scale, scale);
                        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                    }
                }
                return bm;
            }
        }
        return null;
    }

    private File uri2File(Context context, Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Log.e("TAG", "proj =" + proj);
//        Cursor actualimagecursor =((Activity)context).managedQuery(uri, proj, null,
//                null, null);
//        Cursor actualimagecursor =((Activity)context).getContentResolver().query(uri, proj, null,
//                null, null);
//        Log.e("TAG", "actualimagecursor =" + actualimagecursor);
//        int actual_image_column_index = actualimagecursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor
//                .getString(actual_image_column_index);
//        actualimagecursor.close();
//        file = new File(img_path);
//        return file;
        String path = uri.getPath();
        return new File(path);
    }

    private class Uploadpoll implements Callable<String> {

        //        private final String FILE_SERVER_URL = "http://bbs.rednet.cn/api/mobile/";
        private final String FILE_SERVER_URL = "http://10.0.6.58/api/mobile/";
        //final String FILE_SERVER_URL = "http://rednet.pm.comsenz-service.com/api/mobile/";
        static final String CHARSET = "utf-8"; //设置编码

        HttpURLConnection connection;
        DataOutputStream outputStream = null;

        String pathToOurFile;
        String uploadhash;
        String fid;

        String endLine = "\r\n";
        String twoHyphens = "--";
        String boundary = "----kDdwDwoddGegowwdSmoqdaAesgjk";

        public Uploadpoll(String filePath, String uploadhash, String fid) {
            this.pathToOurFile = filePath;
            this.uploadhash = uploadhash;
            this.fid = fid;
        }

        @Override
        public String call() throws Exception {
            try {
                URL url = new URL(AppNetConfig.BASEURL + "?version=5&module=forumupload&operation=poll&fid=" + fid);//&simple=1
                Log.e("TAG", "未知url =" + url);
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

                Bitmap result = loadBitmap(pathToOurFile, 1024 * 1024);
                if (result != null) {
                    result.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    throw new Exception("获取图片失败");
                }

                outputStream.writeBytes(endLine);
                outputStream.writeBytes(twoHyphens + boundary + endLine);

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                StringBuffer sb = new StringBuffer();
                String inputLine;
                if (serverResponseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    in.close();
                } else {
                    throw new Exception("服务器响应错误:" + serverResponseMessage + " responseCode:" + serverResponseCode);
                }
                outputStream.close();
                String resultStr = sb.toString();
                Log.e("TAG", "sb.toString()=" + sb.toString());
                if (!TextUtils.isEmpty(sb.toString())) {
                    return sb.toString();
//                    String code = resultStr.substring(13, 14);
//                    if (code.equals("0")) {
//                        String substring = resultStr.substring(15, resultStr.length());
//                        Log.e("TAG", "mmm=" + substring.indexOf("|"));
//                        int i = substring.indexOf("|");
//                        String substring1 = substring.substring(0, i);
//                        return substring1;
//                    } else {
//                                '-1' : '内部服务器错误',
//                                '0' : '上传成功',
//                                '1' : '不支持此类扩展名',
//                                '2' : '服务器限制无法上传那么大的附件',
//                                '3' : '用户组限制无法上传那么大的附件',
//                                '4' : '不支持此类扩展名',
//                                '5' : '文件类型限制无法上传那么大的附件',
//                                '6' : '今日您已无法上传更多的附件',
//                                '7' : '请选择图片文件(' + imgexts + ')',
//                                '8' : '附件文件无法保存',
//                                '9' : '没有合法的文件被上传',
//                                '10' : '非法操作',
//                                '11' : '今日您已无法上传那么大的附件'
//                        if (code.equals("-1")) {
//                            resultStr = "内部服务器错误";
//                        } else if (code.equals("1")) {
//                            resultStr = "不支持此类扩展名";
//                        } else if (code.equals("2")) {
//                            resultStr = "服务器限制无法上传那么大的附件";
//                        } else if (code.equals("3")) {
//                            resultStr = "用户组限制无法上传那么大的附件";
//                        } else if (code.equals("4")) {
//                            resultStr = "不支持此类扩展名";
//                        } else if (code.equals("5")) {
//                            resultStr = "文件类型限制无法上传那么大的附件";
//                        } else if (code.equals("6")) {
//                            resultStr = "今日您已无法上传更多的附件";
//                        } else if (code.equals("7")) {
//                            resultStr = "请选择图片文件(' + imgexts + ')";
//                        } else if (code.equals("8")) {
//                            resultStr = "附件文件无法保存";
//                        } else if (code.equals("9")) {
//                            resultStr = "没有合法的文件被上传";
//                        } else if (code.equals("10")) {
//                            resultStr = "非法操作";
//                        } else if (code.equals("11")) {
//                            resultStr = "今日您已无法上传那么大的附件";
//                        }
//                        return resultStr;
//                    }

                } else {
                    throw new Exception("图片上传失败");
                }
                /*Matcher m;//DISCUZUPLOAD|3|0|1|1048576
                if (!TextUtils.isEmpty(resultStr) && (m = sPattern.matcher(resultStr)).find() && m.group(1).endsWith("0")) {
                    return m.group(2);
                } else {
                    throw new Exception("error");
                }*/
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }
}
