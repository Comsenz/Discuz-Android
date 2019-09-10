package cn.tencent.DiscuzMob.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.IOException;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2016/3/21.
 */
public class ImageChooserDialog extends Dialog implements View.OnClickListener {

    public static final int REQUEST_CODE_PICK = 111;
    public static final int REQUEST_CODE_CAMERA = 999;
    public static final int REQUEST_CODE_CROP = 3;
    private Activity mActivity;
    private String mCurrentPhotoPath;
    private String mFixPhotoPath;
    private File newFile;
    private Uri contentUri;

    public ImageChooserDialog(Activity activity) {
        this(activity, R.style.Theme_Rednet_Dialog);
    }

    public ImageChooserDialog(Activity activity, int theme) {
        super(activity, theme);
        setOwnerActivity(mActivity = activity);
        setCanceledOnTouchOutside(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_chooser);
        findViewById(R.id.photo).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
    }

    private File createImageFile() throws IOException {
        File photoFile = new File(mActivity.getCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!photoFile.getParentFile().exists()) photoFile.getParentFile().mkdirs();
        return photoFile;
    }

    public Uri getFixPhotoUri() {
        try {
            File file = createImageFile();
            mFixPhotoPath = file.getAbsolutePath();
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public String getFixPhotoPath() {
        return mFixPhotoPath;
    }

    private static final int NEED_CAMERA = 200;
    private static final int RESULT_PICK = 201;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                if (ContextCompat.checkSelfPermission(RedNetApp.getInstance(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_PICK);
                } else { //选择图片
                    mActivity.startActivityForResult(new Intent(Intent.ACTION_PICK)
                            .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"), REQUEST_CODE_PICK);
                    dismiss();
                }

//                if (Build.VERSION.SDK_INT >= 23) {
//                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.
//                            checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
//                    } else {
//                        mActivity.startActivityForResult(new Intent(Intent.ACTION_PICK)
//                                .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"), REQUEST_CODE_PICK);
//                        dismiss();
//
//                    }
//
//                }

                break;
            case R.id.camera:
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 221);
                        return;
                    } else {
                        try {
                            File photoFile = createImageFile();
                            Uri uri = FileProvider.getUriForFile(mActivity, "com.zslt.takephoto.fileprovider", photoFile);
                            mCurrentPhotoPath = photoFile.getAbsolutePath();
                            Log.e("TAG", "mCurrentPhotoPath=" + mCurrentPhotoPath);
                            if (photoFile != null) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定URI
                                mActivity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                            }
                        } catch (IOException ex) {
                            RednetUtils.toast(mActivity, "创建照片文件失败");
                        }
                    }
                } else {
                    try {

                        File photoFile = createImageFiles();
                        if (photoFile != null) {
                            mCurrentPhotoPath = photoFile.getAbsolutePath();
                            mActivity.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)), REQUEST_CODE_CAMERA);
                        } else {
                            RednetUtils.toast(mActivity, "创建照片文件失败");
                        }
                    } catch (IOException ex) {
                        RednetUtils.toast(mActivity, "创建照片文件失败");
                    }
                }

                dismiss();
                break;
            default:
                dismiss();
                return;
        }
    }

    private File createImageFiles() throws IOException {
//        String timeStamp = RednetUtils.DateFormat.FILETIMESTAMP.dateFormat.format(new Date());
//        String imageFileName = timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
        String path = Environment.getExternalStorageDirectory().toString()
                + "/zslt";
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File image = new File(path1, System.currentTimeMillis() + ".jpg");
        return image;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            dismiss();
            return true;
        } else
            return super.onTouchEvent(event);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getWindow() != null && isShowing()) {
            dismiss();
        }
    }


}
