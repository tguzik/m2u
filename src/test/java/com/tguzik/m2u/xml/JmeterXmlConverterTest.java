package com.tguzik.m2u.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.objects.BaseObject;
import com.tguzik.tests.Loader;
import com.tguzik.tests.Normalize;
import org.junit.Before;
import org.junit.Test;

public class JmeterXmlConverterTest {
    private String input;
    private String expected;
    private JmeterXmlConverter xstream;

    @Before
    public void setUp() throws Exception {
        this.xstream = new JmeterXmlConverter();

        this.input = Loader.loadFile( getClass(), "../testdata", "sample-jtl-input.xml" );
        this.expected = Loader.loadFile( getClass(), "../testdata", "parsed-jtl.txt" );
    }

    @Test
    public void sanityCheck() {
        assertFalse( input.isEmpty() );
        assertFalse( expected.isEmpty() );
    }

    @Test
    public void testParsing() {
        TestResults obj = xstream.fromXML( input );
        assertEquals( expected.trim(),
                      Normalize.newLines( obj.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE ) ).trim() );
    }
}
