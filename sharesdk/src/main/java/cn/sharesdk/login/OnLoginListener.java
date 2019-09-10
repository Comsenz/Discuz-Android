package cn.sharesdk.login;

import android.os.Bundle;

public interface OnLoginListener {
    void onResult(boolean success, String platform, Bundle res);
}
