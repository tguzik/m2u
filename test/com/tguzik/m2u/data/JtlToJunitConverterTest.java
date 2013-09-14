package com.tguzik.m2u.data;

import static com.tguzik.m2u.testdata.TestHelper.removeCharacterFeed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.tguzik.m2u.application.ProgramOptions;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.testdata.TestHelper;
import com.tguzik.m2u.xml.JmeterXmlConverter;
import com.tguzik.m2u.xml.JunitXmlConverter;

public class JtlToJunitConverterTest
{
    private String input;
    private String expected;
    private JunitXmlConverter junit;
    private JmeterXmlConverter jmeter;
    private JtlToJunitConverter converter;

    @Before
    public void setUp( ) throws Exception {
        this.junit = new JunitXmlConverter();
        this.jmeter = new JmeterXmlConverter();
        this.converter = new JtlToJunitConverter(new ProgramOptions());

        this.input = TestHelper.fileToString("test/", TestHelper.class, "sample-jtl-input.xml");
        this.expected = TestHelper.fileToString("test/", TestHelper.class, "converted-result.txt");
    }

    @Test
    public void sanityCheck( ) {
        assertFalse(input.isEmpty());
        assertFalse(expected.isEmpty());
    }

    @Test
    public void testParsing( ) {
        TestResults tr = jmeter.fromXML(input);
        TestSuites ts = converter.apply(tr);
        String xml = junit.toXML(ts);

        assertEquals(removeCharacterFeed(expected), removeCharacterFeed(xml));
    }

    // TODO: Expand unit tests
}
