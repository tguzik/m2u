#m2u 
JMeter JTL to JUnit test results converter.


## What it is?
This project aims to ease conversion from JMeter JTL format to JUnit-compatible XML. This is due to some continuous 
integration systems (namely Hudson/Jenkins) support only a subset of JTL spec.

Currently only these JMeter JTL elements are supported:

* `<sample/>`
* `<httpSample/>`


## How to build it?
    mvn assembly:single


## How to use it?
    java -jar jtl2junit.jar --input <jtl> --output <jmeter.xml> [--testSuiteName <name of the test suite>]

If the `<name of the test suite>` argument is not given, it will default to "`jmeter`".


## Developer's quick-start
### Tools
Here's a short list of the tools that I use:

* Eclipse
* m2e - Maven Integration for Eclipse
* JDK 7


### The files
* `build/` directory is automatically populated by Maven. After executing `mvn package` you can find the JAR with the application and all dependencies.
* `.settings/` directory contains formatter, clean up and save action settings for Eclipse.
* `src/` directory contains sources for the application (well, duh)
* `test/` directory contains unit tests and few samples of input/output data


### The source
I will be honest here, I do not consider the code as a final version - there are some parts of this application that 
could use some improvement (more unit tests, refactoring, all that jazz).

