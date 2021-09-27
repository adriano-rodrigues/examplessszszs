package com.monitoratec.tokenservice.vtswalletservice.exception;

public class XPayTokenException extends Exception {
    public XPayTokenException () {
        super();
    }
    public XPayTokenException (String message) {
        super(message);
    }
    public XPayTokenException (Exception e) {
        super(e);
    }
}