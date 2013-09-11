package org.gz.diranalyzer;

import java.io.IOException;
import java.net.URISyntaxException;

import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.logger.Logger;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

public class DirAnalyzer {
	
	@Option(name="-e", required=false, usage="Output format, xml(default) or json")
	private String fileExtension = Constants.XML_OUTPUT_FORMAT;
	
	@Option(name="-f", required=false, usage="File name containing the full Scan Result")
	private String fullScanResultFilePath;
	
	@Option(name="-p", required=false, usage="Previous scan file name")
	private String previousScanResultFilePath;
	
	@Option(name="-d", required=false, usage= "Result file name containing the scan difference")
	private String scanDifferenceFilePath;
	
	@Option(name="-x", required=false, usage= "Turn on debug mode")
	private boolean isDebug = false;
	
	@Argument(index=0, required=true, usage="Path to scan")
	private String pathToScan;
	
	private boolean generateDiff = false;
	
	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {	
		DirAnalyzer dirAnalyzer = new DirAnalyzer();
		//First we check parameters
		boolean check = dirAnalyzer.checkParameters(args);
		if(!check){return;}
		//Then we perform the main logic
		dirAnalyzer.execute();	
	}
	
	private void execute() throws URISyntaxException, IOException {
		long startTime = System.currentTimeMillis();
		//First we scan the directory
		Scanner scanner = new Scanner(pathToScan, fullScanResultFilePath, fileExtension, isDebug);
		Structure currentStructure = scanner.scan();
		
		//If we need to evaluate a diff
		if(generateDiff){
			DiffAnalyzer diffAnalyzer = new DiffAnalyzer(previousScanResultFilePath, scanDifferenceFilePath, fileExtension, isDebug);
			diffAnalyzer.performDiff(currentStructure);			
		}
		long endTime = System.currentTimeMillis();
		Logger.debug(isDebug, "Execution Time :"+(endTime-startTime)+" ms");
	}
	
	private boolean checkParameters(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);

        parser.setUsageWidth(80);

        try {
            // parse the arguments.
            parser.parseArgument(args);
            
            if(!Constants.JSON_OUTPUT_FORMAT.equals(fileExtension) && !Constants.XML_OUTPUT_FORMAT.equals(fileExtension)){
            	throw new CmdLineException(parser,"Output format should be either 'xml' or 'json'");
            }
            if(scanDifferenceFilePath != null){
            	generateDiff = true;
            }
            
            if(!generateDiff && fullScanResultFilePath == null){
            	throw new CmdLineException(parser,"You need to specify at least one expected result '-d' and/or '-f' options");
            }
            

        }catch(CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
        	Logger.error(e.getMessage());
        	Logger.error("java -jar "+Constants.APPLICATION_NAME+" [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            Logger.error("");

            // print option sample. This is useful some time
            Logger.error(" Example: java -jar "+Constants.APPLICATION_NAME+parser.printExample(OptionHandlerFilter.ALL));

            return false;
        }	
        return true;
	}
}
