package com.custom.hermes.aidlbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Ysw on 2020/2/7.
 */
public class HermesRequest implements Parcelable {
    private String data;
    private int type;

    public String getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    protected HermesRequest(Parcel in) {
        data = in.readString();
        type = in.readInt();
    }

    public HermesRequest(String data, int type) {
        this.data = data;
        this.type = type;
    }

    public static final Creator<HermesRequest> CREATOR = new Creator<HermesRequest>() {
        @Override
        public HermesRequest createFromParcel(Parcel in) {
            return new HermesRequest(in);
        }

        @Override
        public HermesRequest[] newArray(int size) {
            return new HermesRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(type);
    }
}
