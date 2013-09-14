package com.tguzik.m2u.xml;

import static com.tguzik.m2u.testdata.TestHelper.removeCharacterFeed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.testdata.TestHelper;

public class JmeterXmlConverterTest
{
    private String input;
    private String expected;
    private JmeterXmlConverter xstream;

    @Before
    public void setUp( ) throws Exception {
        this.xstream = new JmeterXmlConverter();

        this.input = TestHelper.fileToString("test/", TestHelper.class, "sample-jtl-input.xml");
        this.expected = TestHelper.fileToString("test/", TestHelper.class, "parsed-jtl.txt");
    }

    @Test
    public void sanityCheck( ) {
        assertFalse(input.isEmpty());
        assertFalse(expected.isEmpty());
    }

    @Test
    public void testParsing( ) {
        TestResults obj = xstream.fromXML(input);
        assertEquals(removeCharacterFeed(expected), removeCharacterFeed(obj.toString()));
    }
}
