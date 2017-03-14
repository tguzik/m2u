package com.tguzik.m2u.data;

import static com.tguzik.util.CollectionUtils.safe;

import java.util.function.BiFunction;

import com.tguzik.m2u.data.jtl.AssertionResult;
import com.tguzik.m2u.data.jtl.BaseSample;
import com.tguzik.m2u.data.jtl.HttpSample;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.Failure;
import com.tguzik.m2u.data.junit.TestCase;
import com.tguzik.m2u.data.junit.TestSuite;
import com.tguzik.m2u.data.junit.TestSuites;

public class JtlToJunitConverter implements BiFunction<String, TestResults, TestSuites> {

    @Override
    public TestSuites apply( String testSuiteName, TestResults input ) {
        final TestSuites suites = new TestSuites();
        final TestSuite singleSuite = new TestSuite();

        suites.setTestGroupName( testSuiteName );
        singleSuite.setName( testSuiteName );

        safe( input.getSamples() ).stream()
                                  .map( this::convertSample )
                                  .forEach( singleSuite::addTestCase );

        safe( input.getHttpSamples() ).stream()
                                      .map( this::convertHttpSample )
                                      .forEach( singleSuite::addTestCase );

        suites.getTestSuites().add( singleSuite );

        return suites;
    }

    private TestCase convertSample( BaseSample input ) {
        TestCase tc = new TestCase();

        tc.setAssertions( safe( input.getAssertionResults() ).size() );
        tc.setClassname( input.getThreadName() );
        tc.setTestName( input.getThreadName() );
        tc.setTotalTimeSpentInSeconds( input.getElapsedTime() );
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

