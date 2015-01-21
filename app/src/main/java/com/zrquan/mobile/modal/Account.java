package com.zrquan.mobile.modal;

import android.os.Parcel;
import android.os.Parcelable;

//import org.qii.weiciyuan.support.utils.ObjectToStringUtility;


public class Account implements Parcelable {

    private String access_token;
    private long expires_time;
//    private UserBean info;
    private boolean black_magic;
    private int navigationPosition;

//    public String getUid() {
//        return (info != null ? info.getId() : "");
//    }
//
//    public String getUsernick() {
//        return (info != null ? info.getScreen_name() : "");
//    }
//
//    public String getAvatar_url() {
//        return (info != null ? info.getProfile_image_url() : "");
//    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(long expires_time) {
        this.expires_time = expires_time;
    }

//    public UserBean getInfo() {
//        return info;
//    }

//    public void setInfo(UserBean info) {
//        this.info = info;
//    }

    public boolean isBlack_magic() {
        return black_magic;
    }

    public void setBlack_magic(boolean black_magic) {
        this.black_magic = black_magic;
    }

    public int getNavigationPosition() {
        return navigationPosition;
    }

    public void setNavigationPosition(int navigationPosition) {
        this.navigationPosition = navigationPosition;
    }

//    @Override
//    public String toString() {
//        return ObjectToStringUtility.toString(this);
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeLong(expires_time);
        dest.writeInt(navigationPosition);
        dest.writeBooleanArray(new boolean[]{this.black_magic});
//        dest.writeParcelable(info, flags);
    }

    public static final Creator<Account> CREATOR =
            new Creator<Account>() {
                public Account createFromParcel(Parcel in) {
                    Account account = new Account();
                    account.access_token = in.readString();
                    account.expires_time = in.readLong();
                    account.navigationPosition = in.readInt();

                    boolean[] booleans = new boolean[1];
                    in.readBooleanArray(booleans);
                    account.black_magic = booleans[0];

//                    accountBean.info = in.readParcelable(UserBean.class.getClassLoader());

                    return account;
                }

                public Account[] newArray(int size) {
                    return new Account[size];
                }
            };

//    @Override
//    public boolean equals(Object o) {
//
//        return o instanceof AccountBean
//                && !TextUtils.isEmpty(((AccountBean) o).getUid())
//                && ((AccountBean) o).getUid().equalsIgnoreCase(getUid());
//    }

//    @Override
//    public int hashCode() {
//        return info.hashCode();
//    }
}
