package cn.itcast.oa.domain;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * 上传附件
 * 
 * @author icelee
 * 
 */
public class Attachment implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fileDisplayName;//文件显示名称,即原始文件名
	private String fileSaveName;//在服务器硬盘的保存文件名,考虑唯一性使用UUID
	private String fileURL;//保存路径
	private User author;//上传人员
	private Directory directory;//文件保存目录(逻辑目录)
	private Date postTime;//上传时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileDisplayName() {
		return fileDisplayName;
	}
	public void setFileDisplayName(String fileDisplayName) {
		this.fileDisplayName = fileDisplayName;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	public Directory getDirectory() {
		return directory;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	//------------------------------
	
	/**
	 * VO对象,与表单数据项相对应
	 * domain中定义一般为PO对象,与数据库相对应
	 */
	//上传的文件(File类型)
	private File [] uploads;
	//文件名
	private String [] uploadsFileName;
	//文件类型
	private String [] uploadsContentType;
	//文件保存路径,在struts配置文件中指定
	private String savePath;
	//文件下载的流属性
	private InputStream inputStream;
	
	public File[] getUploads() {
		return uploads;
	}
	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}
	public String[] getUploadsFileName() {
		return uploadsFileName;
	}
	public void setUploadsFileName(String[] uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}
	public String[] getUploadsContentType() {
		return uploadsContentType;
	}
	public void setUploadsContentType(String[] uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getSavePath() {
		return savePath;
	}
	
}
