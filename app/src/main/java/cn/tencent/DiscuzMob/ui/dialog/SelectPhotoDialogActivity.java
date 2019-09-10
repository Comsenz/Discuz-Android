package cn.tencent.DiscuzMob.ui.dialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;

import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 19/5/6.
 * 选择照片
 */
public class SelectPhotoDialogActivity extends FragmentActivity implements View.OnClickListener {

    private RelativeLayout mTakePhotoView, mSelectPhotoView, mCancelView;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private String imgPath;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        mTakePhotoView = (RelativeLayout) findViewById(R.id.take_photo_view);
        mSelectPhotoView = (RelativeLayout) findViewById(R.id.select_photo_view);
        mCancelView = (RelativeLayout) findViewById(R.id.cancel_view);

        mTakePhotoView.setOnClickListener(this);
        mSelectPhotoView.setOnClickListener(this);
        mCancelView.setOnClickListener(this);
        findViewById(R.id.exit_select_layout).setOnClickListener(this);//除可见界面外点击退出
    }

    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    File photoFile;
    Uri uri;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_select_layout:
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.cancel_view:
                finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                break;
            case R.id.take_photo_view:
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 221);
                        return;
                    } else {
                        try {
                            photoFile = createImageFile();
                            uri = FileProvider.getUriForFile(this, "com.zslt.takephoto.fileprovider", photoFile);
                            mCurrentPhotoPath = photoFile.getAbsolutePath();
                            if (photoFile != null) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定URI
                                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                            }
                        } catch (IOException ex) {
                            RednetUtils.toast(this, "创建照片文件失败");
                        }
                    }
                } else {
                    try {

                        photoFile = createImageFiles();
                        if (photoFile != null) {
                            mCurrentPhotoPath = photoFile.getAbsolutePath();
                            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)), CAMERA_REQUEST_CODE);
                        } else {
                            RednetUtils.toast(this, "创建照片文件失败");
                        }
                    } catch (IOException ex) {
                        RednetUtils.toast(this, "创建照片文件失败");
                    }
                }
//                if (isSdcardExisting()) {
//                    imgPath = Config.LOCAL_DOWNLOAD_IMAGE_PATH + RednetUtils.getRandomString(6) + "img.jpg";
//                    File vFile = new File(imgPath);
//                    if (!vFile.exists()) {
//                        File vDirPath = vFile.getParentFile();
//                        vDirPath.mkdirs();
//                    }
//                    Uri uri = Uri.fromFile(vFile);
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//                } else {
//                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
//                            .show();
//                }
                break;
            case R.id.select_photo_view:
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                        checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                } else {
                    //提示用户开户权限 String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                }
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    private File createImageFile() throws IOException {
        File photoFile = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!photoFile.getParentFile().exists()) photoFile.getParentFile().mkdirs();
        return photoFile;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            Intent intent = new Intent();
            if (requestCode == IMAGE_REQUEST_CODE && data != null && data.getData() != null) {
                Log.e("TAG", "data.getData()=" + data.getData());
                intent.setData(data.getData());
            } else if (requestCode == CAMERA_REQUEST_CODE) {
//                if (Build.VERSION.SDK_INT >= 24) {
//                    Bitmap bitmap = null;
//                    try {
//                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    imageView.setImageBitmap(bitmap);
//                } else {
//                    Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
//                    imageView.setImageBitmap(bitmap);
//                }
                intent.putExtra("path", mCurrentPhotoPath);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case CAMERA_REQUEST_CODE:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    try {
                        photoFile = createImageFiles();
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)), CAMERA_REQUEST_CODE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    RednetUtils.toast(this, "请开启应用拍照权限");
                }
                break;
            case IMAGE_REQUEST_CODE:
                boolean albumAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (albumAccepted) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了 ToastUtil.show(context,"请开启应用拍照权限");
                }
                break;
        }
    }
}
