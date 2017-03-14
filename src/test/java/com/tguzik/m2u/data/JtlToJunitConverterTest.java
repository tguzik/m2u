package com.tguzik.m2u.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.xml.JmeterXmlConverter;
import com.tguzik.m2u.xml.JunitXmlConverter;
import com.tguzik.tests.Loader;
import com.tguzik.tests.Normalize;
import org.junit.Before;
import org.junit.Test;

public class JtlToJunitConverterTest {
    private String inputXml;
    private String expectedXml;
    private JunitXmlConverter junit;
    private JmeterXmlConverter jmeter;
    private JtlToJunitConverter converter;
    private String originalSeparator;

    @Before
    public void setUp() throws Exception {
        this.junit = new JunitXmlConverter();
        this.jmeter = new JmeterXmlConverter();
        this.converter = new JtlToJunitConverter();

        this.inputXml = Loader.loadFile( getClass(), "../testdata", "sample-jtl-input.xml" );
        this.expectedXml = Loader.loadFile( getClass(), "../testdata", "converted-result.txt" );
    }

    @Test
    public void sanityCheck() {
        assertThat( inputXml ).isNotEmpty();
        assertThat( expectedXml ).isNotEmpty();
    }

    @Test
    public void apply_converts_the_data_structure() {
        final TestResults input = jmeter.fromXML( inputXml );
        final TestSuites ts = converter.apply( "jmeter", input );
        final String actual = junit.toXML( ts );

        assertThat( Normalize.newLines( actual ) ).isXmlEqualTo( Normalize.newLines( expectedXml ) );
    }

    // TODO: Expand unit tests
}
