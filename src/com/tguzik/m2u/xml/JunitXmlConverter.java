package com.tguzik.m2u.xml;

import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.util.xstream.AbstractXStreamConverter;

public class JunitXmlConverter extends AbstractXStreamConverter<TestSuites> {
    public JunitXmlConverter() {
        super( TestSuites.class );
    }
}
