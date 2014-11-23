package com.tguzik.m2u.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.tguzik.m2u.application.ProgramOptions;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.xml.JmeterXmlConverter;
import com.tguzik.m2u.xml.JunitXmlConverter;
import com.tguzik.tests.Loader;
import com.tguzik.tests.Normalize;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class JtlToJunitConverterTest {
    private String input;
    private String expected;
    private JunitXmlConverter junit;
    private JmeterXmlConverter jmeter;
    private JtlToJunitConverter converter;
    private String originalSeparator;

    @Before
    public void setUp() throws Exception {
        this.junit = new JunitXmlConverter();
        this.jmeter = new JmeterXmlConverter();
        this.converter = new JtlToJunitConverter( new ProgramOptions() );

        this.input = Loader.loadFile( "test/", JtlToJunitConverterTest.class, "../testdata", "sample-jtl-input.xml" )
                           .trim();
        this.expected = Loader.loadFile( "test/", JtlToJunitConverterTest.class, "../testdata", "converted-result.txt" )
                              .trim();
    }

    @Test
    public void sanityCheck() {
        assertFalse( input.isEmpty() );
        assertFalse( expected.isEmpty() );
    }

    @Test
    @Ignore
    public void testParsing() {
        TestResults tr = jmeter.fromXML( input );
        TestSuites ts = converter.apply( tr );
        String xml = Normalize.newLines( junit.toXML( ts ) );

        assertEquals( expected.trim(), xml.trim() );
    }

    // TODO: Expand unit tests
}
