package com.tguzik.m2u.data;

import static com.tguzik.util.CollectionUtils.safe;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.tguzik.m2u.application.ProgramOptions;
import com.tguzik.m2u.data.jtl.AssertionResult;
import com.tguzik.m2u.data.jtl.BaseSample;
import com.tguzik.m2u.data.jtl.HttpSample;
import com.tguzik.m2u.data.jtl.Sample;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.Failure;
import com.tguzik.m2u.data.junit.TestCase;
import com.tguzik.m2u.data.junit.TestSuite;
import com.tguzik.m2u.data.junit.TestSuites;

public class JtlToJunitConverter implements Function<TestResults, TestSuites>
{
    private static final Predicate<AssertionResult> FAILED_ASSERTIONS = new FailedAssertionPredicate();
    private static final Predicate<AssertionResult> ERRORED_ASSERTIONS = new ErroredOutAssertionPredicate();
    private final ProgramOptions programOptions;

    public JtlToJunitConverter( ProgramOptions programOptions ) {
        this.programOptions = programOptions;
    }

    @Override
    public TestSuites apply( TestResults jmeter ) {
        TestSuites suites = new TestSuites();
        TestSuite singleSuite = new TestSuite();

        suites.setTestGroupName(programOptions.getTestSuiteName());
        singleSuite.setName(programOptions.getTestSuiteName());

        for ( Sample sample : safe(jmeter.getSamples()) ) {
            TestCase tc = convertSample(sample);
            singleSuite.addTestCase(tc);
        }

        for ( HttpSample sample : safe(jmeter.getHttpSamples()) ) {
            TestCase tc = convertHttpSample(sample);
            singleSuite.addTestCase(tc);
        }

        suites.addTestSuite(singleSuite);

        return suites;
    }

    private TestCase convertSample( BaseSample sample ) {
        TestCase tc = new TestCase();

        tc.setAssertions(safe(sample.getAssertionResults()).size());
        tc.setClassname(sample.getThreadName());
        tc.setTestName(sample.getThreadName());
        tc.setTotalTimeSpent(sample.getElapsedTime());
        tc.addSystemOut(sample.toString());

        for ( AssertionResult ar : Iterables.filter(safe(sample.getAssertionResults()), ERRORED_ASSERTIONS) ) {
            tc.addError(convertErroredOutAssertion(ar));
        }

        for ( AssertionResult ar : Iterables.filter(safe(sample.getAssertionResults()), FAILED_ASSERTIONS) ) {
            tc.addFailure(convertFailedAssertion(ar));
        }

        return tc;
    }

    private Failure convertFailedAssertion( AssertionResult ar ) {
        Failure failure = new Failure();

        failure.setMessage(ar.getFailureMessage());
        failure.setType(ar.getName());

        return failure;
    }

    private com.tguzik.m2u.data.junit.Error convertErroredOutAssertion( AssertionResult ar ) {
        com.tguzik.m2u.data.junit.Error error = new com.tguzik.m2u.data.junit.Error();

        error.setMessage(ar.getFailureMessage());
        error.setType(ar.getName());

        return error;
    }

    private TestCase convertHttpSample( HttpSample sample ) {
        return convertSample(sample);
    }
}

class ErroredOutAssertionPredicate implements Predicate<AssertionResult>
{
    @Override
    public boolean apply( AssertionResult ar ) {
        return ar.isError();
    }
}

class FailedAssertionPredicate implements Predicate<AssertionResult>
{
    @Override
    public boolean apply( AssertionResult ar ) {
        return ar.isFailure();
    }
}
