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
package org.gz.diranalyzer.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="a_systemfileinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemFile {
	
	@XmlAttribute(name="directory")
	private String directory;
	
	@XmlAttribute(name="isfile")
    private Boolean isFile;

	@XmlAttribute(name="issymboliclink")
    private Boolean isSymbolicLink;
    
	@XmlAttribute(name="name")
    private String name;
    
	@XmlAttribute(name="path")
    private String path;
    
	@XmlAttribute(name="size")
    private Long size;
    
	@XmlAttribute(name="checksum")
    private String checksum;
    
	@XmlAttribute(name="extension")
    private String extension;
    
	@XmlAttribute(name="basename")
    private String baseName;
	
	@XmlAttribute(name="action")
    private String action;

	public SystemFile() {
		super();
	}

	public SystemFile(SystemFile systemFile) {
		this.action = systemFile.getAction();
		this.baseName = systemFile.getBaseName();
		this.checksum = systemFile.getChecksum();
		this.directory = systemFile.getDirectory();
		this.extension = systemFile.getExtension();
		this.isFile = systemFile.isFile();
		this.isSymbolicLink = systemFile.isSymbolicLink();
		this.name = systemFile.getName();
		this.path = systemFile.getPath();
		this.size = systemFile.getSize();
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Boolean isFile() {
		return isFile;
	}

	public void setIsFile(Boolean isFile) {
		this.isFile = isFile;
	}

	public Boolean isSymbolicLink() {
		return isSymbolicLink;
	}

	public void setIsSymbolicLink(Boolean isSymbolicLink) {
		this.isSymbolicLink = isSymbolicLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
