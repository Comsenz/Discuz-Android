package cn.tencent.DiscuzMob.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 2017/4/25.
 */

public class TypeItem implements Parcelable {

    public String index;
    public String name;

    public TypeItem(String index, String name) {
        this.index = index;
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.index);
        dest.writeString(this.name);
    }

    protected TypeItem(Parcel in) {
        this.index = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<TypeItem> CREATOR = new Parcelable.Creator<TypeItem>() {
        @Override
        public TypeItem createFromParcel(Parcel source) {
            return new TypeItem(source);
        }

        @Override
        public TypeItem[] newArray(int size) {
            return new TypeItem[size];
        }
    };
}
