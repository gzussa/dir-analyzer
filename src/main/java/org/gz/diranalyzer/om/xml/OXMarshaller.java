/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Gregory
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
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
