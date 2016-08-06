package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 上传文件的逻辑目录
 * 
 * @author icelee
 * 
 */
public class Directory implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Directory parent;
	private Set<Directory> children = new HashSet<Directory>();
	private Set<Attachment> attachments = new HashSet<Attachment>();

	private String name;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Directory getParent() {
		return parent;
	}
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	public Set<Directory> getChildren() {
		return children;
	}
	public void setChildren(Set<Directory> children) {
		this.children = children;
	}
	public Set<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
