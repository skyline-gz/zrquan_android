package com.zrquan.mobile.support.util;

import java.util.regex.Pattern;

public class RegUtil {
    private RegUtil (){}

    private Pattern emailPattern = Pattern.compile("(([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)*\\.([a-zA-Z0-9_\\-\\.])+)");
    private Pattern ipPattern = Pattern.compile("((http|ftp|https):\\/\\/((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
//    private Pattern phonePattern = Pattern.compile("(?<!\\d)((((\\+86)|(86))?(1[3|4|5|7|8])\\d{9})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})$))(?!\\d)");
    private Pattern mobilePattern = Pattern.compile("(1\\d{10})");
    private Pattern passwordPattern = Pattern.compile("([a-zA-Z0-9]{8,20})");
    private Pattern urlPattern = Pattern.compile("(([Hh][Tt][Tt][Pp]://)|([Hh][Tt][Tt][Pp][Ss]://))?(((([a-zA-Z0-9_\\-])+[.]){1,}([a-zA-Z0-9\\-]+)(((\\/[\\~]*|\\[\\~]*)([a-zA-Z0-9_\\-])+)|[.]([a-zA-Z0-9_\\-])+)*(((([?|#]([a-zA-Z0-9_\\-])+){1}[=]*))*(([a-zA-Z0-9_\\-])+){1}([\\&]*([a-zA-Z0-9_\\-])*[\\=]*([a-zA-Z0-9_\\-])*[\\%]*([a-zA-Z0-9_\\-])*)*)*))|(www){1}([[a-zA-Z0-9_\\-]\\.\\-/:]+)([a-zA-Z0-9\\-]+)");

    public static RegUtil getInstance() {
        return InstanceInner.instance;
    }

    public Pattern getEmailPattern() {
        return this.emailPattern;
    }

    public Pattern getIpPattern() {
        return this.ipPattern;
    }

    public Pattern getMobilePattern() {
        return this.mobilePattern;
    }
    public Pattern getPasswordPattern() {
        return this.passwordPattern;
    }

    public Pattern getUrlPattern() {
        return this.urlPattern;
    }

    //使用类级内部类保证　lazy loading 见http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
    private static class InstanceInner {
        private static RegUtil instance = new RegUtil();
    }
}