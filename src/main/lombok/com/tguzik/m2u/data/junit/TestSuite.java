package com.tguzik.m2u.data.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tguzik.m2u.application.MultilineLfNoAddressStyle;
import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.Singular;

/**
 * @author Tomek
 * @see <pre>
 * http://stackoverflow.com/questions/4922867/junit-xml-format-specification-that-hudson-supports
 * </pre>
 */
@Data
@XStreamAlias( "testsuite" )
public class TestSuite {
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
    @XStreamAlias( "tests" )
    private int totalTests;

    @XStreamAsAttribute
    @XStreamAlias( "hostname" )
    private String hostname;

    @XStreamAsAttribute
    @XStreamAlias( "id" )
    private String id;

    @XStreamAsAttribute
    @XStreamAlias( "name" )
    private String name;

    @XStreamAsAttribute
    @XStreamAlias( "package" )
    private String packageName;

    @XStreamAsAttribute
    @XStreamAlias( "skipped" )
    private int skippedTests;

    @XStreamAsAttribute
    @XStreamAlias( "time" )
    private double timeSpentInSeconds;

    @XStreamAsAttribute
    @XStreamAlias( "timestamp" )
    private long timestamp;

    @Singular
    @XStreamAlias( "properties" )
    private final Map<String, String> properties = new HashMap<>();

    @Singular
    @XStreamImplicit
    @XStreamAlias( "testcase" )
    private final List<TestCase> testCases = new ArrayList<>();

    public void addTestCase( TestCase tc ) {
        this.errorsInTests += tc.getErrors().size();
        this.failuresInTests += tc.getFailures().size();
        this.timeSpentInSeconds += tc.getTotalTimeSpentInSeconds();
        this.totalTests++;
        this.testCases.add( tc );
    }

    @Override
    public String toString() {
        return BaseObject.toString( this, MultilineLfNoAddressStyle.INSTANCE );
    }
}
