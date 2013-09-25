# Dir-Analyzer

## About
### What is it?

Dir-Analyser is a java tool that is able to take a matadata snapshot of a directory structure. It can also compare directory changes between two snapshots. Results are currently returned as generated xml files.

### Where to use it?

For example, you can use Dir-Analyzer in your build system in order to list every changes made between two builds.
You can also use Dir-Analyze in order to compare two similar directories and check differences.

## Getting Started

### How to get Dir-Analyzer
#### Build from source

Check out the sources from `https://github.com/gzussa/dir-analyzer.git` and then import the project into Eclipse as a Java Project
Then set up your project as a Maven project so you can fix dependencies. Optionally, you can set up your project as a Spring project as well (I recommend to use Eclipse STS).

#### Test and Export
Tests are available in the following path `src/test/java`. Tests are using the Junit framework. Execute `AllTests.java` with JUnit to execute all available tests.
Finally you can export your project as a executable jar so you can use it.

#### Execute and get help

To get some help but also to make sure that your executable jar works correctly, run the following command:

    java -jar diranalyser.jar

It will show the following message:

    Argument "" is required
    java -jar diranalyzer [options...] arguments...
    VAL    : Path to scan
    -d VAL : Result file name containing the scan difference
    -e VAL : Output format, xml(default) or json
    -f VAL : File name containing the full Scan Result
    -p VAL : Previous scan file name
    -x     : Turn on debug mode

    Example: java -jar diranalyzer -d VAL -e VAL -f VAL -p VAL -x
 
 ### Get a Directory Scan
 
 Let's create a test folder

    mkdir test/
    mkdir test/dir1
    vi test/file1.txt (when you are in Vim just save and quit Esc then :wq!)
    vi test/dir1/file2.txt (when you are in Vim just save and quit Esc then :wq!)

 Our test content is created. Now run the following command:
 
    java -jar diranalyzer.jar test/ -x -f full_scan_result_1.xml
 
 Then check the created file:

    vi full_scan_result_1.xml 

 ```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build date="2013-08-27T18:26:53.534-07:00">
    <systemFiles basename="test" 
        size="4096" 
        path="test" 
        name="test" 
        issymboliclink="false" 
        isfile="false" 
        directory=""/>
    <systemFiles basename="dir1" 
        size="4096" 
        path="test/dir1" 
        name="dir1" 
        issymboliclink="false" 
        isfile="false" 
        directory="test"/>
    <systemFiles basename="file2" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file2.txt" 
        name="file2.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
    <systemFiles basename="file1" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/file1.txt" 
        name="file1.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test"/>
</a_build>
```

Or we can directly get a diff result, since we don't have previous result, the tool will consider that everything is new

    java -jar diranalyzer.jar test/ -x -d diff_scan_result_1.xml
 
Here is the result we get:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build>
    <systemFiles action="CREATED" 
        basename="file2" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file2.txt" 
        name="file2.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
    <systemFiles action="CREATED" 
        basename="test" 
        size="4096" 
        path="test" 
        name="test" 
        issymboliclink="false" 
        isfile="false" 
        directory=""/>
    <systemFiles action="CREATED" 
        basename="file1" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/file1.txt" 
        name="file1.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test"/>
    <systemFiles action="CREATED" 
        basename="dir1" 
        size="4096" 
        path="test/dir1" 
        name="dir1" 
        issymboliclink="false" 
        isfile="false" 
        directory="test"/>
</a_build>
 ```

 Of course both file can be generated with one command only.
    
    java -jar diranalyzer.jar test/ -f full_scan_result_1.xml -d diff_scan_result_1.xml
 
Now let's make some modification to file system. We will remove, update and delete files
remove one file: `rm test/dir1/file2.txt` 
create a new file: `vi test/dir1/file3.txt`
`vi test/file1.txt` (write something in this file and save)
 
 Then run the tool as follow:
 `java -jar diranalyzer.jar test/ -x -d diff_scan_result_2.xml -f full_scan_result_2.xml -p full_scan_result_1.xml`
 full_scan_result_2.xml will display the new updated scan 
 diff_scan_result_2.xml only shows difference between full_scan_result_1.xml and the current test directory state
 
 `vi full_scan_result_2.xml`

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build date="2013-08-27T19:08:06.980-07:00">
    <systemFiles basename="test" 
        size="4096" 
        path="test" 
        name="test" 
        issymboliclink="false" 
        isfile="false" 
        directory=""/>
    <systemFiles basename="dir1" 
        size="4096" 
        path="test/dir1" 
        name="dir1" 
        issymboliclink="false" 
        isfile="false" 
        directory="test"/>
    <systemFiles basename="file3" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file3.txt" 
        name="file3.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
    <systemFiles basename="file1" 
        extension="txt" 
        checksum="e59ff97941044f85df5297e1c302d260" 
        size="12" 
        path="test/file1.txt" 
        name="file1.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test"/>
</a_build>
 ```

`vi diff_scan_result_2.xml`

```xml
<a_build>
    <systemFiles action="DELETED" 
        basename="file2" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file2.txt" 
        name="file2.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
    <systemFiles action="UPDATED" 
        basename="file1" 
        extension="txt" 
        checksum="e59ff97941044f85df5297e1c302d260" 
        size="12" 
        path="test/file1.txt" 
        name="file1.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test"/>
    <systemFiles action="CREATED" 
        basename="file3" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file3.txt" 
        name="file3.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
</a_build>
```

You can also run get the diff scan result without the full scan result
`java -jar diranalyzer.jar test/ -x -d diff_scan_result_2.xml -p full_scan_result_1.xml`

You can also disable debug output by removing the -x option
`java -jar diranalyzer.jar test/ -d diff_scan_result_2.xml -p full_scan_result_1.xml`

Also instead of using the full_scan_result_1.xml as previous file you can also use diff_scan_result_1.xml or even a simplified version of the xml as follow:
full_scan_result_1_simplified.xml

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build date="2013-08-27T18:26:53.534-07:00">
    <systemFiles path="test" />
    <systemFiles path="test/dir1" />
    <systemFiles checksum="d41d8cd98f00b204e9800998ecf8427e" path="test/dir1/file2.txt" />
    <systemFiles checksum="d41d8cd98f00b204e9800998ecf8427e" path="test/file1.txt" />
</a_build>
```

Then by running
`java -jar diranalyzer.jar test/ -d diff_scan_result_3.xml -p full_scan_result_1_simplified.xml`

You for diff_scan_result_3.xml you will get the following result:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build>
    <systemFiles action="DELETED" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        path="test/dir1/file2.txt"/>
    <systemFiles action="UPDATED" 
        basename="file1" 
        extension="txt" 
        checksum="e59ff97941044f85df5297e1c302d260" 
        size="12" 
        path="test/file1.txt" 
        name="file1.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test"/>
    <systemFiles action="CREATED" 
        basename="file3" 
        extension="txt" 
        checksum="d41d8cd98f00b204e9800998ecf8427e" 
        size="0" 
        path="test/dir1/file3.txt" 
        name="file3.txt" 
        issymboliclink="false" 
        isfile="true" 
        directory="test/dir1"/>
</a_build>
 ``` 
 
 
 

 
 

