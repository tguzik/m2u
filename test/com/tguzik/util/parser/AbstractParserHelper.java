package com.tguzik.util.parser;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

public class AbstractParserHelper extends AbstractParser<AbstractParserHelperBean> {
    private static final Map<String, String> supportedElements;

    static {
        supportedElements = new HashMap<String, String>();
        supportedElements.put( "name", "Name" );
        supportedElements.put( "number", "Number" );
        supportedElements.put( "exception", "Exception" );
    }

    protected AbstractParserHelper() throws ParsingException {
        super( supportedElements );
    }

    public void startElementName( final Attributes attrs ) {
        logger.trace( "startElementName" );
    }

    public void valueElementName( final String value ) {
        logger.trace( "valueElementName" );
        logger.debug( "Read value: " + value );
        if ( getDocument() != null ) {
            getDocument().setName( value );
        }
    }

    public void endElementName() {
        logger.trace( "endElementName" );
    }

    public void startElementNumber( final Attributes attrs ) {
        final Integer number = safeParseInteger( attrs.getValue( "value" ) );
        logger.trace( "startElementNumber" );
        logger.debug( "Read number: " + number );
        if ( getDocument() != null ) {
            getDocument().setNumber( number );
        }
    }

    public void endElementNumber() {
        logger.trace( "endElementNumber" );
    }

    public void startElementException( final Attributes attrs ) throws ParsingException {
        logger.trace( "startElementException" );
        throw new ParsingException( "startElementException" );
    }

    public void valueElementException( final String attrs ) throws ParsingException {
        logger.trace( "valueElementException" );
        throw new ParsingException( "valueElementException" );
    }

    public void endElementException() throws ParsingException {
        logger.trace( "endElementException" );
        throw new ParsingException( "endElementException" );
    }
}
