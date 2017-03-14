package com.tguzik.m2u.data.jtl;

import com.tguzik.m2u.application.MultilineLfNoAddressStyle;
import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias( "assertionResult" )
public class AssertionResult {
    @XStreamAlias( "name" )
    private String name;

    @XStreamAlias( "failure" )
    private boolean failure;

    @XStreamAlias( "error" )
    private boolean error;

    @XStreamAlias( "failureMessage" )
    private String failureMessage;

    @Override
    public String toString() {
        return BaseObject.toString( this, MultilineLfNoAddressStyle.INSTANCE );
    }
}
