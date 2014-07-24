package com.tguzik.m2u.xml;

import static com.tguzik.m2u.testdata.TestHelper.removeCharacterFeed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.testdata.TestHelper;
import org.junit.Before;
import org.junit.Test;

public class JunitXmlConverterTest {
    private String input;
    private String expected;
    private JunitXmlConverter xstream;

    @Before
    public void setUp() throws Exception {
        this.xstream = new JunitXmlConverter();

        this.input = TestHelper.fileToString( "test/", TestHelper.class, "sample-junit-input.xml" );
        this.expected = TestHelper.fileToString( "test/", TestHelper.class, "parsed-junit.txt" );
    }

    @Test
    public void sanityCheck() {
        assertFalse( input.isEmpty() );
        assertFalse( expected.isEmpty() );
    }

    @Test
    public void testParsing() {
        TestSuites obj = xstream.fromXML( input );
        assertEquals( removeCharacterFeed( expected ), removeCharacterFeed( obj.toString() ) );
    }
}
