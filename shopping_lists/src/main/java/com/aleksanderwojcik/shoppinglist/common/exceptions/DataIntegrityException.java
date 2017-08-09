package com.aleksanderwojcik.shoppinglist.common.exceptions;

/**
 * Created by AXELA on 2014-11-30.
 */
public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException() {
    }
    public DataIntegrityException(String detailMessage) {
        super(detailMessage);
    }
}
