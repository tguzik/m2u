package com.tguzik.m2u.data.jtl;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Extended from Base Sample because XStream sometimes would put objects of
 * wrong type into TestResults collections.
 *
 * @author Tomek
 */
@XStreamAlias( impl = Sample.class, value = "sample" )
public class Sample extends BaseSample {
    // no changes necessary
}
