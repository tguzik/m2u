package com.tguzik.util.logging;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFactory extends Logger {
    private static final String FALLBACK_CONFIGURATION_FILE_NAME = "/log4j.properties";
    private static final AtomicBoolean configured = new AtomicBoolean( false );

    private static void configure() {
        final InputStream is;
        if ( configured.get() ) {
            return;
        }

        is = System.class.getResourceAsStream( FALLBACK_CONFIGURATION_FILE_NAME );
        if ( is != null ) {
            // Use provided default property file for configuration.
            PropertyConfigurator.configure( is );
        }
        else {
            // Default configuration. We're very sad this has happened.
            BasicConfigurator.configure();
        }
        configured.set( true );
    }

    @SuppressWarnings( "rawtypes" )
    public static Logger getLogger( final Class clazz ) {
        configure();
        return Logger.getLogger( clazz );
    }

    public static Logger getLogger( final String name ) {
        configure();
        return Logger.getLogger( name );
    }

    public static Logger getLogger( final String name, final org.apache.log4j.spi.LoggerFactory factory ) {
        configure();
        return Logger.getLogger( name, factory );
    }

    protected LoggerFactory( String name ) {
        super( name );
    }

}
