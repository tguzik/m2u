package com.tguzik.m2u.data.jtl;

import java.util.List;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

/**
 * Some of the fields might seem to have redundant @XStreamAlias annotations,
 * but that comes in handy when renaming the field.
 *
 * @author Tomek
 */
@Data
public class BaseSample {
    @XStreamImplicit
    @XStreamAlias( "assertionResult" )
    private List<AssertionResult> assertionResults;

    @XStreamAsAttribute
    @XStreamAlias( "t" )
    private Integer elapsedTime; // miliseconds

    @XStreamAsAttribute
    @XStreamAlias( "lt" )
    private Integer latency; // miliseconds

    @XStreamAsAttribute
    @XStreamAlias( "ts" )
    private Long timestamp;

    @XStreamAsAttribute
    @XStreamAlias( "s" )
    private boolean success;

    @XStreamAsAttribute
    @XStreamAlias( "lb" )
    private String label;

    @XStreamAsAttribute
    @XStreamAlias( "rc" )
    private String httpResponseCode;

    @XStreamAsAttribute
    @XStreamAlias( "rm" )
    private String httpResponseMessage;

    @XStreamAsAttribute
    @XStreamAlias( "tn" )
    private String threadName;

    @XStreamAsAttribute
    @XStreamAlias( "dt" )
    private String dataType;

    @XStreamAsAttribute
    @XStreamAlias( "de" )
    private String dataEncoding;

    @XStreamAsAttribute
    @XStreamAlias( "by" )
    private Integer bytes;

    @XStreamAsAttribute
    @XStreamAlias( "ng" )
    private Integer numberOfActiveThreadsInGroup;

    @XStreamAsAttribute
    @XStreamAlias( "na" )
    private Integer numberOfAllActiveThreads;

    @XStreamAsAttribute
    @XStreamAlias( "hn" )
    private String hostname;

    @XStreamAlias( "responseHeader" )
    private String responseHeader;

    @XStreamAlias( "requestHeader" )
    private String requestHeader;

    @XStreamAlias( "responseData" )
    private String responseData;

    @XStreamAlias( "samplerData" )
    private String samplerData;

    @XStreamAlias( "cookies" )
    private String cookies;

    @XStreamAlias( "method" )
    private String method;

    @XStreamAlias( "queryString" )
    private String queryString;

    @XStreamAlias( "java.net.URL" )
    private String url;

    @Override
    public String toString() {
        return BaseObject.toString( this, BaseObject.MULTILINE_NO_ADDRESS_STYLE );
    }

}
