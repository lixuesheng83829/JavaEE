package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Channel implements Serializable {
	/**
	 * 信息栏目
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Channel parent;//父栏目
	private Set<Channel> children = new HashSet<Channel>();//子栏目
	private Set<Intro> intros = new HashSet<Intro>();//简介
	private Set<Information> informations = new HashSet<Information>();//栏目下的文章
	private String name;
	private Date postDate;//提交修改时间
	private Date ctime;//创建时间
	private int position;//显示顺序
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Channel getParent() {
		return parent;
	}
	public void setParent(Channel parent) {
		this.parent = parent;
	}
	public Set<Channel> getChildren() {
		return children;
	}
	public void setChildren(Set<Channel> children) {
		this.children = children;
	}
	public Set<Intro> getIntros() {
		return intros;
	}
	public void setIntros(Set<Intro> intros) {
		this.intros = intros;
	}
	public Set<Information> getInformations() {
		return informations;
	}
	public void setInformations(Set<Information> informations) {
		this.informations = informations;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getCtime() {
		return ctime;
	}
	
	
}
