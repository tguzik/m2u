package com.tguzik.m2u.application;

import org.apache.commons.cli.*;

public class CommandLineParser {
    public static final String APPLICATION_NAME = "jtl2junit";
    public static final String CMD_OPTION_INPUT = "input";
    public static final String CMD_OPTION_OUTPUT = "output";
    public static final String CMD_OPTION_TESTSUITE_NAME = "testSuiteName";

    public static ProgramOptions parse( String[] argv ) throws Exception {
        Options options = createCommandLineOptions();
        PosixParser parser = new PosixParser();

        try {
            return extractProgramOptions( parser.parse( options, argv ) );
        }
        catch ( Exception e ) {
            new HelpFormatter().printHelp( APPLICATION_NAME, options );
            throw e;
        }
    }

    static ProgramOptions extractProgramOptions( CommandLine cmd ) {
        ProgramOptions programOptions = new ProgramOptions();

        programOptions.setInputFileName( cmd.getOptionValue( CMD_OPTION_INPUT ) );
        programOptions.setOutputFileName( cmd.getOptionValue( CMD_OPTION_OUTPUT ) );

        if ( cmd.hasOption( CMD_OPTION_TESTSUITE_NAME ) ) {
            programOptions.setTestSuiteName( cmd.getOptionValue( CMD_OPTION_TESTSUITE_NAME ) );
        }

        return programOptions;
    }

    static Options createCommandLineOptions() {
        Options options = new Options();

        // This builder pattern is atrocious :<
        OptionBuilder.isRequired( true );
        OptionBuilder.hasArg( true );
        OptionBuilder.withArgName( "JTL-XML-FILE" );
        OptionBuilder.withLongOpt( CMD_OPTION_INPUT );
        OptionBuilder.withDescription( "Input JTL test results file" );
        options.addOption( OptionBuilder.create() );

        OptionBuilder.isRequired( true );
        OptionBuilder.hasArg( true );
        OptionBuilder.withArgName( "JUNIT-XML-FILE" );
        OptionBuilder.withLongOpt( CMD_OPTION_OUTPUT );
        OptionBuilder.withDescription( "JUnit XML test results file" );
        options.addOption( OptionBuilder.create() );

        OptionBuilder.isRequired( false );
        OptionBuilder.hasArg( true );
        OptionBuilder.withArgName( "TEST-SUITE-NAME" );
        OptionBuilder.withLongOpt( CMD_OPTION_TESTSUITE_NAME );
        OptionBuilder.withDescription( "Name for the generated test suite (default: jmeter)" );
        options.addOption( OptionBuilder.create() );

        return options;
    }
}
