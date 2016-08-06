package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.Date;

public class Intro implements Serializable{
	/**
	 * 栏目简介
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cate;
	private String content;
	private Date ctime;
	private Channel channel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
}
