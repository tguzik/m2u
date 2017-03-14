package com.tguzik.m2u.xml;

import static org.assertj.core.api.Assertions.assertThat;

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

        this.input = Loader.loadFile( getClass(), "../testdata", "sample-junit-input.xml" );
        this.expected = Loader.loadFile( getClass(), "../testdata", "parsed-junit.txt" );
    }

    @Test
    public void sanityCheck() {
        assertThat( input ).isNotEmpty();
        assertThat( expected ).isNotEmpty();
    }

    @Test
    public void testParsing() {
        final TestSuites obj = xstream.fromXML( input );
        final String actual = Normalize.newLines( BaseObject.toString( obj, BaseObject.MULTILINE_NO_ADDRESS_STYLE ) )
                                       .trim();

        assertThat( actual ).isEqualTo( expected.trim() );
    }
}
