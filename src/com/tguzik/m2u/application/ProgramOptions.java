package com.tguzik.m2u.application;

import com.tguzik.objects.BaseObject;

public class ProgramOptions extends BaseObject {
    private String inputFileName;
    private String outputFileName;
    private String testSuiteName = "jmeter";

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName( String inputFileName ) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName( String outputFileName ) {
        this.outputFileName = outputFileName;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public void setTestSuiteName( String testSuiteName ) {
        this.testSuiteName = testSuiteName;
    }
}
