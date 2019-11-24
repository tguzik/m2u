#m2u 
JMeter JTL to JUnit test results converter.

**Project status: Archived** - will no longer be updated by me. If anyone wants to take over, you are free to fork 
it and keep developing it. 

This was a project I originally created in one afternoon to make reports from JMeter show up in Jenkins - I no longer 
have had a need for that for quite a while, as I moved away from JMeter-based tests to TestNG-based tests, so the 
utility fell out of use. 

The repo will be marked as archived 1-2 weeks after this update.


### Original features

This project aimed to ease conversion from JMeter JTL format to JUnit-compatible XML. This is due to some continuous 
integration systems (namely Hudson/Jenkins) support only a subset of JTL spec and/or take a very opinionated way of
displaying the results, which makes them unusable if JMeter is used for functional testing.

Currently only these JMeter JTL elements are supported:

* `<sample/>`
* `<httpSample/>`



## How to build it?
In order to build this jar, just run maven without any arguments:

    $ mvn

## How to use it?

    java -jar m2j.jar --input <jmeter.jtl> --output <junit.xml> [--testSuiteName <name of the test suite>] [--filter <true/false>]

If the `<name of the test suite>` argument is not given, it will default to `jmeter`.

Sometimes we want selected jmeter samplers' result to convert to junit
If the `--filter` option is not set to `true`, all the jmeter sample results will be converted to junit result as it is.
If `--filter true`, only those samples will be converted which have `@junit` as part of their name `ie label`

## License
This software is available under GNU GPL license. Please see file `LICENSE` for details.
