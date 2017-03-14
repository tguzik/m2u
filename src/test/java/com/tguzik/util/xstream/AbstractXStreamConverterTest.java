package com.tguzik.util.xstream;

import static org.junit.Assert.assertEquals;

import com.tguzik.objects.BaseObject;
import com.tguzik.tests.Normalize;
import org.junit.Before;
import org.junit.Test;

public class AbstractXStreamConverterTest {
    private AbstractXStreamConverterHelper xstream;

    @Before
    public void setUp() throws Exception {
        xstream = new AbstractXStreamConverterHelper();
    }

    @Test
    public void testParse_omitsUnknownFields() {
        String xml = "<dataobj>\n  <name>That is the name field</name>\n  <number>1234</number>\n</dataobj>";

        SampleDataObject sdo = xstream.fromXML( xml );

        assertEquals( xml, Normalize.newLines( xstream.toXML( sdo ) ) );
        assertEquals( "SampleDataObject[\n  name=That is the name field,\n  number=1234\n]",
                      Normalize.newLines( sdo.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE ) ) );
    }

    private static class AbstractXStreamConverterHelper extends AbstractXStreamConverter<SampleDataObject> {
        public AbstractXStreamConverterHelper() {
            super();
            xstream.processAnnotations( SampleDataObject.class );
        }
    }
}


