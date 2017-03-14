package com.tguzik.m2u.data.junit;

import java.util.ArrayList;
import java.util.List;

import com.tguzik.m2u.application.MultilineLfNoAddressStyle;
import com.tguzik.objects.BaseObject;
import com.tguzik.tests.Normalize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.Singular;

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

    @Singular
    @XStreamImplicit
    @XStreamAlias( "testsuite" )
    private final List<TestSuite> testSuites = new ArrayList<>();

    @Override
    public String toString() {
        return Normalize.newLines( BaseObject.toString( this, MultilineLfNoAddressStyle.INSTANCE ) );
    }
}
