package com.tguzik.util.xstream;

import static com.tguzik.m2u.testdata.TestHelper.removeCharacterFeed;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AbstractXStreamConverterTest
{
    private AbstractXStreamConverterHelper xstream;

    @Before
    public void setUp( ) throws Exception {
        xstream = new AbstractXStreamConverterHelper();
    }

    @Test
    public void testParse_omitsUnknownFields( ) {
        String xml = "<dataobj>\n"
                     + "  <name>That is the name field</name>\n"
                     + "  <number>1234</number>\n"
                     + "</dataobj>";

        SampleDataObject sdo = xstream.fromXML(xml);

        assertEquals(xml, removeCharacterFeed(xstream.toXML(sdo)));
        assertEquals("com.tguzik.util.xstream.SampleDataObject[\n"
                     + "  name=That is the name field\n"
                     + "  number=1234\n"
                     + "]", //
                     removeCharacterFeed(sdo.toString()));
    }
}

class AbstractXStreamConverterHelper extends AbstractXStreamConverter<SampleDataObject>
{
    public AbstractXStreamConverterHelper() {
        super();
        xstream.processAnnotations(SampleDataObject.class);
    }
}
