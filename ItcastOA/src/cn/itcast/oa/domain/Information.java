package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Information implements Serializable{
	/**
	 * 栏目下发布信息
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cate;
	private String title;//文章标题
	private String content;//文章内容
	private String pic;//图片地址
	private String author;//发布者
	private Date postDate;//修改发布时间
	private String postIp;//发布IP
	private byte isShow;//是否显示
	private Date ctime;//创建时间
	private Set<Channel> channels = new HashSet<Channel>();//所属栏目,可以在多个栏目中显示
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getPostIp() {
		return postIp;
	}
	public void setPostIp(String postIp) {
		this.postIp = postIp;
	}
	
	public byte getIsShow() {
		return isShow;
	}
	public void setIsShow(byte isShow) {
		this.isShow = isShow;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Set<Channel> getChannels() {
		return channels;
	}
	public void setChannels(Set<Channel> channels) {
		this.channels = channels;
	}
	
	
}
