package com.tguzik.m2u.data;

import static com.tguzik.util.CollectionUtils.safe;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tguzik.m2u.constants.M2UConstants;
import com.tguzik.m2u.data.jtl.AssertionResult;
import com.tguzik.m2u.data.jtl.BaseSample;
import com.tguzik.m2u.data.jtl.HttpSample;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.Error;
import com.tguzik.m2u.data.junit.Failure;
import com.tguzik.m2u.data.junit.TestCase;
import com.tguzik.m2u.data.junit.TestSuite;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.params.ControlParams;
import org.apache.commons.lang3.StringUtils;

public class JtlToJunitConverter implements BiFunction<String, TestResults, TestSuites> {

    @Override
    public TestSuites apply( String testSuiteName, TestResults input ) {
        final String filter = ControlParams.getParam( M2UConstants.JUNIT_FILTER_SWITCH_NAME );
        final TestSuite singleSuite = new TestSuite();
        final TestSuites suites = new TestSuites();
        final Predicate<BaseSample> p;

        suites.setTestGroupName( testSuiteName );
        singleSuite.setName( testSuiteName );

        if ( StringUtils.equalsIgnoreCase( M2UConstants.TRUE, filter ) ) {
            p = a -> a.getLabel().contains( M2UConstants.JUNIT_DECORATOR );
        }
        else {
            p = x -> true;
        }

        safe( input.getSamples() ).stream().filter( p ).map( this::convertSample ).forEach( singleSuite::addTestCase );
        safe( input.getHttpSamples() ).stream()
                                      .filter( p )
                                      .map( this::convertHttpSample )
                                      .forEach( singleSuite::addTestCase );

        suites.getTestSuites().add( singleSuite );

        return suites;
    }

    private TestCase convertSample( BaseSample input ) {
        final String filter = ControlParams.getParam( M2UConstants.JUNIT_FILTER_SWITCH_NAME );
        final String threadPlusLabel = Stream.of( StringUtils.trimToEmpty( input.getThreadName() ),
                                                  StringUtils.trimToEmpty( input.getLabel() ) )
                                             .filter( StringUtils::isNotEmpty )
                                             .collect( Collectors.joining( " - " ) );
        final TestCase tc = new TestCase();

        tc.setAssertions( safe( input.getAssertionResults() ).size() );
        tc.setClassname( threadPlusLabel );

        if ( StringUtils.equalsIgnoreCase( M2UConstants.TRUE, filter ) ) {
            tc.setTestName( input.getLabel().replace( M2UConstants.JUNIT_DECORATOR, M2UConstants.BLANK ) );
        }
        else {
            tc.setTestName( threadPlusLabel );
        }

        tc.setTotalTimeSpentInSeconds( ((double) input.getElapsedTime()) / 1000.0 );
        tc.getSystemOut().add( input.toString() );

        safe( input.getAssertionResults() ).stream()
                                           .filter( AssertionResult::isError )
                                           .map( this::convertErrorAssertion )
                                           .forEach( tc.getErrors()::add );

        safe( input.getAssertionResults() ).stream()
                                           .filter( AssertionResult::isFailure )
                                           .map( this::convertFailureAssertion )
                                           .forEach( tc.getFailures()::add );

        return tc;
    }

    private TestCase convertHttpSample( HttpSample sample ) {
        return convertSample( sample );
    }

    private Failure convertFailureAssertion( AssertionResult ar ) {
        final Failure failure = new Failure();

        failure.setMessage( ar.getFailureMessage() );
        failure.setType( ar.getName() );

        return failure;
    }

    private Error convertErrorAssertion( AssertionResult ar ) {
        final Error error = new Error();

        error.setMessage( ar.getFailureMessage() );
        error.setType( ar.getName() );

        return error;
    }

}

