package cn.tencent.DiscuzMob.recordingutils;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by cg on 2017/5/26.
 */

public class URecorder implements IVoiceManager {

    private final String TAG = URecorder.class.getName();
    private String path;
    private MediaRecorder mRecorder;

    public URecorder(String path) {
        this.path = path;
        mRecorder = new MediaRecorder();
    }

    /*
     * 开始录音
     * @return boolean
     */
    @Override
    public boolean start() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        //设置音源为Micphone
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置封装格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(path);
        //设置编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        //录音
        return false;
    }

    /*
     * 停止录音
     * @return boolean
     */
    @Override
    public boolean stop() {
        if (mRecorder != null) {
            try {
                mRecorder.stop();
                mRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mRecorder = null;
            return false;
        }
        return false;
    }

}
