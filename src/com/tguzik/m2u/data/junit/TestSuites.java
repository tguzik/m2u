package com.tguzik.m2u.data.junit;

import java.util.List;

import com.google.common.collect.Lists;
import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Represents collection of all of the tests ran
 *
 * @author Tomek
 */
@XStreamAlias("testsuites")
public class TestSuites extends BaseObject {
    @XStreamAsAttribute
    @XStreamAlias("disabled")
    private int disabledTests;

    @XStreamAsAttribute
    @XStreamAlias("errors")
    private int errorsInTests;

    @XStreamAsAttribute
    @XStreamAlias("failures")
    private int failuresInTests;

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String testGroupName;

    @XStreamAsAttribute
    @XStreamAlias("tests")
    private int totalTests;

    @XStreamAsAttribute
    @XStreamAlias("time")
    private long totalTimeSpent; // miliseconds

    @XStreamImplicit
    @XStreamAlias("testsuite")
    private final List<TestSuite> testSuites;

    public TestSuites() {
        this.testSuites = Lists.newArrayList();
    }

    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    public void addTestSuite( TestSuite ts ) {
        this.disabledTests += ts.getDisabledTests();
        this.errorsInTests += ts.getErrorsInTests();
        this.failuresInTests += ts.getFailuresInTests();
        this.totalTests += ts.getTotalTests();

        this.testSuites.add( ts );
    }

    public int getDisabledTests() {
        return disabledTests;
    }

    public void setDisabledTests( int disabledTests ) {
        this.disabledTests = disabledTests;
    }

    public int getErrorsInTests() {
        return errorsInTests;
    }

    public void setErrorsInTests( int errorsInTests ) {
        this.errorsInTests = errorsInTests;
    }

    public int getFailuresInTests() {
        return failuresInTests;
    }

    public void setFailuresInTests( int failuresInTests ) {
        this.failuresInTests = failuresInTests;
    }

    public String getTestGroupName() {
        return testGroupName;
    }

    public void setTestGroupName( String testsuitesGroupName ) {
        this.testGroupName = testsuitesGroupName;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests( int totalTests ) {
        this.totalTests = totalTests;
    }

    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent( long totalTimeSpent ) {
        this.totalTimeSpent = totalTimeSpent;
    }

    @Override
    public String toString() {
        return toString( MULTILINE_NO_ADDRESS_STYLE );
    }
}
