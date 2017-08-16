package com.tguzik.m2u.application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.tguzik.m2u.constants.M2UConstants;
import com.tguzik.m2u.params.ControlParams;

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
            
            String filter = cmd.getOptionValue(M2UConstants.JUNIT_FILTER_SWITCH_NAME);
            ControlParams.setParams(M2UConstants.JUNIT_FILTER_SWITCH_NAME, filter!=null?filter.toLowerCase():M2UConstants.FALSE);
            
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
        
        options.addOption( Option.builder()
				                .required( false )
				                .hasArg()
				                .argName( "JUNIT-FILTER_SWITCH-NAME")
				                .longOpt( M2UConstants.JUNIT_FILTER_SWITCH_NAME )
				                .desc( "Name for the junit test switch (default: false)" )
				                .build() );

        return options;
    }
}
