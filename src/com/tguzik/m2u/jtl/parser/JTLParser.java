package com.tguzik.m2u.jtl.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.tguzik.m2u.jtl.JTLAssertionElement;
import com.tguzik.m2u.jtl.JTLDocument;
import com.tguzik.m2u.jtl.JTLElementSample;
import com.tguzik.util.parser.AbstractParser;
import com.tguzik.util.parser.ParsingException;

/**
 * This is a handler for elements in Jmeter's JTL output. The expected document layout looks similar to this:
 * 
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testResults version="1.2">
 *     <sample t="0" lt="0" ts="0" s="true" lb="Label" rc="200" rm="OK" tn="Thread Group" dt="text" de="" by="0" ng="0" na="0" hn="0">
 *       <responseHeader class="java.lang.String">text</responseHeader>
 *       <requestHeader class="java.lang.String">text</requestHeader>
 *       <responseData class="java.lang.String"></responseData>
 *       <samplerData class="java.lang.String">text</samplerData>
 *     </sample>
 *     <httpSample t="0" lt="0" ts="0" s="true" lb="Label" rc="200" rm="OK" tn="Thread Group" dt="text" de="utf-8" by="0" ng="0" na="0" hn="0">
 *       <assertionResult>
 *         <name>Assertion name</name>
 *         <failure>false</failure>
 *         <error>false</error>
 *         <failureMessage>text</failureMessage>
 *       </assertionResult>
 *       <responseHeader class="java.lang.String">text</responseHeader>
 *       <requestHeader class="java.lang.String">text</requestHeader>
 *       <responseData class="java.lang.String">text</responseData>
 *       <cookies class="java.lang.String">text</cookies>
 *       <method class="java.lang.String">text</method>
 *       <queryString class="java.lang.String">text</queryString>
 *       <java.net.URL>text</java.net.URL>
 *     </httpSample>
 *     
 *     <!-- more sample and httpSample elements -->
 *     
 * </testResults>
 * }
 * </pre>
 */
public class JTLParser extends AbstractParser<JTLDocument> {
    public static final Map<String, String> supportedElements;
    static {
        Map<String, String> map = new HashMap<String, String>();

        map.put( JTLParserConstants.ELEM_TEST_RESULTS, "TestResults" );
        map.put( JTLParserConstants.ELEM_SAMPLE, "Sample" ); // both elements use same handler
        map.put( JTLParserConstants.ELEM_HTTP_SAMPLE, "Sample" ); // both elements use same handler
        map.put( JTLParserConstants.ELEM_ASSERTION_RESULT, "AssertionResult" );
        map.put( JTLParserConstants.ELEM_NAME, "Name" );
        map.put( JTLParserConstants.ELEM_FAILURE, "Failure" );
        map.put( JTLParserConstants.ELEM_ERROR, "Error" );
        map.put( JTLParserConstants.ELEM_FAILURE_MESSAGE, "FailureMessage" );
        map.put( JTLParserConstants.ELEM_SAMPLER_DATA, "SamplerData" );
        map.put( JTLParserConstants.ELEM_REQUEST_HEADER, "RequestHeader" );
        map.put( JTLParserConstants.ELEM_QUERY_STRING, "QueryString" );
        map.put( JTLParserConstants.ELEM_RESPONSE_HEADER, "ResponseHeader" );
        map.put( JTLParserConstants.ELEM_RESPONSE_DATA, "ResponseData" );
        map.put( JTLParserConstants.ELEM_COOKIES, "Cookies" );
        map.put( JTLParserConstants.ELEM_METHOD, "Method" );
        map.put( JTLParserConstants.ELEM_URL, "URL" );

        supportedElements = Collections.unmodifiableMap( map );
    }

    private JTLAssertionElement assertion;
    private JTLElementSample sample;

    // See SAX Parser's behavior for characters() method before making any judgment :)
    private StringBuilder assertionName;
    private StringBuilder assertionFailure;
    private StringBuilder assertionError;
    private StringBuilder assertionFailureMessage;
    private StringBuilder samplerData;
    private StringBuilder requestHeader;
    private StringBuilder queryString;
    private StringBuilder responseHeader;
    private StringBuilder responseData;
    private StringBuilder cookies;
    private StringBuilder method;
    private StringBuilder url;

    public JTLParser() throws ParsingException {
        super( supportedElements );
    }

    public void startElementTestResults( final Attributes attrs ) {
        getDocument().setVersion( attrs.getValue( JTLParserConstants.ATTR_VERSION ) );
    }

    public void endElementTestResults() {

    }

    public void startElementSample( final Attributes attrs ) throws ParsingException {
        if ( sample != null ) {
            throw new ParsingException( "Element (http)sample repoened without closing first one" );
        }
        sample = new JTLElementSample();

        sample.setElapsedTime( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_ELAPSED_TIME ) ) );
        sample.setLatency( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_LATENCY ) ) );
        sample.setTimestamp( safeParseLong( attrs.getValue( JTLParserConstants.ATTR_TIMESTAMP ) ) );
        sample.setSuccess( safeParseBoolean( attrs.getValue( JTLParserConstants.ATTR_SUCCESS ) ) );
        sample.setLabel( attrs.getValue( JTLParserConstants.ATTR_LABEL ) );
        sample.setHttpResponseCode( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_RESPONSE_CODE ) ) );
        sample.setHttpResponseMessage( attrs.getValue( JTLParserConstants.ATTR_RESPONSE_MESSAGE ) );
        sample.setThreadName( attrs.getValue( JTLParserConstants.ATTR_THREAD_NAME ) );
        sample.setDataType( attrs.getValue( JTLParserConstants.ATTR_DATA_TYPE ) );
        sample.setDataEncoding( attrs.getValue( JTLParserConstants.ATTR_DATA_ENCODING ) );
        sample.setBytes( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_BYTES ) ) );
        sample.setNumberOfActiveThreadsInGroup( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_NUMBER_ACTIVE_THREADS_IN_GROUP ) ) );
        sample.setNumberOfAllActiveThreads( safeParseInteger( attrs.getValue( JTLParserConstants.ATTR_NUMBER_ALL_ACTIVE_THREADS ) ) );
        sample.setHostname( attrs.getValue( JTLParserConstants.ATTR_HOSTNAME ) );
    }

    public void endElementSample() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element (http)sample closed without opening it!" );
        }
        getDocument().getElements().add( sample );
        this.sample = null;
    }

    public void startElementAssertionResult( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element AssertionResult must be in element (http)sample" );
        }
        this.assertion = new JTLAssertionElement();
    }

    public void endElementAssertionResult() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element AssertionResult must be in element (http)sample" );
        }
        if ( assertion == null ) {
            throw new ParsingException( "Element AssertionResult closed without opening it!" );
        }
        sample.getAssertions().add( assertion );
        this.assertion = null;
    }

    public void startElementName( final Attributes attrs ) throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for asertion's name must be in element AssertionResult" );
        }
        if ( assertionName != null ) {
            throw new ParsingException( "Assertion name element opened multiple times" );
        }
        assertionName = new StringBuilder();
    }

    public void valueElementName( final String value ) throws ParsingException {
        if ( assertionName == null ) {
            throw new ParsingException( "Assertion name text present without opening the element" );
        }
        assertionName.append( value );
    }

    public void endElementName() throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's name must be in element AssertionResult" );
        }
        assertion.setName( assertionName.toString() );
        assertionName = null;
    }

    public void startElementFailure( final Attributes attrs ) throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's failure must be in element AssertionResult" );
        }
        if ( assertionFailure != null ) {
            throw new ParsingException( "Assertion failure element opened multiple times" );
        }
        assertionFailure = new StringBuilder();
    }

    public void valueElementFailure( final String value ) throws ParsingException {
        if ( assertionFailure == null ) {
            throw new ParsingException( "Assertion failure text present without opening the element" );
        }
        assertionFailure.append( value );
    }

    public void endElementFailure() throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's failure must be in element AssertionResult" );
        }
        assertion.setFailure( safeParseBoolean( assertionFailure.toString() ) );
        assertionFailure = null;
    }

    public void startElementError( final Attributes attrs ) throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's error must be in element AssertionResult" );
        }
        if ( assertionError != null ) {
            throw new ParsingException( "Assertion error element opened multiple times" );
        }
        assertionError = new StringBuilder();
    }

    public void valueElementError( final String value ) throws ParsingException {
        if ( assertionError == null ) {
            throw new ParsingException( "Assertion error text present without opening the element" );
        }
        assertionError.append( value );
    }

    public void endElementError() throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's error must be in element AssertionResult" );
        }
        assertion.setError( safeParseBoolean( assertionError.toString() ) );
        assertionError = null;
    }

    public void startElementFailureMessage( final Attributes attrs ) throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's failure message must be in element AssertionResult" );
        }
        if ( assertionFailureMessage != null ) {
            throw new ParsingException( "Assertion error failure message opened multiple times" );
        }
        assertionFailureMessage = new StringBuilder();
    }

    public void valueElementFailureMessage( final String value ) throws ParsingException {
        if ( assertionFailureMessage == null ) {
            throw new ParsingException( "Assertion failure message text present without opening the element" );
        }
        assertionFailureMessage.append( value );
    }

    public void endElementFailureMessage() throws ParsingException {
        if ( assertion == null ) {
            throw new ParsingException( "Element for assertion's failure message must be in element AssertionResult" );
        }
        assertion.setFailureMessage( assertionFailureMessage.toString() );
        assertionFailureMessage = null;
    }

    public void startElementCookies( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element cookies must be in element (http)sample" );
        }
        if ( cookies != null ) {
            throw new ParsingException( "Element cookies opened multiple times" );
        }
        cookies = new StringBuilder();
    }

    public void valueElementCookies( final String value ) throws ParsingException {
        if ( cookies == null ) {
            throw new ParsingException( "Element cookies' text present without opening the element" );
        }
        cookies.append( value );
    }

    public void endElementCookies() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element cookies must be in element (http)sample" );
        }
        if ( cookies == null ) {
            throw new ParsingException( "Element cookies' closed without opening it first" );
        }
        sample.setCookies( cookies.toString() );
        cookies = null;
    }

    public void startElementMethod( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element method must be in element (http)sample" );
        }
        if ( method != null ) {
            throw new ParsingException( "Element method opened multiple times" );
        }
        method = new StringBuilder();
    }

    public void valueElementMethod( final String value ) throws ParsingException {
        if ( method == null ) {
            throw new ParsingException( "Element method's text present without opening the element" );
        }
        method.append( value );
    }

    public void endElementMethod() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element method must be in element (http)sample" );
        }
        if ( method == null ) {
            throw new ParsingException( "Element method' closed without opening it first" );
        }
        sample.setMethod( method.toString() );
        method = null;
    }

    public void startElementQueryString( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element queryString must be in element (http)sample" );
        }
        if ( queryString != null ) {
            throw new ParsingException( "Element queryString opened multiple times" );
        }
        queryString = new StringBuilder();
    }

    public void valueElementQueryString( final String value ) throws ParsingException {
        if ( queryString == null ) {
            throw new ParsingException( "Element queryString's text present without opening the element" );
        }
        queryString.append( value );
    }

    public void endElementQueryString() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element queryString must be in element (http)sample" );
        }
        if ( queryString == null ) {
            throw new ParsingException( "Element queryString closed without opening it first" );
        }
        sample.setQueryString( queryString.toString() );
        queryString = null;
    }

    public void startElementURL( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element java.net.URL must be in element (http)sample" );
        }
        if ( url != null ) {
            throw new ParsingException( "Element java.net.URL opened multiple times" );
        }
        url = new StringBuilder();
    }

    public void valueElementURL( final String value ) throws ParsingException {
        if ( url == null ) {
            throw new ParsingException( "Element java.net.URL's text present without opening the element" );
        }
        url.append( value );
    }

    public void endElementURL() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element java.net.URL must be in element (http)sample" );
        }
        if ( url == null ) {
            throw new ParsingException( "Element java.net.URL closed without opening it first" );
        }
        sample.setUrl( url.toString() );
        url = null;
    }

    public void startElementResponseHeader( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element responseHeader must be in element (http)sample" );
        }
        if ( responseHeader != null ) {
            throw new ParsingException( "Element responseHeader opened multiple times" );
        }
        responseHeader = new StringBuilder();
    }

    public void valueElementResponseHeader( final String value ) throws ParsingException {
        if ( responseHeader == null ) {
            throw new ParsingException( "Element responseHeader's text present without opening the element" );
        }
        responseHeader.append( value );
    }

    public void endElementResponseHeader() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element responseHeader must be in element (http)sample" );
        }
        if ( responseHeader == null ) {
            throw new ParsingException( "Element responseHeader closed without opening it first" );
        }
        sample.setResponseHeader( responseHeader.toString() );
        responseHeader = null;
    }

    public void startElementRequestHeader( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element requestHeader must be in element (http)sample" );
        }
        if ( requestHeader != null ) {
            throw new ParsingException( "Element requestHeader opened multiple times" );
        }
        requestHeader = new StringBuilder();
    }

    public void valueElementRequestHeader( final String value ) throws ParsingException {
        if ( requestHeader == null ) {
            throw new ParsingException( "Element requestHeader's text present without opening the element" );
        }
        requestHeader.append( value );
    }

    public void endElementRequestHeader() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element requestHeader must be in element (http)sample" );
        }
        if ( requestHeader == null ) {
            throw new ParsingException( "Element requestHeader closed without opening it first" );
        }
        sample.setResponseHeader( requestHeader.toString() );
        requestHeader = null;
    }

    public void startElementSamplerData( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element samplerData must be in element (http)sample" );
        }
        if ( samplerData != null ) {
            throw new ParsingException( "Element samplerData opened multiple times" );
        }
        samplerData = new StringBuilder();
    }

    public void valueElementSamplerData( final String value ) throws ParsingException {
        if ( samplerData == null ) {
            throw new ParsingException( "Element samplerData's text present without opening the element" );
        }
        samplerData.append( value );
    }

    public void endElementSamplerData() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element samplerData must be in element (http)sample" );
        }
        if ( samplerData == null ) {
            throw new ParsingException( "Element samplerData closed without opening it first" );
        }
        sample.setSamplerData( samplerData.toString() );
        samplerData = null;
    }

    public void startElementResponseData( final Attributes attrs ) throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element responseData must be in element (http)sample" );
        }
        if ( responseData != null ) {
            throw new ParsingException( "Element responseData opened multiple times" );
        }
        responseData = new StringBuilder();
    }

    public void valueElementResponseData( final String value ) throws ParsingException {
        if ( responseData == null ) {
            throw new ParsingException( "Element responseData's text present without opening the element" );
        }
        responseData.append( value );
    }

    public void endElementResponseData() throws ParsingException {
        if ( sample == null ) {
            throw new ParsingException( "Element responseData must be in element (http)sample" );
        }
        if ( responseData == null ) {
            throw new ParsingException( "Element responseData closed without opening it first" );
        }
        sample.setResponseData( responseData.toString() );
        responseData = null;
    }
}
