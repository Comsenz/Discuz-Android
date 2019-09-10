package cn.tencent.DiscuzMob.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.tencent.DiscuzMob.base.Config;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.R;

/**
 * 存放本程序中所有不传Activity对象的公共方法，静态方法
 **/
public class Tools {
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 检查sdcard挂载状态̬
     */
    public static Boolean _isSdcardMounted() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 把时间戳(String)转换成日期
     *
     * @param _number
     * @return
     */
    public static String _getNumToDateTime(String _number) {
        if (_number == null) {
            _number = String.valueOf(System.currentTimeMillis());
        }
        if (_number.length() == 10) {
            _number = _number + "000";
        }
        long number = Long.valueOf(_number);
        return _getNumToDateTime(number, null);
    }

    /**
     * 把时间戳(long)转换成日期
     *
     * @param _number
     * @param _form   时间格式
     * @return
     */
    public static String _getNumToDateTime(long _number, String _form) {
        Date date = null;
        DateFormat df = null;
        try {
            date = new Date(_number);
            if (_form != null) {
                df = new SimpleDateFormat(_form);
            } else {
                df = new SimpleDateFormat("yyyy-MM-dd");
            }
        } catch (Exception e) {
            return null;
        }
        return df.format(date);
    }

    /**
     * 获取系统时间戳
     *
     * @return
     */
    public static long _getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static long _getTimeStampUnix() {
        return (long) (System.currentTimeMillis() / 1000);
    }

    /**
     * 正确获取网站脚本MD5的方法
     *
     * @param _str 需要被md5的字符串
     * @return MD5值
     */
    public static String _md5(String _str) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(_str.getBytes());
            byte messageDigest[] = digest.digest();
            return _toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 正确获取网站脚本MD5的方法
     *
     * @return MD5值
     */
    private static String _toHexString(byte[] _b) { // String to byte
        StringBuilder sb = new StringBuilder(_b.length * 2);
        for (int i = 0; i < _b.length; i++) {
            sb.append(HEX_DIGITS[(_b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[_b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 字符串转型为整型
     *
     * @param intStr 要转型字符串
     * @return
     */
    public static int strToInt(String intStr) {
        return strToInt(intStr, 0);
    }


    /**
     * �ַ�ת��Ϊ����
     * 字符串转型为整型
     *
     * @param intStr       要转型字符串
     * @param defaultValue 当转型出现异常时，返回默认值
     * @return
     */
    public static int strToInt(String intStr, int defaultValue) {
        try {
            return Integer.valueOf(intStr);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }


    /**
     * 把以秒为单位的时候转换成以时分秒为单位返回
     *
     * @param second
     * @return
     */
    public static String _time(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int m = 0;
        if (second >= 1000) {
            m = second % 1000;
            second = second / 1000;
            if (second >= 60) {
                s = second % 60;
                second = second / 60;
                if (second >= 60) {
                    d = second % 60;
                    second = second / 60;
                    if (second >= 60) {
                        h = second % 60;
                        second = second / 60;
                    } else {
                        h = second;
                    }
                } else {
                    d = second;
                }
            } else {
                s = second;
            }
        } else {
            m = second;
        }
        return h + "'" + d + "'" + s + "'" + m;
    }

    /**
     * ��ȡ�ļ���С
     * @param fileName
     * @return
     */
//	public static String _fileSize(String fileName) {
//		File file = new File(fileName);
//		long size = file.length();
//		file = null;
//		int k = 0;
//		int m = 0;
//		if (size > 1024) {
//			k = (int) (size % (1024 * 1024));
//			m = (int) (size / (1024 * 1024));
//		} else {
//			k = (int) size;
//		}
//		return m + "." + k + "M";
//	}

    /**
     * 获取文件大小
     *
     * @param
     * @return
     */
    public static String readableFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 获取加载图片的Options选项，用于解决加载大图时内存溢出的问题
     *
     * @param imagePath 图片路径
     * @param scaleSize 尺寸
     * @return
     */
    public static Options getBitmapOptions(String imagePath, int scaleSize) {
        // http://moto0421.iteye.com/blog/1153657
        Options opt = new Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, opt);
        // 获取到这个图片的原始宽度和高度
        int picWidth = opt.outWidth;
        int picHeight = opt.outHeight;
        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        opt.inSampleSize = 1;
        // 根据屏的大小和图片大小计算出缩放比例
        if (picWidth > picHeight) {
            if (picWidth > scaleSize)
                opt.inSampleSize = picWidth / scaleSize + 1;
        } else {
            if (picHeight > scaleSize)
                opt.inSampleSize = picHeight / scaleSize + 1;
        }
        // 这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        return opt;
    }

    /**
     * 判断sourceString字符串是否以字符串endTrim结尾，如果是侧把endTrim字符串截掉
     *
     * @param sourceString
     * @param endTrim
     * @return
     */
    public static String trim(String sourceString, String endTrim) {
        if (!"".equals(sourceString) && !"".equals(endTrim)) {
            if (sourceString.endsWith(endTrim)) {
                return sourceString.substring(0, sourceString.length() - endTrim.length());
            }
        }
        return sourceString;
    }

    //File���

    /**
     * 删除SDCard的文件
     *
     * @param _fileName
     * @return
     */
    public static boolean _deleteSDFile(String _fileName) {
        boolean isDeleted = false;
        if (_isFileExist(_fileName)) {
            File file = new File(_fileName);
            isDeleted = file.delete();
            file = null;
        }
        return isDeleted;
    }

    /**
     * 判断文件是否存在
     *
     * @param _fileName
     * @return
     */
    public static boolean _isFileExist(String _fileName) {
        boolean isExist = false;
        File file = new File(_fileName);
        if (file.exists() && file.isFile()) {
            isExist = true;
        }
        file = null;
        return isExist;
    }

    /**
     * 创建自定义的SDCard目标文件夹
     *
     * @param _sdDir
     */
    public static String _getSDCustomDir(String _sdDir) {
        String sdDir = Config.SDCARD_PATH + _sdDir;
        File dir = new File(sdDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static byte[] resizeImg(String path, int resize) {


        if (resize <= 1024) {
            return null;
        }

        File tempFile = new File(path);
        if (!tempFile.exists()) {
            return null;
        }

        int fileSize = (int) tempFile.length();

        byte[] targetBytes;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        Bitmap photo = null;
        String filePostfix = null;
        CompressFormat formatType = null;
        try {
            if (resize >= fileSize) {
                inputStream = new FileInputStream(tempFile);
                targetBytes = new byte[fileSize];
                if (inputStream.read(targetBytes) <= -1) {
                } else {
                    return targetBytes;
                }

            } else {
                int multiple = fileSize / resize;
                if (fileSize % resize >= 0 && fileSize % resize >= ((fileSize / multiple + 1) / 2)) {
                    multiple++;
                }

                if (multiple > 3) {
                    if (resize == 200 * 1024) {
                        multiple = 3;
                    } else if (resize == 100 * 1024) {
                        multiple = 6;
                    } else if (resize <= 50 * 1024) {
                        multiple = 10;
                    }
                }

                Options options = new Options();
                options.inScaled = true;
                options.inSampleSize = multiple;

                int compressCount = 1;
                do {
                    photo = BitmapFactory.decodeFile(path, options);
                    options.inSampleSize = multiple + compressCount;
                    compressCount++;
                } while (photo == null && compressCount <= 5);

                String[] tempStrArry = path.split("\\.");
                filePostfix = tempStrArry[tempStrArry.length - 1];
                tempStrArry = null;
                if (filePostfix.equals("PNG") || filePostfix.equals("png")) {
                    formatType = CompressFormat.PNG;
                } else if (filePostfix.equals("JPG") || filePostfix.equals("jpg") || filePostfix.equals("JPEG") || filePostfix.equals("jpeg")) {
                    formatType = CompressFormat.JPEG;
                } else {
                    return null;
                }

                int quality = 100;
                while (quality > 0) {
                    baos = new ByteArrayOutputStream();
                    photo.compress(formatType, quality, baos);
                    final byte[] tempArry = baos.toByteArray();

                    if (tempArry.length <= resize) {
                        targetBytes = tempArry;
                        return targetBytes;
                    }
                    if (tempArry.length >= 1000000) {
                        quality = quality - 10;
                    } else if (tempArry.length >= 260000) {
                        quality = quality - 5;
                    } else {
                        quality = quality - 1;
                    }

                    if (baos != null) {
                        baos.close();
                        baos = null;
                    }
                }

            }
        } catch (Exception e) {
        } finally {
            targetBytes = null;
            try {
                filePostfix = null;
                formatType = null;
                if (targetBytes != null && targetBytes.length <= 0) {
                    targetBytes = null;
                }

                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }

                if (baos != null) {
                    baos.close();
                    baos = null;
                }

                if (photo != null) {
                    if (!photo.isRecycled()) {
                        photo.recycle();
                    }
                    photo = null;
                }
            } catch (Exception e) {
            }
        }

        return null;
    }

    public static void stream2File(byte[] stream, String filepath) throws Exception {
        FileOutputStream outStream = null;
        try {
            File f = new File(filepath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            outStream = new FileOutputStream(f);
            outStream.write(stream);
            outStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                    outStream = null;
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    public static void saveImage(Context ctx, String url, Bitmap bm) {

        // save as file first
        String dir = null;
        if (Config.IsCanUseSdCard()) {
            dir = Config.LOCAL_DOWNLOAD_IMAGE_PATH;
        } else {
            dir = ctx.getFilesDir() + "/." + Config.APPNAME + "/";
        }
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdir();
        }

        String filename = dir + url.substring(url.lastIndexOf('/') + 1);
        Log.d("aa", "save " + url + "as file: " + filename);
        File f = new File(filename);
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e1) {
                Log.e("aa", "can't create file: " + f, e1);
            }

        try {
            FileOutputStream out = new FileOutputStream(f);

            bm.compress(CompressFormat.PNG, 90, out);

        } catch (FileNotFoundException e) {
            Log.e("aa", "Can't save file to " + f);
        }

    }

    public static String getLocalPathFromUrl(String url, Context context) {
        if (url == null)
            return null;

        String path = url.substring(url.lastIndexOf('/') + 1).trim();

        if (path == null || path.equals(""))
            return null;
        else if (Config.IsCanUseSdCard()) {
            return (Config.LOCAL_DOWNLOAD_IMAGE_PATH + url.substring(url.lastIndexOf('/') + 1).trim());
        } else {
            return (context.getFilesDir() + "/." + Config.APPNAME + "/" + url.substring(url.lastIndexOf('/') + 1).trim());
        }

    }

    /**
     * 返回的是没开方的数字
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float cal2PtDis(float x1, float y1, float x2, float y2) {
        float lenX = x1 - x2;
        float lenY = y1 - y2;
        return lenX * lenX + lenY * lenY;
    }

    public static String getSDPath() {
        File sdDir = null;
        if (!FileUtils.isExternalStorageWritable()) {
            return "/mnt/sdcard";
        } else {
            sdDir = android.os.Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    /**
     * 判断桌面是否已经存在快捷方式图标
     *
     * @return
     */
    public static boolean hasInstallShortcut(Context context) {
        String url = "";
        if (getSystemVersion() < 8) {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
                new String[]{context.getString(R.string.app_name)}, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        }
        return false;
    }

    private static int getSystemVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }


    /**
     * 添加快捷方式图标到桌面
     */
    public static void addShortcutToDesktop(Context mContext) {
        if (hasInstallShortcut(mContext)) {
            return;
        }
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重建
        shortcut.putExtra("duplicate", false);
        // 设置名字
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, mContext.getResources().getString(R.string.app_name));
        // 设置图标
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(mContext, R.drawable.ic_launcher));
        // 设置意图和快捷方式关联程序
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(mContext, mContext.getClass()).setAction(Intent.ACTION_MAIN));
        // 发送广播
        mContext.sendBroadcast(shortcut);
    }

    public static void initDeviceInfo(Application application) {
        DisplayMetrics dm = application.getResources().getDisplayMetrics();
        Config.DeviceInfo.screenHeight = dm.heightPixels;
        Config.DeviceInfo.screenWidth = dm.widthPixels;
        Config.DeviceInfo.density = dm.density;
        Config.DeviceInfo.densityDpi = dm.densityDpi;
        Config.DeviceInfo.scaledDensity = dm.scaledDensity;
    }

    public static String stringToMD5(String sourceString) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        char[] charArray = sourceString.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean checkNetworkState() {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) RedNetApp.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    public static String getImageUrl(String aid) {
        String url = Config.HOST + "/api/mobile/index.php?module=forumimage&size=960x960&version=2&ype=fixwr";
        String md5str = _md5(aid + "|" + "960|960");
        url = url + "&aid=" + aid + "&key=" + md5str;
        return url;
    }

}
