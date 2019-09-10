package cn.tencent.DiscuzMob.request;

/**
 * Created by Havorld
 */

public class NetResult<T> {

    private T t;
    private int code;
    private Exception error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

}
