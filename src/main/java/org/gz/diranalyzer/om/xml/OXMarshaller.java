package org.gz.diranalyzer.om.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.logger.Logger;
import org.gz.diranalyzer.util.FileUtil;

public class OXMarshaller {
	
	public static void marshall(String filename, Structure structure){
		try{
			File file = new File(filename);
			JAXBContext jaxbContext = JAXBContext.newInstance(Structure.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
			jaxbMarshaller.marshal(structure, file);
			
		} catch (JAXBException e) {
			Logger.error(e.getMessage());
	    }
	}
	
	public static Structure unmarshall(String filename) {
		try {
			File file = FileUtil.findFromPath(filename);
			if(file == null){return null;}
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Structure.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Structure structure = (Structure) jaxbUnmarshaller.unmarshal(file);
		
			return structure;
		}catch(Exception e){
			Logger.error("File "+filename+" not found");			
		}
		return null;	
	}
}
