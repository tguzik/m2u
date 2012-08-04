package com.tguzik.m2u.jtl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JTLDocumentTest {
    private JTLDocument document;

    @Before
    public void setUp() throws Exception {
        JTLElementSample sample;
        document = new JTLDocument();

        sample = new JTLElementSample();
        sample.setSuccess( Boolean.valueOf( true ) );
        sample.setElapsedTime( Integer.valueOf( 1 ) );
        document.getElements().add( sample );

        sample = new JTLElementSample();
        sample.setSuccess( Boolean.valueOf( false ) );
        sample.setElapsedTime( Integer.valueOf( 2 ) );
        document.getElements().add( sample );

        sample = new JTLElementSample();
        sample.setSuccess( Boolean.valueOf( true ) );
        sample.setElapsedTime( Integer.valueOf( 3 ) );
        document.getElements().add( sample );

        sample = new JTLElementSample();
        sample.setSuccess( null );
        sample.setElapsedTime( null );
        document.getElements().add( sample );
    }

    @Test
    public void testGetNumberOfSuccededTests() {
        assertEquals( 2, document.getNumberOfSuccededTests() );
    }

    @Test
    public void testGetTotalTimeInMiliseconds() {
        assertEquals( 6, document.getTotalTimeInMiliseconds() );
    }
}
