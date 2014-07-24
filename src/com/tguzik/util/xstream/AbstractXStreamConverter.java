package com.tguzik.util.xstream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractXStreamConverter<T> {
    protected final XStream xstream;

    @SafeVarargs
    public AbstractXStreamConverter( Class<T>... clazz ) {
        this.xstream = new OmittingXStream();
        this.xstream.processAnnotations( clazz );
        this.xstream.autodetectAnnotations( false );
    }

    @SuppressWarnings("unchecked")
    public T fromXML( Reader input ) {
        return (T) xstream.fromXML( input );
    }

    @SuppressWarnings("unchecked")
    public T fromXML( InputStream input ) {
        return (T) xstream.fromXML( input );
    }

    @SuppressWarnings("unchecked")
    public T fromXML( String xml ) {
        return (T) xstream.fromXML( xml );
    }

    public String toXML( T results ) {
        return xstream.toXML( results );
    }

    public void toXML( T results, OutputStream out ) {
        xstream.toXML( results, out );
    }

    public void toXML( T results, Writer out ) {
        xstream.toXML( results, out );
    }
}
