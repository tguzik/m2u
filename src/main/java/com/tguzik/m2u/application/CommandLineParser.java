package com.tguzik.m2u.application;

import org.apache.commons.cli.*;

public class CommandLineParser {
    public static final String APPLICATION_NAME = "jtl2junit";
    public static final String CMD_OPTION_INPUT = "input";
    public static final String CMD_OPTION_OUTPUT = "output";
    public static final String CMD_OPTION_TESTSUITE_NAME = "testSuiteName";

    public ProgramOptions parse( String[] argv ) throws Exception {
        final Options options = createCommandLineOptions();
        final DefaultParser parser = new DefaultParser();

        try {
            final CommandLine cmd = parser.parse( options, argv );
            return new ProgramOptions( cmd.getOptionValue( CMD_OPTION_INPUT ),
                                       cmd.getOptionValue( CMD_OPTION_OUTPUT ),
                                       cmd.getOptionValue( CMD_OPTION_TESTSUITE_NAME ) );
        }
        catch ( Exception e ) {
            new HelpFormatter().printHelp( APPLICATION_NAME, options );
            throw e;
        }
    }

    protected Options createCommandLineOptions() {
        final Options options = new Options();

        options.addOption( Option.builder()
                                 .required()
                                 .hasArg()
                                 .argName( "JTL-XML-FILE" )
                                 .longOpt( CMD_OPTION_INPUT )
                                 .desc( "Input JTL test results file" )
                                 .build() );

        options.addOption( Option.builder()
                                 .required()
                                 .hasArg()
                                 .argName( "JUNIT-XML-FILE" )
                                 .longOpt( CMD_OPTION_OUTPUT )
                                 .desc( "JUnit XML test results file" )
                                 .build() );

        options.addOption( Option.builder()
                                 .required( false )
                                 .hasArg()
                                 .argName( "TEST-SUITE-NAME" )
                                 .longOpt( CMD_OPTION_TESTSUITE_NAME )
                                 .desc( "Name for the generated test suite (default: jmeter)" )
                                 .build() );

        return options;
    }
}
