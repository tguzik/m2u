package com.tguzik.m2u.data.junit;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("failure")
public class Failure extends BaseObject {
    @XStreamAsAttribute
    @XStreamAlias("message")
    private String message;

    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
