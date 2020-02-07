package com.custom.hermes.aidlbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Ysw on 2020/2/7.
 */
public class HermesResponse implements Parcelable {
    private String data;

    public String getData() {
        return data;
    }

    public HermesResponse(String data) {
        this.data = data;
    }

    protected HermesResponse(Parcel in) {
        data = in.readString();
    }

    public static final Creator<HermesResponse> CREATOR = new Creator<HermesResponse>() {
        @Override
        public HermesResponse createFromParcel(Parcel in) {
            return new HermesResponse(in);
        }

        @Override
        public HermesResponse[] newArray(int size) {
            return new HermesResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }
}
