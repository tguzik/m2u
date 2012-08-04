package com.tguzik.util.parser;

import org.xml.sax.SAXException;

public class ParsingException extends SAXException {
    private static final long serialVersionUID = 1339868007465569809L;

    public ParsingException() {
        super();
    }

    public ParsingException( String msg ) {
        super( msg );
    }

    public ParsingException( String msg, Exception cause ) {
        super( msg, cause );
    }
}
