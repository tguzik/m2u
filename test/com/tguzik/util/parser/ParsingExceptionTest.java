package com.tguzik.util.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ParsingExceptionTest {
    @Test
    public void test_noMessage() {
        try {
            throw new ParsingException();
        }
        catch ( Exception e ) {
            assertNull( e.getMessage() );
        }
    }

    @Test
    public void test_onlyMessage() {
        try {
            throw new ParsingException( "a" );
        }
        catch ( Exception e ) {
            assertEquals( "a", e.getMessage() );
        }
    }

    @Test
    public void test_messageAndCause() {
        Exception t = new Exception( "Some throwable" );
        try {
            throw new ParsingException( "a", t );
        }
        catch ( Exception e ) {
            assertEquals( "a", e.getMessage() );
            assertEquals( t, e.getCause() );
            assertSame( t, e.getCause() );
        }
    }
}
