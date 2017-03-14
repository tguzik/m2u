package com.tguzik.m2u.data.junit;

import java.util.ArrayList;
import java.util.List;

import com.tguzik.m2u.application.MultilineLfNoAddressStyle;
import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.Singular;

@Data
@XStreamAlias( "testcase" )
public class TestCase {
    @XStreamAsAttribute
    @XStreamAlias( "assertions" )
    private int assertions;

    @XStreamAsAttribute
    @XStreamAlias( "classname" )
    private String classname;

    @XStreamAsAttribute
    @XStreamAlias( "name" )
    private String testName;

    @XStreamAsAttribute
    @XStreamAlias( "status" )
    private String status;

    @XStreamAsAttribute
    @XStreamAlias( "time" )
    private double totalTimeSpentInSeconds;

    @Singular
    @XStreamImplicit
    @XStreamAlias( "error" )
    private final List<Error> errors = new ArrayList<>();

    @Singular
    @XStreamImplicit
    @XStreamAlias( "failure" )
    private final List<Failure> failures = new ArrayList<>();

    @Singular
    @XStreamImplicit( itemFieldName = "system-out" )
    @XStreamAlias( "system-out" )
    private final List<String> systemOut = new ArrayList<>();

    @Singular
    @XStreamImplicit
    @XStreamAlias( "system-err" )
    private final List<String> systemErr = new ArrayList<>();

    @Override
    public String toString() {
        return BaseObject.toString( this, MultilineLfNoAddressStyle.INSTANCE );
    }
}
