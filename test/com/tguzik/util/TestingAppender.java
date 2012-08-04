package com.tguzik.util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class TestingAppender extends ConsoleAppender {
    private final StringBuffer log;
    private final StringBuffer msg;

    public TestingAppender() {
        super();
        setThreshold( Level.TRACE );

        log = new StringBuffer();
        msg = new StringBuffer();
    }

    @Override
    public void append( final LoggingEvent evt ) {

        log.append( evt.getLevel().toString() ).append( "\n" );
        msg.append( evt.getMessage().toString() ).append( "\n" );
    }

    public void doAppend( final LoggingEvent event ) {
        append( event );
    }

    public String getLog() {
        return log.toString();
    }

    public String getMsg() {
        return msg.toString();
    }
}
