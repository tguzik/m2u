package com.tguzik.m2u.application;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Tomasz Guzik <tomek@tguzik.com>
 */
public class MultilineLfNoAddressStyle extends ToStringStyle {
    public static final MultilineLfNoAddressStyle INSTANCE = new MultilineLfNoAddressStyle();

    private static final long serialVersionUID = 1L;

    public MultilineLfNoAddressStyle() {
        this.setContentStart( "[\n  " );
        this.setUseShortClassName( true );
        this.setUseIdentityHashCode( false );
        this.setFieldSeparator( ",\n  " );
        this.setFieldSeparatorAtStart( false );
        this.setContentEnd( "\n]" );
    }
}
