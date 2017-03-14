package com.tguzik.m2u.data.junit;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@XStreamAlias( "failure" )
public class Failure {
    @XStreamAsAttribute
    @XStreamAlias( "message" )
    private String message;

    @XStreamAsAttribute
    @XStreamAlias( "type" )
    private String type;

    @Override
    public String toString() {
        return BaseObject.toString( this, ToStringStyle.SHORT_PREFIX_STYLE );
    }
}
