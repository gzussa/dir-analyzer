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
