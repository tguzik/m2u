package com.tguzik.m2u.data.jtl;

import java.util.List;

import com.google.common.collect.Lists;
import com.tguzik.util.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Some of the fields might seem to have redundant @XStreamAlias annotations,
 * but that comes in handy when renaming the field.
 *
 * @author Tomek
 */
public class BaseSample extends BaseObject {
    @XStreamImplicit
    @XStreamAlias("assertionResult")
    private List<AssertionResult> assertionResults;

    @XStreamAsAttribute
    @XStreamAlias("t")
    private Integer elapsedTime; // miliseconds

    @XStreamAsAttribute
    @XStreamAlias("lt")
    private Integer latency; // miliseconds

    @XStreamAsAttribute
    @XStreamAlias("ts")
    private Long timestamp;

    @XStreamAsAttribute
    @XStreamAlias("s")
    private boolean success;

    @XStreamAsAttribute
    @XStreamAlias("lb")
    private String label;

    @XStreamAsAttribute
    @XStreamAlias("rc")
    private Integer httpResponseCode;

    @XStreamAsAttribute
    @XStreamAlias("rm")
    private String httpResponseMessage;

    @XStreamAsAttribute
    @XStreamAlias("tn")
    private String threadName;

    @XStreamAsAttribute
    @XStreamAlias("dt")
    private String dataType;

    @XStreamAsAttribute
    @XStreamAlias("de")
    private String dataEncoding;

    @XStreamAsAttribute
    @XStreamAlias("by")
    private Integer bytes;

    @XStreamAsAttribute
    @XStreamAlias("ng")
    private Integer numberOfActiveThreadsInGroup;

    @XStreamAsAttribute
    @XStreamAlias("na")
    private Integer numberOfAllActiveThreads;

    @XStreamAsAttribute
    @XStreamAlias("hn")
    private String hostname;

    @XStreamAlias("responseHeader")
    private String responseHeader;

    @XStreamAlias("requestHeader")
    private String requestHeader;

    @XStreamAlias("responseData")
    private String responseData;

    @XStreamAlias("samplerData")
    private String samplerData;

    @XStreamAlias("cookies")
    private String cookies;

    @XStreamAlias("method")
    private String method;

    @XStreamAlias("queryString")
    private String queryString;

    @XStreamAlias("java.net.URL")
    private String url;

    public BaseSample() {
        this.assertionResults = Lists.newArrayList();
    }

    public List<AssertionResult> getAssertionResults() {
        return assertionResults;
    }

    public void setAssertionResults( List<AssertionResult> assertionResults ) {
        this.assertionResults = assertionResults;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess( boolean success ) {
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
