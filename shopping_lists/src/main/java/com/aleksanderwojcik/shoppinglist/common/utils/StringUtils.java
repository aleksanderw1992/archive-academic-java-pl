package com.aleksanderwojcik.shoppinglist.common.utils;

/**
 * Created by AXELA on 2014-11-28.
 */
public class StringUtils {
    public static String singleQuotation(String string) {
        return "'".concat(string).concat("'");
    }
    public static String trimNullToEmpty(String description) {
        return description == null ? "" : description;
    }
    public static String[] asArray(String... string) {
        return string;
    }
    public static String[] asArray(int... ints) {
        String[] strings = new String[ints.length];
        for (int i = 0; i < ints.length; i++) {
            strings[i] = "" + ints[i];
        }
        return strings;
    }
    public static String[] asArray(long... longs) {
        String[] strings = new String[longs.length];
        for (int i = 0; i < longs.length; i++) {
            strings[i] = "" + longs[i];
        }
        return strings;
    }
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }
    public static String equalsSing(String left, int right) {
        return equalsSing(left, right + "");
    }
    public static String equalsSing(String left, String right) {
        return left.concat("=").concat(right);
    }
    public static String equalsSing(String left, long right) {
        return equalsSing(left, right + "");
    }
    public static String equalsSing(int left, int right) {
        return equalsSing(left + "", right + "");
    }
    public static boolean notEqualsTrim(String s1, String s2) {
        return !equalsTrim(s1, s2);
    }
    public static boolean equalsTrim(String s1, String s2) {
        return s1.trim().equals(s2.trim());
    }
}
