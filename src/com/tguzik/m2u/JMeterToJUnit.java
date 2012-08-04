package com.tguzik.m2u;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.xml.sax.SAXException;

import com.tguzik.util.parser.ParsingException;

/**
 * Main class of the converter. Neither multiple input file nor multiple output files are
 * not supported yet - it would require some awkward command line tricks.
 */
@SuppressWarnings( "static-access" )
public class JMeterToJUnit {
    public static final String APPLICATION_NAME = "jtl2junit";
    public static final String CMD_OPTION_INPUT = "input";
    public static final String CMD_OPTION_OUTPUT = "output";
    public static final String CMD_OPTION_TESTSUITE_NAME = "testSuiteName";
    public static final String DEFAULT_TESTSUITE_NAME = "jmeter";

    public static final CommandLineParser parser = new PosixParser();
    public static final Options options = new Options();

    static {
        options.addOption( OptionBuilder.isRequired( true )
                                        .hasArg( true )
                                        .withArgName( "JTL-XML-FILE" )
                                        .withLongOpt( CMD_OPTION_INPUT )
                                        .withDescription( "Input JTL test results file" )
                                        .create() );

        options.addOption( OptionBuilder.isRequired( true )
                                        .hasArg( true )
                                        .withArgName( "JUNIT-XML-FILE" )
                                        .withLongOpt( CMD_OPTION_OUTPUT )
                                        .withDescription( "JUnit XML test results file" )
                                        .create() );

        options.addOption( OptionBuilder.isRequired( false )
                                        .hasArg( true )
                                        .withArgName( "TEST-SUITE-NAME" )
                                        .withLongOpt( CMD_OPTION_TESTSUITE_NAME )
                                        .withDescription( "Name for the generated test suite (default: jmeter)" )
                                        .create() );
    }

    public static void main( String[] args ) throws Exception {
        final String inputFileName;
        final String outputFileName;
        final String testSuiteName;
        final CommandLine cmd;

        try {
            cmd = parser.parse( options, args );

            if ( !cmd.hasOption( CMD_OPTION_INPUT ) || !cmd.hasOption( CMD_OPTION_OUTPUT ) ) {
                throw new IllegalArgumentException( "Not all required options are declared." );
            }
        }
        catch ( Exception e ) {
            new HelpFormatter().printHelp( APPLICATION_NAME, options );
            throw e;
        }

        inputFileName = cmd.getOptionValue( CMD_OPTION_INPUT );
        outputFileName = cmd.getOptionValue( CMD_OPTION_OUTPUT );

        if ( cmd.hasOption( CMD_OPTION_TESTSUITE_NAME ) ) {
            testSuiteName = cmd.getOptionValue( CMD_OPTION_TESTSUITE_NAME );
        }
        else {
            testSuiteName = DEFAULT_TESTSUITE_NAME;
        }

        new JMeterToJUnit( inputFileName, outputFileName ).process( testSuiteName );
    }

    private final String inputFileName, outputFileName;
    private final Translator translator;

    public JMeterToJUnit( String inputFileName, String outputFileName ) throws ParsingException {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.translator = new Translator();
    }

    public void process( String testSuiteName ) throws IOException, SAXException {
        final InputStreamReader input = new FileReader( inputFileName );
        final OutputStreamWriter output = new FileWriter( outputFileName );

        translator.process( input, output, testSuiteName );

        input.close();
        output.close();
    }
}
