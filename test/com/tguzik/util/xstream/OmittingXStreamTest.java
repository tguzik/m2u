package com.tguzik.util.xstream;

import static org.junit.Assert.assertEquals;

import com.tguzik.objects.BaseObject;
import com.tguzik.tests.Normalize;
import org.junit.Before;
import org.junit.Test;

public class OmittingXStreamTest {
    private OmittingXStream xstream;

    @Before
    public void setUp() throws Exception {
        xstream = new OmittingXStream();
        xstream.processAnnotations( SampleDataObject.class );
    }

    @Test
    public void testParse_omitsUnknownFields() {
        String xml = "<dataobj><name>That's the name</name><number>1234</number>\n" +
                     "<something else=\"entirely\">some text</something></dataobj>";

        SampleDataObject sdo = (SampleDataObject) xstream.fromXML( xml );

        assertEquals( "SampleDataObject[\n" + "  name=That's the name,\n" + "  number=1234\n" + "]", //
                      Normalize.newLines( sdo.toString( BaseObject.MULTILINE_NO_ADDRESS_STYLE ) ) );
    }
}
