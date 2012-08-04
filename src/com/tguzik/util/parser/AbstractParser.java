package com.tguzik.util.parser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.tguzik.util.logging.LoggerFactory;

public abstract class AbstractParser<T> extends DefaultHandler {
    protected static final Logger logger = LoggerFactory.getLogger( AbstractParser.class );

    private static final String REPORTING_SAFE_PARSE_FORMAT = "Error when parsing %s value: '%s'";
    private static final String REPORTING_XML_PARSE_FORMAT = "Parser reported %s: %s";
    private static final String REPORTING_REFLECTION_ERROR_FORMAT = "%s: %s";
    private static final String START_ELEMENT = "startElement";
    private static final String VALUE_ELEMENT = "valueElement";
    private static final String END_ELEMENT = "endElement";
    private static final String FATAL_ERROR = "fatal error";
    private static final String WARNING = "warning";
    private static final String ERROR = "error";

    private final Map<String, String> supportedElements;
    private final SAXParser parser;
    private String openedElement;
    private T document;

    protected AbstractParser( final Map<String, String> supportedElements ) throws ParsingException {
        this.supportedElements = Collections.unmodifiableMap( new HashMap<String, String>( supportedElements ) );
        try {
            final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating( false );

            this.parser = parserFactory.newSAXParser();
        }
        catch ( Exception e ) {
            throw new ParsingException( "Error while creating parser: " + e.getMessage(), e );
        }
    }

    void handleUnknownElement( final String elementName ) throws ParsingException {
        throw new ParsingException( "Don't know how to parse element: " + elementName );
    }

    Method findElementHandler( final String completeMethodName ) {
        for ( final Method method : this.getClass().getMethods() ) {
            if ( method.getName().equals( completeMethodName ) ) {
                return method;
            }
        }
        return null;
    }

    String getOpenedElement() {
        return openedElement;
    }

    protected T getDocument() {
        return document;
    }

    protected Integer safeParseInteger( final String value ) {
        if ( value != null ) {
            try {
                return Integer.valueOf( value, 10 );
            }
            catch ( Exception e ) {
                if ( logger.isDebugEnabled() ) {
                    logger.debug( String.format( REPORTING_SAFE_PARSE_FORMAT, "integer", value ) );
                }
            }
        }
        return null;
    }

    protected Long safeParseLong( final String value ) {
        if ( value != null ) {
            try {
                return Long.valueOf( value, 10 );
            }
            catch ( Exception e ) {
                if ( logger.isDebugEnabled() ) {
                    logger.debug( String.format( REPORTING_SAFE_PARSE_FORMAT, "long", value ) );
                }
            }
        }
        return null;
    }

    protected Boolean safeParseBoolean( final String value ) {
        if ( value != null ) {
            try {
                return Boolean.valueOf( value );
            }
            catch ( Exception e ) {
                if ( logger.isDebugEnabled() ) {
                    logger.debug( String.format( REPORTING_SAFE_PARSE_FORMAT, "boolean", value ) );
                }
            }
        }
        return null;
    }

    public T process( final T document, final InputSource input ) throws SAXException, IOException {
        this.document = document;
        parser.parse( input, this );
        this.document = null;
        return document;
    }

    public void startElement( String uri, String localName, String qName, Attributes attrs ) throws SAXException {
        final String completeMethodName;
        final Method method;

        if ( logger.isTraceEnabled() ) {
            logger.trace( String.format( "%s qName:%s", START_ELEMENT, qName ) );
        }
        openedElement = qName;

        if ( supportedElements.containsKey( qName ) ) {
            completeMethodName = START_ELEMENT + supportedElements.get( qName );
            method = findElementHandler( completeMethodName );
            if ( method != null ) {
                try {
                    method.invoke( this, attrs );
                    return;
                }
                catch ( InvocationTargetException e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getCause().getMessage() );
                    logger.error( message );
                    throw new ParsingException( message, e );
                }
                catch ( Exception e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getMessage() );
                    logger.fatal( message );
                    throw new ParsingException( message, e );
                }
            }
        }

        handleUnknownElement( qName );
    }

    /**
     * Remember that SAX parser can call this method multiple times for the same
     * logical #text element in xml.
     * 
     * The most common cases when SAX parser will call this method multiple times:
     * - SAX encountered xml entity (like &lt;, &gt; and so on) - only 1 character
     * (resolved entity) will be passed
     * - SAX encountered large block of text - SAX will split the text into 8K or
     * 16K chunks and call this method for each chunk
     */
    public void characters( char[] ch, int start, int length ) throws SAXException {
        final String completeMethodName;
        final String argument = new String( ch, start, length );
        final Method method;

        if ( logger.isTraceEnabled() ) {
            logger.trace( String.format( "%s qName:%s: '%s'", VALUE_ELEMENT, openedElement, argument ) );
        }

        if ( supportedElements.containsKey( openedElement ) ) {
            completeMethodName = VALUE_ELEMENT + supportedElements.get( openedElement );
            method = findElementHandler( completeMethodName );
            if ( method != null ) {
                try {
                    method.invoke( this, argument );
                }
                catch ( InvocationTargetException e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getCause().getMessage() );
                    logger.error( message );
                    throw new ParsingException( message, e );
                }
                catch ( Exception e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getMessage() );
                    logger.fatal( message );
                    throw new ParsingException( message, e );
                }
            }
        }
    }

    public void endElement( String uri, String localName, String qName ) throws SAXException {
        final String completeMethodName;
        final Method method;

        if ( logger.isTraceEnabled() ) {
            logger.trace( String.format( "%s qName:%s", END_ELEMENT, qName ) );
        }

        openedElement = null;

        if ( supportedElements.containsKey( qName ) ) {
            completeMethodName = END_ELEMENT + supportedElements.get( qName );
            method = findElementHandler( completeMethodName );
            if ( method != null ) {
                try {
                    method.invoke( this );
                    return;
                }
                catch ( InvocationTargetException e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getCause().getMessage() );
                    logger.error( message );
                    throw new ParsingException( message, e );
                }
                catch ( Exception e ) {
                    final String message = String.format( REPORTING_REFLECTION_ERROR_FORMAT,
                                                          completeMethodName,
                                                          e.getMessage() );
                    logger.fatal( message );
                    throw new ParsingException( message, e );
                }
            }
        }
        handleUnknownElement( qName );
    }

    public void warning( SAXParseException e ) throws SAXException {
        logger.info( String.format( REPORTING_XML_PARSE_FORMAT, WARNING, e.getMessage() ) );
        super.warning( e );
    }

    public void error( SAXParseException e ) throws SAXException {
        logger.warn( String.format( REPORTING_XML_PARSE_FORMAT, ERROR, e.getMessage() ) );
        super.error( e );
    }

    public void fatalError( SAXParseException e ) throws SAXException {
        logger.warn( String.format( REPORTING_XML_PARSE_FORMAT, FATAL_ERROR, e.getMessage() ) );
        super.fatalError( e );
    }
}
