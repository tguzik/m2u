package com.tguzik.m2u.jtl;


public class JTLAssertionElement {
    private String  name;
    private Boolean failure;
    private Boolean error;
    private String  failureMessage;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Boolean getFailure() {
        return failure;
    }

    public void setFailure( Boolean failure ) {
        this.failure = failure;
    }

    public Boolean getError() {
        return error;
    }

    public void setError( Boolean error ) {
        this.error = error;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage( String failureMessage ) {
        this.failureMessage = failureMessage;
    }
}
