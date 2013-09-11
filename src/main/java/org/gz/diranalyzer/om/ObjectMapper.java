package org.gz.diranalyzer.om;

import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.om.xml.OXMarshaller;

public class ObjectMapper {
	
	public static void outputStructure(Structure structure, String filename, String fileExtension) {
		if(filename == null){
			return;
		}
		if(fileExtension.equals(Constants.XML_OUTPUT_FORMAT)){
			OXMarshaller.marshall(filename, structure);	
		}else if(fileExtension.equals(Constants.JSON_OUTPUT_FORMAT)){
			
		}
	}
}
