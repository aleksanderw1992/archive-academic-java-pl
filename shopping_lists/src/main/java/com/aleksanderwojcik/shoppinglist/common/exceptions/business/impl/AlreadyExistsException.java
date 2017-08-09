package com.aleksanderwojcik.shoppinglist.common.exceptions.business.impl;

import com.aleksanderwojcik.shoppinglist.common.exceptions.business.BusinessException;

/**
 * Created by AXELA on 2014-11-30.
 */
public class AlreadyExistsException extends BusinessException {

    public AlreadyExistsException(String source, int messageResourceCode) {
        super(source, messageResourceCode);
    }
}
