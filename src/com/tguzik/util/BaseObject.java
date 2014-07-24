package com.tguzik.util;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BaseObject {
    @Override
    public boolean equals( Object other ) {
        return EqualsBuilder.reflectionEquals( this, other, false );
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString( this, MultilineNoAddressessToStringStyle.INSTANCE );
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode( this, false );
    }
}

class MultilineNoAddressessToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = 1L;
    public static final MultilineNoAddressessToStringStyle INSTANCE = new MultilineNoAddressessToStringStyle();

    MultilineNoAddressessToStringStyle() {
        super();
        this.setContentStart( "[" );
        this.setUseIdentityHashCode( false );
        this.setFieldSeparator( SystemUtils.LINE_SEPARATOR + "  " );
        this.setFieldSeparatorAtStart( true );
        this.setContentEnd( SystemUtils.LINE_SEPARATOR + "]" );
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
