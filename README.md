m2u 
===
JMeter JTL to JUnit test results converter.


## What it is?
This project aims to ease conversion from JMeter JTL format to JUnit-compatible XML. This is due to some continuous integration systems (namely Hudson/Jenkins) support only a subset of JTL spec.

Currently only these JMeter JTL elements are supported:

* `<sample/>`
* `<httpSample/>`


## How to use it?
    java -jar jtl2junit.jar --input <input.jtl> --output <output.jmeter.xml> [--testSuiteName <name of the test suite>]

If the `<name of the test suite>` argument is not given, it will default to "`jmeter`".

## Developer's quick-start
### Tools
Here's a short list of the tools that I use:

* Eclipse (version 4.2.0)
* m2e - Maven Integration for Eclipse (version 1.1.0.20120530-0009)
* JDK 7


### The files
* The `bin/` directory is automatically populated by Maven. After executing `mvn package assembly:single` you can find the JAR with the application and all dependencies.
* The `doc/` directory contains formatter and clean up settings for Eclipse. I recommend using those and enabling clean up on save.
* The `src/` directory contains sources for the application (well, duh) as well as `log4j.properties` file that is loaded automatically by the application.
* The `test/` directory contains unit tests and another `log4j.properties` file that enables TRACE log level (required for some tests).

### Dependencies
Currently there are following dependencies for this application:

* `commons-cli` - runtime
* `log4j` - runtime (could be changed soon)
* `junit-4.10` and `org.mockito.mockito-all` - test only

### The source
I will be honest here, I do not consider the code as a final version - there are some parts of this application that could use some improvement (more unit tests, refactoring, all that jazz) and there are some parts that were ripped from my different pet-projects just because I've already had them laying around. I will fix these issues as soon as possible. 

On this note, if you think something could or should be done differently, feel free to change it - just make sure you leave a note or a pull request :)
