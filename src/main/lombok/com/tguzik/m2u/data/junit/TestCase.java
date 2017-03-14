package com.tguzik.m2u.data.junit;

import java.util.List;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

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

    @XStreamImplicit
    @XStreamAlias( "error" )
    private List<Error> errors;

    @XStreamImplicit
    @XStreamAlias( "failure" )
    private List<Failure> failures;

    @XStreamImplicit( itemFieldName = "system-out" )
    @XStreamAlias( "system-out" )
    private List<String> systemOut;

    @XStreamImplicit
    @XStreamAlias( "system-err" )
    private List<String> systemErr;

    @Override
    public String toString() {
        return BaseObject.toString( this, BaseObject.MULTILINE_NO_ADDRESS_STYLE );
    }
}
