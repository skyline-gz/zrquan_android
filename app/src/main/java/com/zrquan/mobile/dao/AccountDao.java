package com.zrquan.mobile.dao;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zrquan.mobile.modal.Account;
import com.zrquan.mobile.support.util.PreferencesUtils;

public class AccountDao {

    public final String ACCOUNT_STORE_KEY = "Account";

    public Account loadAccount(Context context) {
        String accountJson = PreferencesUtils.getString(context, ACCOUNT_STORE_KEY);
        if(!TextUtils.isEmpty(accountJson)) {
            return new Gson().fromJson(accountJson, Account.class);
        } else {
            return new Account();
        }
    }

    public void saveAccount(Context context, Account account) {
        String accountJson = new Gson().toJson(account);
        PreferencesUtils.putString(context, ACCOUNT_STORE_KEY, accountJson);
    }
}

