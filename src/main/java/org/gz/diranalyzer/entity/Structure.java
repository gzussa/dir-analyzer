package org.gz.diranalyzer.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="a_build")
@XmlAccessorType(XmlAccessType.FIELD)
public class Structure {
	
	@XmlAttribute(name="date")
	private Calendar date;

	@XmlAttribute(name="project")
	private String projectName;
	
	@XmlAttribute(name="user")
	private String userName;

    @XmlElement(name="systemFiles")
    private List<SystemFile> systemFiles;

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<SystemFile> getSystemFiles() {
		return systemFiles;
	}

	public void setSystemFiles(List<SystemFile> systemFiles) {
		this.systemFiles = systemFiles;
	}
	
	public void addSystemFile(SystemFile systemFileInfo){
		if(systemFiles == null){
			systemFiles = new ArrayList<SystemFile>();
		}
		systemFiles.add(systemFileInfo);
	}
}

