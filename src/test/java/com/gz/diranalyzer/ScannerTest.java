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
package com.gz.diranalyzer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.gz.diranalyzer.Scanner;
import org.gz.diranalyzer.constant.Constants;
import org.gz.diranalyzer.entity.Structure;
import org.gz.diranalyzer.entity.SystemFile;
import org.junit.After;
import org.junit.Test;

public class ScannerTest {	

	private final static String dir1 = "dir1"; 
	private final static String dir1path = "."+File.separator+dir1; 
	private final static String file0 = "file0.txt"; 
	private final static String file0path = "."+File.separator+file0;
	private final static String result = "result.xml"; 
	private final static String file1 = "file1.txt"; 
	private final static String file1path = "."+File.separator+dir1+File.separator+file1;
	private final static String dir2 = "dir2"; 
	private final static String dir2path = "."+File.separator+dir1+File.separator+dir2; 
	private final static String file2 = "file2.txt"; 
	private final static String file2path = "."+File.separator+dir1+File.separator+dir2+File.separator+file2;
	private final static String dir3 = "dir3"; 
	private final static String dir3path = "."+File.separator+dir1+File.separator+dir2+File.separator+dir3; 
	private final static String file3 = "file3.txt"; 
	private final static String file3path = "."+File.separator+dir1+File.separator+dir2+File.separator+dir3+File.separator+file3;
	
	@After
	public void teardown() {
		File file = new File(file3path);
		if(file.exists()){
			file.delete();
		}
		file = new File(dir3path);
		if(file.exists()){
			file.delete();
		}
		file = new File(file2path);
		if(file.exists()){
			file.delete();
		}
		file = new File(dir2path);
		if(file.exists()){
			file.delete();
		}
		file = new File(file1path);
		if(file.exists()){
			file.delete();
		}
		file = new File(dir1path);
		if(file.exists()){
			file.delete();
		}
		file = new File(result);
		if(file.exists()){
			file.delete();
		}
		file = new File(file0path);
		if(file.exists()){
			file.delete();
		}	
	}
	
	@Test
	public void testSimpleScanOnDirectory() throws URISyntaxException, IOException {
		File directory = new File(dir1path);
		assertTrue(directory.mkdir());
		
		Scanner scanner = new Scanner(dir1path, result, Constants.XML_OUTPUT_FORMAT, true);
		Structure result = scanner.scan();
		
		assertTrue(result.getProjectName() == null);
		assertTrue(result.getUserName() == null);
		assertTrue(result.getSystemFiles().size() == 1);
		SystemFile systemFile = result.getSystemFiles().get(0);
		assertTrue(systemFile.getAction() == null);
		assertTrue(systemFile.getBaseName().equals(dir1));
		assertTrue(systemFile.getChecksum() == null);
		assertTrue(systemFile.getDirectory().equals(""));
		assertTrue(systemFile.getExtension() == null);
		assertTrue(systemFile.getName().equals(dir1));
		assertTrue(systemFile.getPath().equals(dir1));
		assertTrue(systemFile.getSize() != null);
	}
	
	@Test(expected=RuntimeException.class)
	public void testSimpleScanOnFile() throws IOException, URISyntaxException {
		File file = new File(file0path);
		assertTrue(file.createNewFile());
		
		Scanner scanner = new Scanner(file0path, result, Constants.XML_OUTPUT_FORMAT, true);
		scanner.scan();
	}
	
	@Test
	public void testScanWithComplexeStructure() throws IOException, URISyntaxException {
		File directory = new File(dir1path);
		assertTrue(directory.mkdir());
		directory = new File(dir2path);
		assertTrue(directory.mkdir());
		directory = new File(dir3path);
		assertTrue(directory.mkdir());
		
		File file = new File(file1path);
		assertTrue(file.createNewFile());
		file = new File(file2path);
		assertTrue(file.createNewFile());
		file = new File(file3path);
		assertTrue(file.createNewFile());
		
		Scanner scanner = new Scanner(dir1path, result, Constants.XML_OUTPUT_FORMAT, true);
		Structure result = scanner.scan();
		
		assertTrue(result.getProjectName() == null);
		assertTrue(result.getUserName() == null);
		assertTrue(result.getSystemFiles().size() == 6);
		
		for(Iterator<SystemFile> systemFileIterator = result.getSystemFiles().iterator(); systemFileIterator.hasNext();){
			SystemFile currentSystemFile = systemFileIterator.next();
			String currentName = currentSystemFile.getName();
		
			if(dir1.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals(dir1));
					assertTrue(currentSystemFile.getChecksum() == null);
					assertTrue(currentSystemFile.getDirectory().equals(""));
					assertTrue(currentSystemFile.getExtension() == null);
					assertTrue(currentSystemFile.getName().equals(dir1));
					assertTrue(currentSystemFile.getPath().equals(dir1));
					assertTrue(currentSystemFile.getSize() != null);
			}else if(file1.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals("file1"));
					assertTrue(currentSystemFile.getChecksum() != null);
					assertTrue(currentSystemFile.getDirectory().equals(dir1));
					assertTrue(currentSystemFile.getExtension().equals("txt"));
					assertTrue(currentSystemFile.getName().equals(file1));
					assertTrue(currentSystemFile.getPath().equals(dir1+File.separator+file1));
					assertTrue(currentSystemFile.getSize() != null);
			}else if(dir2.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals(dir2));
					assertTrue(currentSystemFile.getChecksum() == null);
					assertTrue(currentSystemFile.getDirectory().equals(dir1));
					assertTrue(currentSystemFile.getExtension() == null);
					assertTrue(currentSystemFile.getName().equals(dir2));
					assertTrue(currentSystemFile.getPath().equals(dir1+File.separator+dir2));
					assertTrue(currentSystemFile.getSize() != null);
			}else if(file2.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals("file2"));
					assertTrue(currentSystemFile.getChecksum() != null);
					assertTrue(currentSystemFile.getDirectory().equals(dir1+File.separator+dir2));
					assertTrue(currentSystemFile.getExtension().equals("txt"));
					assertTrue(currentSystemFile.getName().equals(file2));
					assertTrue(currentSystemFile.getPath().equals(dir1+File.separator+dir2+File.separator+file2));
					assertTrue(currentSystemFile.getSize() != null);
			}else if(dir3.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals(dir3));
					assertTrue(currentSystemFile.getChecksum() == null);
					assertTrue(currentSystemFile.getDirectory().equals(dir1+File.separator+dir2));
					assertTrue(currentSystemFile.getExtension() == null);
					assertTrue(currentSystemFile.getName().equals(dir3));
					assertTrue(currentSystemFile.getPath().equals(dir1+File.separator+dir2+File.separator+dir3));
					assertTrue(currentSystemFile.getSize() != null);
			}else if(file3.equals(currentName)){
					assertTrue(currentSystemFile.getAction() == null);
					assertTrue(currentSystemFile.getBaseName().equals("file3"));
					assertTrue(currentSystemFile.getChecksum() != null);
					assertTrue(currentSystemFile.getDirectory().equals(dir1+File.separator+dir2+File.separator+dir3));
					assertTrue(currentSystemFile.getExtension().equals("txt"));
					assertTrue(currentSystemFile.getName().equals(file3));
					assertTrue(currentSystemFile.getPath().equals(dir1+File.separator+dir2+File.separator+dir3+File.separator+file3));
					assertTrue(currentSystemFile.getSize() != null);
			}
		}	
	}
}
