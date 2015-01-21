package com.zrquan.mobile.modal;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    private String accessToken;
    private int navigationPosition;

    public boolean isLogin() {
        return false;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeInt(navigationPosition);
    }

    public static final Creator<Account> CREATOR =
            new Creator<Account>() {
                public Account createFromParcel(Parcel in) {
                    Account account = new Account();
                    account.accessToken = in.readString();
                    account.navigationPosition = in.readInt();

                    boolean[] booleans = new boolean[1];
                    in.readBooleanArray(booleans);
                    return account;
                }

                public Account[] newArray(int size) {
                    return new Account[size];
                }
            };
}
