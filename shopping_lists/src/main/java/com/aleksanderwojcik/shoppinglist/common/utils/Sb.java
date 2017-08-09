package com.aleksanderwojcik.shoppinglist.common.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by AXELA on 2014-11-30.
 */
public class Sb {

    protected StringBuilder stringBuilder;

    public Sb() {
        stringBuilder = new StringBuilder();
    }
    public Sb append(java.lang.Object o) {
        this.stringBuilder.append(o.toString());
        return this;
    }
    public Sb appendToNull(java.lang.Object o) {
        if (o == null) return this.append("null");
        this.stringBuilder.append(o.toString());
        return this;
    }
    public Sb append(java.lang.String s) {
        this.stringBuilder.append(s);
        return this;
    }
    public Sb appendNotNull(java.lang.Object o) {
        if (o == null) throw new RuntimeException("SB : appendNotNull : Object o is null");
        this.stringBuilder.append(o.toString());
        return this;
    }
    public Sb appendToEmpty(java.lang.Object o) {
        if (o == null) return this;
        this.stringBuilder.append(o.toString());
        return this;
    }
    public Sb appendAndSpace(java.lang.String s) {
        return this.append(s)
                .space();
    }
    public Sb space() {
        return this.append(" ");
    }
    public Sb spaces(java.lang.String s) {
        return this.space().
                append(s)
                .space();
    }
    public Sb append(java.lang.CharSequence charSequence) {
        this.stringBuilder.append(charSequence);
        return this;
    }
    public Sb append(char[] chars) {
        this.stringBuilder.append(chars);
        return this;
    }
    public Sb append(boolean b) {
        this.stringBuilder.append(b);
        return this;
    }
    public Sb append(char c) {
        this.stringBuilder.append(c);
        return this;
    }
    public Sb append(int i) {
        this.stringBuilder.append(i);
        return this;
    }
    public Sb append(long l) {
        this.stringBuilder.append(l);
        return this;
    }
    public Sb append(float v) {
        this.stringBuilder.append(v);
        return this;
    }
    public Sb append(double v) {
        this.stringBuilder.append(v);
        return this;
    }
    public Sb append(List<Object> objects) {
        String toString = Arrays.toString(objects.toArray());
        return this.append(toString);
    }
    public Sb newline(int n) {
        for (int i = 0; i < n; i++) this.append("\n");
        return this;
    }
    public Sb emptyLine() {
        return this.append("\n\n");
    }
    public Sb tab() {
        return this.space(4);
    }
    public Sb space(int n) {
        for (int i = 0; i < n; i++)
            this.append(" ");
        return this;
    }
    public Sb tab(int n) {
        return this.space(4 * n);
    }
    public Sb timesbr(String s, int n) {
        return this.times(s, n).newline();
    }
    public Sb newline() {
        return this.append("\n");
    }
    public Sb times(String s, int n) {
        for (int i = 0; i < n; i++) {
            this.append(s);
        }
        return this;
    }
    public Sb sout() {
        System.out.println(this.toString());
        return this;
    }
    @Override
    public String toString() {
        return this.stringBuilder.toString();
    }
    public Sb log() {
        Log.w(this.getClass().getSimpleName(), this.toString());
        return this;
    }

    public Sb comma() {
        return this.append(", ");
    }
    public Sb dot() {
        return this.append(". ");
    }
    public Sb equals(String s1, int s2) {
        return equals(s1, s2 + "");
    }
    public Sb equals(String s1, String s2) {
        stringBuilder.append(StringUtils.equalsSing(s1, s2));
        return this;
    }
    public Sb equals(String s1, long s2) {
        return equals(s1, s2 + "");
    }
}
