package com.tguzik.m2u.jtl;

import java.util.ArrayList;
import java.util.List;

public class JTLElementSample {

    private final List<JTLAssertionElement> assertions = new ArrayList<JTLAssertionElement>();
    private Integer elapsedTime; // miliseconds
    private Integer latency; // miliseconds
    private Long timestamp;
    private Boolean success;
    private String label;
    private Integer httpResponseCode;
    private String httpResponseMessage;
    private String threadName;
    private String dataType;
    private String dataEncoding;
    private Integer bytes;
    private Integer numberOfActiveThreadsInGroup;
    private Integer numberOfAllActiveThreads;
    private String hostname;
    private String responseHeader;
    private String requestHeader;
    private String responseData;
    private String samplerData;
    private String cookies;
    private String method;
    private String queryString;
    private String url;

    public List<JTLAssertionElement> getFailedAssertions() {
        List<JTLAssertionElement> failures = new ArrayList<JTLAssertionElement>();

        for ( JTLAssertionElement assertion : assertions ) {
            if ( ( assertion.getError() != null && assertion.getError() )
                 || ( assertion.getFailure() != null && assertion.getFailure() ) ) {
                failures.add( assertion );
            }
        }

        return failures;
    }

    public List<JTLAssertionElement> getAssertions() {
        return assertions;
    }

    public void addAssertion( JTLAssertionElement assertion ) {
        this.assertions.add( assertion );
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime( Integer elapsedTime ) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency( Integer latency ) {
        this.latency = latency;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Long timestamp ) {
        this.timestamp = timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess( Boolean success ) {
        this.success = success;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode( Integer httpResponseCode ) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getHttpResponseMessage() {
        return httpResponseMessage;
    }

    public void setHttpResponseMessage( String httpResponseMessage ) {
        this.httpResponseMessage = httpResponseMessage;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName( String threadName ) {
        this.threadName = threadName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType( String dataType ) {
        this.dataType = dataType;
    }

    public String getDataEncoding() {
        return dataEncoding;
    }

    public void setDataEncoding( String dataEncoding ) {
        this.dataEncoding = dataEncoding;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes( Integer bytes ) {
        this.bytes = bytes;
    }

    public Integer getNumberOfActiveThreadsInGroup() {
        return numberOfActiveThreadsInGroup;
    }

    public void setNumberOfActiveThreadsInGroup( Integer numberOfActiveThreadsInGroup ) {
        this.numberOfActiveThreadsInGroup = numberOfActiveThreadsInGroup;
    }

    public Integer getNumberOfAllActiveThreads() {
        return numberOfAllActiveThreads;
    }

    public void setNumberOfAllActiveThreads( Integer numberOfAllActiveThreads ) {
        this.numberOfAllActiveThreads = numberOfAllActiveThreads;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname( String hostname ) {
        this.hostname = hostname;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader( String responseHeader ) {
        this.responseHeader = responseHeader;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader( String requestHeader ) {
        this.requestHeader = requestHeader;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData( String responseData ) {
        this.responseData = responseData;
    }

    public String getSamplerData() {
        return samplerData;
    }

    public void setSamplerData( String samplerData ) {
        this.samplerData = samplerData;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies( String cookies ) {
        this.cookies = cookies;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod( String method ) {
        this.method = method;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString( String queryString ) {
        this.queryString = queryString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

}
