package com.tguzik.m2u.data.jtl;

import java.util.List;

import com.google.common.collect.Lists;
import com.tguzik.util.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * View of the data parsed:
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
 * 
 * @see http://jmeter.apache.org/usermanual/listeners.html#attributes
 * @author Tomek
 */
@XStreamAlias( "testResults" )
public class TestResults extends BaseObject
{
    @XStreamAsAttribute
    private String version;

    @XStreamImplicit
    @XStreamAlias( impl = Sample.class, value = "sample" )
    private final List<Sample> samples;

    @XStreamImplicit
    @XStreamAlias( impl = HttpSample.class, value = "httpSample" )
    private List<HttpSample> httpSamples;

    public TestResults() {
        this.samples = Lists.newArrayList();
        this.httpSamples = Lists.newArrayList();
    }

    public String getVersion( ) {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public List<Sample> getSamples( ) {
        return samples;
    }

    public void addSample( Sample sample ) {
        this.samples.add(sample);
    }

    public List<HttpSample> getHttpSamples( ) {
        return httpSamples;
    }

    public void addHttpSample( HttpSample sample ) {
        this.httpSamples.add(sample);
    }

    public void setHttpSamples( List<HttpSample> httpSamples ) {
        this.httpSamples = httpSamples;
    }
}
