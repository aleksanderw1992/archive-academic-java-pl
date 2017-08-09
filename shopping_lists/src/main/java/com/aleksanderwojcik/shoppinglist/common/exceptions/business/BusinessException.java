package com.aleksanderwojcik.shoppinglist.common.exceptions.business;

/**
 * Created by AXELA on 2014-11-30.
 */
public abstract class BusinessException extends Exception {
    private int messageResourceCode;
    protected  BusinessException(String source, int messageResourceCode) {
        super(source);
        this.messageResourceCode = messageResourceCode;
    }
    public int getMessageResourceCode() {
        return messageResourceCode;
    }
}
