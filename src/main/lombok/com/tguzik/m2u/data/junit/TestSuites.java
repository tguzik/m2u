package com.tguzik.m2u.data.junit;

import java.util.List;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

/**
 * Represents collection of all of the tests ran
 *
 * @author Tomek
 */
@Data
@XStreamAlias( "testsuites" )
public class TestSuites {
    @XStreamAsAttribute
    @XStreamAlias( "disabled" )
    private int disabledTests;

    @XStreamAsAttribute
    @XStreamAlias( "errors" )
    private int errorsInTests;

    @XStreamAsAttribute
    @XStreamAlias( "failures" )
    private int failuresInTests;

    @XStreamAsAttribute
    @XStreamAlias( "name" )
    private String testGroupName;

    @XStreamAsAttribute
    @XStreamAlias( "tests" )
    private int totalTests;

    @XStreamAsAttribute
    @XStreamAlias( "time" )
    private long totalTimeSpent; // miliseconds

    @XStreamImplicit
    @XStreamAlias( "testsuite" )
    private List<TestSuite> testSuites;

    @Override
    public String toString() {
        return BaseObject.toString( this, BaseObject.MULTILINE_NO_ADDRESS_STYLE );
    }
}
