package com.tguzik.m2u.application;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

import com.tguzik.objects.BaseObject;
import org.apache.commons.lang3.StringUtils;

@ParametersAreNonnullByDefault
public class ProgramOptions extends BaseObject {
    private final String inputFileName;
    private final String outputFileName;
    private final String testSuiteName;

    public ProgramOptions( String inputFileName, String outputFileName, @Nullable String testSuiteName ) {
        this.inputFileName = Objects.requireNonNull( StringUtils.trimToNull( inputFileName ) );
        this.outputFileName = Objects.requireNonNull( StringUtils.trimToNull( outputFileName ) );
        this.testSuiteName = Optional.ofNullable( testSuiteName ).map( StringUtils::trimToNull ).orElse( "jmeter" );
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }
}
