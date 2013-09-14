package com.tguzik.m2u.application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
public class Main
{
    public static void main( String[] args ) throws Exception {
        ProgramOptions po = CommandLineParser.parse(args);
        new Main(po).process();
    }

    private final ProgramOptions programOptions;

    public Main( ProgramOptions po ) {
        this.programOptions = po;
    }

    public void process( ) throws IOException {
        final InputStreamReader input = new FileReader(programOptions.getInputFileName());
        final OutputStreamWriter output = new FileWriter(programOptions.getOutputFileName());

        TestResults jmeterResults = new JmeterXmlConverter().fromXML(input);

        TestSuites junitResults = new JtlToJunitConverter(programOptions).apply(jmeterResults);

        new JunitXmlConverter().toXML(junitResults, output);

        input.close();
        output.close();
    }
}
