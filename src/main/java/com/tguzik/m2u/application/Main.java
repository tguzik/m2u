package com.tguzik.m2u.application;

import java.io.*;

import com.tguzik.m2u.data.JtlToJunitConverter;
import com.tguzik.m2u.data.jtl.TestResults;
import com.tguzik.m2u.data.junit.TestSuites;
import com.tguzik.m2u.xml.JmeterXmlConverter;
import com.tguzik.m2u.xml.JunitXmlConverter;


/**
 * Main class of the converter. Neither multiple input file nor multiple output
 * files are not supported yet - it would require some awkward command line
 * tricks.
 */
public class Main {
    public static void main( String[] args ) throws Exception {
        ProgramOptions po = new CommandLineParser().parse( args );
        new Main().run( po );
    }

    public void run( ProgramOptions programOptions ) throws IOException {
        try ( InputStreamReader input = new FileReader( programOptions.getInputFileName() );
              OutputStreamWriter output = new FileWriter( programOptions.getOutputFileName() ) ) {

            TestResults jmeterResults = new JmeterXmlConverter().fromXML( input );
            TestSuites junitResults = new JtlToJunitConverter().apply( programOptions.getTestSuiteName(),
                                                                       jmeterResults );

            new JunitXmlConverter().toXML( junitResults, output );
        }
    }
}
