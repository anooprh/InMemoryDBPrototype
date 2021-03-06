In Memory Database Prototype
--------

***

DEPENDENCIES: JAVA 1.7+(not tested with older versions),
 
environment variable 'JAVA_HOME' must be set properly to your java installation

***

***SETTING UP PROJECT FOR BROWSING CODE***

This project is uploaded at [github] (https://github.com/anooprh/InMemoryDBPrototype) for easy viewing

```shell 
$ git clone https://github.com/anooprh/InMemoryDBPrototype    
$ cd InMemoryDBPrototype
```
First make sure all tests are correct by running, 

```shell
$ ./gradlew test (on Linux / MacOS) 
$ ./gradlew.bat test (on Windows ? not tested ?)
```

If all tests pass, then you can set up the project for your IDE. 

```shell
$ ./gradlew idea [or eclipse] 
```

***

***BUILDING AND RUNNING THE CODE***

To quickly run the program

```shell
$ ./gradlew -q run
```
For the inputs given as the requirements, the inputs are stored in a sample_inputs/ and can be fed directly

```shell
$ ./gradlew -q run < sample_inputs/sample1
$ ./gradlew -q run < sample_inputs/sample2
$ ./gradlew -q run < sample_inputs/sample3
$ ./gradlew -q run < sample_inputs/sample4
$ ./gradlew -q run < sample_inputs/sample5
$ ./gradlew -q run < sample_inputs/sample6
```

