package com.tguzik.m2u;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tguzik.m2u.jtl.JTLDocument;
import com.tguzik.m2u.jtl.parser.JTLParser;
import com.tguzik.m2u.junit.JunitXMLBuilder;
import com.tguzik.util.parser.ParsingException;

public class Translator {
    private final JTLParser parser;

    public Translator() throws ParsingException {
        this.parser = new JTLParser();
    }

    public void process( final InputStreamReader input, final OutputStreamWriter output, final String testSuiteName )
            throws SAXException, IOException {
        final JTLDocument document = new JTLDocument();

        document.setDocumentName( testSuiteName );
        parser.process( document, new InputSource( input ) );

        output.write( new JunitXMLBuilder( document ).getXML() );
    }
}
