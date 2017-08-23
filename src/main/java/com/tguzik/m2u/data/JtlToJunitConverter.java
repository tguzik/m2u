package com.tguzik.m2u.data;

import static com.tguzik.util.CollectionUtils.safe;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import com.tguzik.m2u.constants.M2UConstants;
import com.tguzik.m2u.data.jtl.AssertionResult;
import com.tguzik.m2u.data.jtl.BaseSample;
import com.tguzik.m2u.data.jtl.HttpSample;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.Failure;
import com.tguzik.m2u.data.junit.TestCase;
import com.tguzik.m2u.data.junit.TestSuite;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.params.ControlParams;

public class JtlToJunitConverter implements BiFunction<String, TestResults, TestSuites> {

    @Override
    public TestSuites apply( String testSuiteName, TestResults input ) {
        final TestSuites suites = new TestSuites();
        final TestSuite singleSuite = new TestSuite();

        suites.setTestGroupName( testSuiteName );
        singleSuite.setName( testSuiteName );
        
        String filter = ControlParams.getParam(M2UConstants.JUNIT_FILTER_SWITCH_NAME);
        Predicate<BaseSample> p = a -> true;
        if(filter != null && filter.equalsIgnoreCase(M2UConstants.TRUE)){
        	p = a->a.getLabel().contains(M2UConstants.JUNIT_DECORATOR);
        }

        safe( input.getSamples() ).stream()
        						  .filter(p)
                                  .map( this::convertSample )
                                  .forEach( singleSuite::addTestCase );

        safe( input.getHttpSamples() ).stream()
        							  .filter(p)
                                      .map( this::convertHttpSample )
                                      .forEach( singleSuite::addTestCase );

        suites.getTestSuites().add( singleSuite );

        return suites;
    }

    private TestCase convertSample( BaseSample input ) {
        TestCase tc = new TestCase();

        tc.setAssertions( safe( input.getAssertionResults() ).size() );
        tc.setClassname( input.getThreadName() );
        
        String filter = ControlParams.getParam(M2UConstants.JUNIT_FILTER_SWITCH_NAME);
        if(filter != null && filter.equalsIgnoreCase(M2UConstants.TRUE)){
        	tc.setTestName( input.getLabel().replace(M2UConstants.JUNIT_DECORATOR, M2UConstants.BLANK) );
        }else{
        	tc.setTestName( input.getThreadName() );
        }
        
        tc.setTotalTimeSpentInSeconds( input.getElapsedTime()/1000 );
        tc.getSystemOut().add( input.toString() );

        safe( input.getAssertionResults() ).stream()
                                           .filter( AssertionResult::isError )
                                           .map( this::convertErrorAssertion )
                                           .forEach( x -> tc.getErrors().add( x ) );

        safe( input.getAssertionResults() ).stream()
                                           .filter( AssertionResult::isFailure )
                                           .map( this::convertFailureAssertion )
                                           .forEach( x -> tc.getFailures().add( x ) );

        return tc;
    }

    private TestCase convertHttpSample( HttpSample sample ) {
        return convertSample( sample );
    }

    private Failure convertFailureAssertion( AssertionResult ar ) {
        Failure failure = new Failure();

        failure.setMessage( ar.getFailureMessage() );
        failure.setType( ar.getName() );

        return failure;
    }

    private com.tguzik.m2u.data.junit.Error convertErrorAssertion( AssertionResult ar ) {
        com.tguzik.m2u.data.junit.Error error = new com.tguzik.m2u.data.junit.Error();

        error.setMessage( ar.getFailureMessage() );
        error.setType( ar.getName() );

        return error;
    }

}

