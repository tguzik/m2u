package com.tguzik.m2u.junit;

import java.util.List;

import com.tguzik.m2u.jtl.JTLAssertionElement;
import com.tguzik.m2u.jtl.JTLDocument;
import com.tguzik.m2u.jtl.JTLElementSample;
import com.tguzik.util.XMLStringBuilder;

/**
 * @see http://stackoverflow.com/questions/4922867/junit-xml-format-specification-that-hudson-supports
 */
public class JunitXMLBuilder {
    static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    static final String JUNIT_TAG_TESTCASE = "testcase";
    static final String JUNIT_ATR_CLASSNAME = "classname";
    static final String JUNIT_ATR_LABEL = "name";
    static final String JUNIT_ATR_ELAPSED_TIME = "time";
    static final String JUNIT_TAG_SYSTEM_OUT = "system-out";
    static final String JUNIT_TAG_SYSTEM_ERR = "system-err";
    static final String JUNIT_TAG_ERROR = "error";
    static final String JUNIT_TAG_FAILURE = "failure";
    static final String JUNIT_ATR_MESSAGE = "message";
    static final String JUNIT_ATR_TYPE = "type";
    static final String JUNIT_ATR_DISABLED = "disabled";
    static final String JUNIT_TAG_TESTSUITES = "testsuites";
    static final String JUNIT_ATR_ERRORS = "errors";
    static final String JUNIT_ATR_FAILURES = "failures";
    static final String JUNIT_ATR_HOSTNAME = "hostname";
    static final String JUNIT_ATR_NAME = "name";
    static final String JUNIT_ATR_TESTS = "tests";
    static final String JUNIT_ATR_TIME = "time";
    static final String JUNIT_ATR_TIMESTAMP = "timestamp";

    private final XMLStringBuilder sb;

    public JunitXMLBuilder( final JTLDocument jtl ) {
        sb = new XMLStringBuilder( " " );
        sb.appendRawValue( XML_HEADER );
        buildTestsuiteElement( jtl );
    }

    public String getXML() {
        return sb.toXML();
    }

    void buildTestsuiteElement( final JTLDocument jtl ) {
        sb.openTag( JUNIT_TAG_TESTSUITES );
        sb.appendAttribute( JUNIT_ATR_TESTS, jtl.getElements().size() );
        sb.appendAttribute( JUNIT_ATR_ERRORS, jtl.getElements().size() - jtl.getNumberOfSuccededTests() );
        sb.appendAttribute( JUNIT_ATR_FAILURES, 0 );
        sb.appendAttribute( JUNIT_ATR_NAME, jtl.getDocumentName() );
        sb.appendAttribute( JUNIT_ATR_TIME, Double.valueOf( jtl.getTotalTimeInMiliseconds() ) / 1000 );
        sb.appendChildren();

        for ( JTLElementSample test : jtl.getElements() ) {
            buildTestElement( test, jtl.getDocumentName() );
        }

        sb.closeTag( JUNIT_TAG_TESTSUITES );
    }

    void buildTestElement( final JTLElementSample test, final String testName ) {
        sb.openTag( JUNIT_TAG_TESTCASE );
        sb.appendAttribute( JUNIT_ATR_CLASSNAME, testName );
        sb.appendAttribute( JUNIT_ATR_NAME, test.getLabel() );
        sb.appendAttribute( JUNIT_ATR_TIME, Double.valueOf( test.getElapsedTime() ) / 1000 );
        sb.appendChildren();

        buildSystemOutInTest( test );
        buildSystemErrInTest( test.getAssertions() );
        buildErrorInTest( test.getFailedAssertions() );

        sb.closeTag( JUNIT_TAG_TESTCASE );
    }

    void buildSystemOutInTest( JTLElementSample test ) {
        final StringBuilder rawSB = new StringBuilder();

        rawSB.append( addField( "Thread name", test.getThreadName() ) );
        rawSB.append( addField( "Hostname", test.getHostname() ) );
        rawSB.append( addField( "URL", test.getUrl() ) );
        rawSB.append( addField( "Method", test.getMethod() ) );
        rawSB.append( addField( "Cookies", test.getCookies() ) );
        rawSB.append( addField( "Request header", test.getRequestHeader() ) );
        rawSB.append( addField( "Query string", test.getQueryString() ) );
        rawSB.append( addField( "HTTP-Response-code", test.getHttpResponseCode() ) );
        rawSB.append( addField( "HTTP-Response-message", test.getHttpResponseMessage() ) );
        rawSB.append( addField( "HTTP-Response-size", test.getBytes() ) );
        rawSB.append( addField( "Latency miliseconds", test.getLatency() ) );
        rawSB.append( addField( "Response header", test.getResponseHeader() ) );
        rawSB.append( addField( "Response data", test.getResponseData() ) );
        rawSB.append( addField( "Sampler data", test.getSamplerData() ) );
        rawSB.append( "\n" );

        sb.openTag( JUNIT_TAG_SYSTEM_OUT );
        sb.appendChildren();
        sb.appendCDATA( rawSB.toString() );
        sb.closeTag( JUNIT_TAG_SYSTEM_OUT );
    }

    void buildErrorInTest( final List<JTLAssertionElement> assertions ) {
        final StringBuilder rawSB = new StringBuilder();
        if ( assertions.isEmpty() ) {
            return;
        }

        for ( JTLAssertionElement assertion : assertions ) {
            rawSB.append( "\n--Assertion--" );
            rawSB.append( addField( "Name", assertion.getName() ) );
            rawSB.append( addField( "Error", assertion.getError() ) );
            rawSB.append( addField( "Failure", assertion.getFailure() ) );
            rawSB.append( addField( "Message", assertion.getFailureMessage() ) );
        }

        sb.openTag( JUNIT_TAG_ERROR );
        sb.appendChildren();
        sb.appendCDATA( rawSB.toString() );
        sb.closeTag( JUNIT_TAG_ERROR );
    }

    void buildSystemErrInTest( final List<JTLAssertionElement> assertions ) {
        final StringBuilder rawSB = new StringBuilder();
        if ( assertions.isEmpty() ) {
            return;
        }

        for ( JTLAssertionElement assertion : assertions ) {
            rawSB.append( "\n---------" );
            rawSB.append( addField( "Name", assertion.getName() ) );
            rawSB.append( addField( "Error", assertion.getError() ) );
            rawSB.append( addField( "Failure", assertion.getFailure() ) );
            rawSB.append( addField( "Message", assertion.getFailureMessage() ) );
        }

        sb.openTag( JUNIT_TAG_SYSTEM_ERR );
        sb.appendChildren();
        sb.appendCDATA( rawSB.toString() );
        sb.closeTag( JUNIT_TAG_SYSTEM_ERR );
    }

    String addField( final String fieldName, final Object message ) {
        final int fieldLength = fieldName.trim().length() + 2;
        final String padding = getPadding( fieldLength );
        final String msg = String.valueOf( message ).replace( '\0', ' ' ).replace( "\n", padding );

        return "\n" + fieldName + ": " + msg;
    }

    String getPadding( final int paddingLength ) {
        final StringBuilder sb = new StringBuilder();
        sb.append( "\n" );
        for ( int i = 0; i < paddingLength; i++ ) {
            sb.append( " " );
        }
        return sb.toString();
    }
}
