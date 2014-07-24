package com.tguzik.m2u.data.jtl;

import com.tguzik.util.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("assertionResult")
public class AssertionResult extends BaseObject {
    @XStreamAlias("name")
    private String name;

    @XStreamAlias("failure")
    private boolean failure;

    @XStreamAlias("error")
    private boolean error;

    @XStreamAlias("failureMessage")
    private String failureMessage;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure( boolean failure ) {
        this.failure = failure;
    }

    public boolean isError() {
        return error;
    }

    public void setError( boolean error ) {
        this.error = error;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage( String failureMessage ) {
        this.failureMessage = failureMessage;
    }
}
