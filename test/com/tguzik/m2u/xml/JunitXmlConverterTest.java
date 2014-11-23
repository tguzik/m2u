package com.tguzik.m2u.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.objects.BaseObject;
import com.tguzik.tests.Loader;
import com.tguzik.tests.Normalize;
import org.junit.Before;
import org.junit.Test;

public class JunitXmlConverterTest {
    private String input;
    private String expected;
    private JunitXmlConverter xstream;

    @Before
    public void setUp() throws Exception {
        this.xstream = new JunitXmlConverter();

        this.input = Loader.loadFile( "test/", JunitXmlConverterTest.class, "../testdata", "sample-junit-input.xml" );
        this.expected = Loader.loadFile( "test/", JunitXmlConverterTest.class, "../testdata", "parsed-junit.txt" );
    }

    @Test
    public void sanityCheck() {
        assertFalse( input.isEmpty() );
        assertFalse( expected.isEmpty() );
    }

    @Test
    public void testParsing() {
        TestSuites obj = xstream.fromXML( input );
        assertEquals( expected.trim(),
                      Normalize.newLines( obj.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE ) ).trim() );
    }
}
