# Dir-Analyzer

## About
### What is it?

Dir-Analyser is a java tool that is able to take a matadata snapshot of a directory structure. It can also compare directory changes between two snapshots. Results are currently returned as generated xml files.

### Where to use it?

For example, you can use Dir-Analyzer in your build system in order to list every changes made between each builds.
You can also use Dir-Analyze in order to compare two similar directories and check differences.

## Getting Started

### How to get Dir-Analyzer
#### Build from source

Check out the sources from `https://github.com/gzussa/dir-analyzer.git` and then import the project into Eclipse as a Java Project.
Then set up your project as a Maven project so you can fix dependencies. Optionally, you can set up your project as a Spring project as well (I recommend to use Eclipse STS).

#### Test and Export
Tests are available in the following path `src/test/java`. Tests are using the JUnit framework. Execute `AllTests.java` with JUnit to run all available tests.
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
 
 Let's create a test folder as a get started scenario in order to better demonstrate how this tool works.

`mkdir test/`

`mkdir test/dir1`

`vi test/file1.txt` (when you are in Vim just save and quit Esc then :wq!)

`vi test/dir1/file2.txt` (when you are in Vim just save and quit Esc then :wq!)

 Now, you have created your environment scenario, run the following command:
 
`java -jar diranalyzer.jar test/ -x -f full_scan_result_1.xml`
 
 The `-x` option is used to display Debug information on stdout.
 The `-f` option is used to tell Dir-Analyzer to generate a full result scan.
 Then check the created file `vi full_scan_result_1.xml` : 

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

### Get a Difference Directory Scan

#### Generate First Difference Result Scan 

In order to get a difference result, you need to pass a previous  result scan (a previous snapshot result) as a argument to the tool. The Dir-Analyzer tool will evaluate the difference between the current state of the evaluated folder and the previous generated result scan file passed as parameter.

If you don't pass a previous generated result scan file, the tool will consider that everything is new

`java -jar diranalyzer.jar test/ -x -d diff_scan_result_1.xml`

 Note that we are not using the `-f` option here. Rather, we are using the `-d` option that is used to generate a difference result scan.
 
Here is the result you should get:

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
 
You can see that the result of the `-d` option is very similar to the result generated by the `-f` option. The `-d` option added an additionnal field called `action` to the `systemFiles` element. Since not previous generetated result scan has been provided, the tool consider that everything is new.

 Of course both file (the full scan and the difference scan) can be generated with one command only by simply using both option `-f` and `-d` at the same time.
    
`java -jar diranalyzer.jar test/ -f full_scan_result_1.xml -d diff_scan_result_1.xml`
 
#### Generate Ongoing Difference Result Scan 

First of all, let's make some modification to our test folder. We will remove, update and delete files as follow:

remove one file: `rm test/dir1/file2.txt` 

create a new file: `vi test/dir1/file3.txt`

`vi test/file1.txt` (write something in this file and save)
 
Now, let's execute the tool as follow:
 `java -jar diranalyzer.jar test/ -x -d diff_scan_result_2.xml -f full_scan_result_2.xml -p full_scan_result_1.xml`
 
 The `-p` option is used to specify the previous generated result scan we want to use in order to perform the comparaison with the current state of the folder.
 
 `full_scan_result_2.xml` will display the new updated full result scan.
 
 `diff_scan_result_2.xml` will only shows difference between `full_scan_result_1.xml` and the current test directory state.
 
`vi full_scan_result_2.xml`:

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

No surprises, we are getting an updated full result scan here.

`vi diff_scan_result_2.xml`:

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

As expected, the difference result scan shows what have been `CREATED`, `UPDATED` and `DELETED`. Files and Directories that haven't been changed are not shown here but are listed in the full result scan above.

#### How to optimize everything

You can also generate the difference result scan without generating the full result scan.

`java -jar diranalyzer.jar test/ -x -d diff_scan_result_2.xml -p full_scan_result_1.xml`

You can also disable debug output by removing the `-x` option.

`java -jar diranalyzer.jar test/ -d diff_scan_result_2.xml -p full_scan_result_1.xml`

Also, instead of using the `full_scan_result_1.xml` as previous file, you can use a simplified version of the xml as follow:
`full_scan_result_1_simplified.xml`. What the tool care about are the `path` and `checksum` attributes:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<a_build date="2013-08-27T18:26:53.534-07:00">
    <systemFiles path="test" />
    <systemFiles path="test/dir1" />
    <systemFiles checksum="d41d8cd98f00b204e9800998ecf8427e" path="test/dir1/file2.txt" />
    <systemFiles checksum="d41d8cd98f00b204e9800998ecf8427e" path="test/file1.txt" />
</a_build>
```

Then by running the following command:
`java -jar diranalyzer.jar test/ -d diff_scan_result_3.xml -p full_scan_result_1_simplified.xml`

By doing so, you will get the following result for `diff_scan_result_3.xml`:

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
Since you are using a simplified previous generated full result scan, some attributes won't be displayed for deleted files or directories.