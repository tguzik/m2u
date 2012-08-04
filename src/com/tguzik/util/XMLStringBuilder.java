package com.tguzik.util;

public class XMLStringBuilder {
    protected static final String OPEN_TAG = "<";
    protected static final String CLOSE_TAG = ">";
    protected static final String SLASH = "/";
    protected static final String NEW_LINE = "\n";
    protected static final String SPACE = " ";
    protected static final String QUOTE = "\"";
    protected static final String EQUALS_QUOTE = "="
                                                 + QUOTE;
    protected static final String CDATA_OPEN = "<![CDATA[";
    protected static final String CDATA_CLOSE = "]]>";

    private final StringBuilder sb;
    private final String indentation;
    private int indentationLevel = 0;

    public XMLStringBuilder() {
        this.sb = new StringBuilder();
        indentation = "";
    }

    public XMLStringBuilder( final String indentation ) {
        this.sb = new StringBuilder();
        this.indentation = indentation;
    }

    public XMLStringBuilder( final StringBuilder sb, final String indentation ) {
        this.sb = sb;
        this.indentation = indentation;
    }

    public void appendRawValue( final String value ) {
        sb.append( value );
    }

    protected void appendIndentation() {
        for ( int i = 0; i < indentationLevel; i++ ) {
            sb.append( indentation );
        }
    }

    public void openTag( final String tagName ) {
        sb.append( NEW_LINE );
        appendIndentation();
        indentationLevel++;

        sb.append( OPEN_TAG );
        sb.append( tagName );
    }

    public void appendAttribute( final String name, final Object value ) {
        sb.append( SPACE );
        sb.append( name );
        sb.append( EQUALS_QUOTE );
        sb.append( value );
        sb.append( QUOTE );
    }

    public void appendOptionalAttribute( final String name, final Object value ) {
        if ( value != null
             && !"".equals( value ) ) {
            appendAttribute( name, value );
        }
    }

    public void quickCloseTag() {
        sb.append( SLASH ).append( CLOSE_TAG );
    }

    public void closeTag( final String tagName ) {
        sb.append( NEW_LINE );
        indentationLevel--;
        appendIndentation();
        sb.append( OPEN_TAG ).append( SLASH ).append( tagName ).append( CLOSE_TAG );
    }

    public void appendCDATA( final String value ) {
        sb.append( NEW_LINE ).append( CDATA_OPEN ).append( value ).append( CDATA_CLOSE );
    }

    public void appendChildren() {
        sb.append( CLOSE_TAG );
    }

    public String toXML() {
        return toString();
    }

    public String toString() {
        return sb.toString();
    }

}
