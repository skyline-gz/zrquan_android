package com.zrquan.mobile.support.util;

import java.util.regex.Pattern;

public class RegUtils {
    private RegUtils() {
    }

    private Pattern atPattern = Pattern.compile("@[\\w|#]+\\b");
    private Pattern emojiPattern = Pattern.compile("(\\[[\\w]+\\])");
    private Pattern emailPattern = Pattern.compile("(([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)*\\.([a-zA-Z0-9_\\-\\.])+)");
    private Pattern mobilePattern = Pattern.compile("(1\\d{10})");
    private Pattern passwordPattern = Pattern.compile("([a-zA-Z0-9]{8,20})");
    private Pattern verifyCodePattern = Pattern.compile("(\\d{6})");
    private Pattern trueNamePattern = Pattern.compile("[u4E00-u9FA5|a-zA-Z]{4,30}");
    private Pattern schoolNamePattern = Pattern.compile("[u4E00-u9FA5|a-zA-Z0-9_\\-]{1,30}");
    private Pattern ipPattern = Pattern.compile("((http|ftp|https):\\/\\/((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
    private Pattern urlPattern = Pattern.compile("(([Hh][Tt][Tt][Pp]://)|([Hh][Tt][Tt][Pp][Ss]://))?(((([a-zA-Z0-9_\\-])+[.]){1,}([a-zA-Z0-9\\-]+)(((\\/[\\~]*|\\[\\~]*)([a-zA-Z0-9_\\-])+)|[.]([a-zA-Z0-9_\\-])+)*(((([?|#]([a-zA-Z0-9_\\-])+){1}[=]*))*(([a-zA-Z0-9_\\-])+){1}([\\&]*([a-zA-Z0-9_\\-])*[\\=]*([a-zA-Z0-9_\\-])*[\\%]*([a-zA-Z0-9_\\-])*)*)*))|(www){1}([[a-zA-Z0-9_\\-]\\.\\-/:]+)([a-zA-Z0-9\\-]+)");

    public static RegUtils getInstance() {
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

    public Pattern getAtPattern() {
        return this.atPattern;
    }

    public Pattern getEmojiPattern() {
        return this.emojiPattern;
    }

    public Pattern getVerifyCodePattern() {
        return this.verifyCodePattern;
    }

    public Pattern getTrueNamePattern() {
        return this.trueNamePattern;
    }

    public Pattern getSchoolNamePattern() {
        return this.schoolNamePattern;
    }

    //使用类级内部类保证　lazy loading 见http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
    private static class InstanceInner {
        private static RegUtils instance = new RegUtils();
    }
}