package com.tguzik.m2u.jtl;

import java.util.ArrayList;
import java.util.List;

public class JTLDocument {
    private final List<JTLElementSample> elements;
    private String documentName;
    private String version;

    public JTLDocument() {
        elements = new ArrayList<JTLElementSample>();
    }

    public int getNumberOfSuccededTests() {
        int x = 0;
        for ( JTLElementSample element : elements ) {
            if ( element.getSuccess() != null && element.getSuccess() ) {
                x++;
            }
        }
        return x;
    }

    public long getTotalTimeInMiliseconds() {
        long x = 0;
        for ( JTLElementSample element : elements ) {
            if ( element.getElapsedTime() != null ) {
                x += element.getElapsedTime();
            }
        }
        return x;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public List<JTLElementSample> getElements() {
        return elements;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName( String documentName ) {
        this.documentName = documentName;
    }
}
