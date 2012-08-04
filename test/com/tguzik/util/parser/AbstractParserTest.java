package com.tguzik.util.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.tguzik.util.TestingAppender;
import com.tguzik.util.logging.LoggerFactory;

public class AbstractParserTest {

    private AbstractParserHelper parser;
    private TestingAppender appender;

    @Before
    public void setUp() throws Exception {
        parser = new AbstractParserHelper();
        appender = new TestingAppender();
        LoggerFactory.getLogger( AbstractParser.class ).addAppender( appender );
    }

    @Test
    public void testProcess() throws SAXException, IOException {
        final String xml = "<number value=\"42\"><name>some text</name></number>";
        final AbstractParserHelperBean beanIn, beanOut;

        beanIn = new AbstractParserHelperBean();

        beanOut = parser.process( beanIn, new InputSource( new StringReader( xml ) ) );

        assertSame( beanIn, beanOut );
        assertNotNull( beanIn );
        assertEquals( 42, beanIn.getNumber() );
        assertEquals( "some text", beanIn.getName() );
    }

    @Test
    public void testHandleUnknownElement() {
        try {
            parser.handleUnknownElement( "ELEMENT" );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "Don't know how to parse element: ELEMENT", e.getMessage() );
        }
    }

    @Test
    public void testFindElementHandler() {
        assertNotNull( parser.findElementHandler( "startElementName" ) );
        assertNotNull( parser.findElementHandler( "valueElementName" ) );
        assertNotNull( parser.findElementHandler( "endElementName" ) );
        assertNull( parser.findElementHandler( "DOESNT EXIST" ) );
        assertNull( parser.findElementHandler( null ) );
    }

    @Test
    public void testSafeParseInteger() {
        assertEquals( Integer.valueOf( 1 ), parser.safeParseInteger( "1" ) );
        assertEquals( Integer.valueOf( -1 ), parser.safeParseInteger( "-1" ) );
        assertEquals( Integer.valueOf( Integer.MAX_VALUE ),
                      parser.safeParseInteger( String.valueOf( Integer.MAX_VALUE ) ) );
        assertEquals( Integer.valueOf( Integer.MIN_VALUE ),
                      parser.safeParseInteger( String.valueOf( Integer.MIN_VALUE ) ) );

        assertNull( parser.safeParseInteger( "NOT A NUMBER" ) );
        assertNull( parser.safeParseInteger( null ) );
    }

    @Test
    public void testSafeParseLong() {
        assertEquals( Long.valueOf( 1 ), parser.safeParseLong( "1" ) );
        assertEquals( Long.valueOf( -1 ), parser.safeParseLong( "-1" ) );
        assertEquals( Long.valueOf( Long.MAX_VALUE ), parser.safeParseLong( String.valueOf( Long.MAX_VALUE ) ) );
        assertEquals( Long.valueOf( Long.MIN_VALUE ), parser.safeParseLong( String.valueOf( Long.MIN_VALUE ) ) );

        assertNull( parser.safeParseLong( "NOT A NUMBER" ) );
        assertNull( parser.safeParseLong( null ) );
    }

    @Test
    public void testSafeParseBoolean() {
        assertEquals( Boolean.TRUE, parser.safeParseBoolean( "true" ) );
        assertEquals( Boolean.FALSE, parser.safeParseBoolean( "false" ) );
        assertNull( parser.safeParseBoolean( null ) );
    }

    @Test
    public void testStartElement_allNulls() {
        try {
            parser.startElement( null, null, null, null );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "Don't know how to parse element: null", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "startElement qName:null" ) );
    }

    @Test
    public void testStartElement_unsupported() {
        try {
            parser.startElement( null, null, "NOT SUPPORTED", null );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "Don't know how to parse element: NOT SUPPORTED", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "startElement qName:NOT SUPPORTED" ) );
    }

    @Test
    public void testStartElement_exceptionFromHandler() throws SAXException {
        try {
            parser.startElement( null, null, "exception", null );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "startElementException: startElementException", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "startElement qName:exception" ) );
        assertTrue( appender.getMsg().contains( "startElementException" ) );
    }

    @Test
    public void testStartElement_passesArgument() throws SAXException {
        final AttributesImpl attrs = new AttributesImpl();
        attrs.addAttribute( null, null, "value", "string", "42" );

        parser.startElement( null, null, "number", attrs );

        assertTrue( appender.getMsg().contains( "startElement qName:number" ) );
        assertTrue( appender.getMsg().contains( "startElementNumber" ) );
        assertTrue( appender.getMsg().contains( "Read number: 42" ) );
    }

    @Test
    public void testStartElement() throws SAXException {
        parser.startElement( null, null, "name", null );
        assertTrue( appender.getMsg().contains( "startElement qName:name" ) );
        assertTrue( appender.getMsg().contains( "startElementName" ) );
    }

    @Test
    public void testEndElement_allNulls() {
        try {
            parser.endElement( null, null, null );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "Don't know how to parse element: null", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "endElement qName:null" ) );
    }

    @Test
    public void testEndElement_unsupported() {
        try {
            parser.endElement( null, null, "NOT SUPPORTED" );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "Don't know how to parse element: NOT SUPPORTED", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "endElement qName:NOT SUPPORTED" ) );
    }

    @Test
    public void testEndElement_exceptionFromHandler() throws SAXException {
        try {
            parser.endElement( null, null, "exception" );
            fail( "Expected exception" );
        }
        catch ( Exception e ) {
            assertEquals( "endElementException: endElementException", e.getMessage() );
        }
        assertTrue( appender.getMsg().contains( "endElement qName:exception" ) );
        assertTrue( appender.getMsg().contains( "endElementException" ) );
    }

    @Test
    public void testEndElement() throws SAXException {
        parser.endElement( null, null, "name" );
        assertTrue( appender.getMsg().contains( "endElement qName:name" ) );
        assertTrue( appender.getMsg().contains( "endElementName" ) );
    }

    @Test
    public void testCharacters_allNulls() throws SAXException {
        try {
            // Cause openedElement to be set to null
            parser.endElement( null, null, null );
        }
        catch ( Exception e ) {
            // do nothing
        }

        parser.characters( new char[0], 0, 0 );
        assertTrue( appender.getMsg().contains( "valueElement qName:null" ) );
    }

    @Test
    public void testCharacters_unsupported() throws SAXException {
        final String str = "string";

        // Cause openedElement to be set to null
        parser.endElement( null, null, "name" );
        parser.characters( str.toCharArray(), 0, str.length() );
        assertTrue( appender.getMsg().contains( "valueElement qName:null" ) );
    }

    @Test
    public void testCharacters_exceptionFromHandler() throws SAXException {
        final String str = "string";
        try {
            parser.startElement( null, null, "exception", null );
        }
        catch ( Exception e ) {
            assertEquals( "startElementException: startElementException", e.getMessage() );
        }

        try {
            parser.characters( str.toCharArray(), 0, str.length() );
        }
        catch ( Exception e ) {
            assertEquals( "valueElementException: valueElementException", e.getMessage() );
        }

        assertTrue( appender.getMsg().contains( "startElement qName:exception" ) );
        assertTrue( appender.getMsg().contains( "startElementException" ) );
        assertTrue( appender.getMsg().contains( "valueElement qName:exception" ) );
    }

    @Test
    public void testCharacters_passesArgument() throws SAXException {
        final String str = "string";
        parser.startElement( null, null, "name", null );
        parser.characters( str.toCharArray(), 0, str.length() );

        assertTrue( appender.getMsg().contains( "startElement qName:name" ) );
        assertTrue( appender.getMsg().contains( "startElementName" ) );
        assertTrue( appender.getMsg().contains( "valueElement qName:name" ) );
        assertTrue( appender.getMsg().contains( "Read value: string" ) );
    }
}
