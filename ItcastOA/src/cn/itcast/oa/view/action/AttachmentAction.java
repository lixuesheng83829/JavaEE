package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Attachment;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.FileUtils;
import cn.itcast.oa.util.HqlHelper;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class AttachmentAction extends BaseAction<Attachment>{

	private static final long serialVersionUID = 1L;
	
	public String list() throws Exception {
		List<Attachment> attachmentList = attachmentService.findAll();
		ActionContext.getContext().put("attachmentList", attachmentList);
		//带分页查询,方法内已将pageBean放入栈中
		new HqlHelper(Attachment.class, "a")//	
			//.addCondition("i.isShow=?", new Byte("1"))//isShow:1显示，0隐藏
			.addOrder("a.postTime", false)//
			.buildPageBeanForStruts2(pageNum, attachmentService);

		return "list";
	}
	public String delete() throws Exception {
		attachmentService.delete(model.getId());
		return "toList";
	}
	public String addUI() throws Exception {
//		List<Channel> topList = channelService.findTopList();
//		List<Channel> channelList = ChannelUtils.getAll(topList);
//		ActionContext.getContext().put("channelList", channelList);
		//TODO 1.准备数据(文件上产的目录结构)
		return "saveUI";
	}
	
	public String add() throws Exception {
		/**
		 * 完成文件上传
			  1：将上传的文件统一放置到upload的文件夹下
			  2：将每天上传的文件，使用日期格式的文件夹分开，并使得每个业务模块用文件夹的方式分开
			  3：上传的文件名要指定唯一，可以使用UUID的方式，也可以使用日期作为文件名
			  4：封装一个文件上传的方法，该方法可以支持多文件的上传，即支持各种格式文件的上传
			  5：保存路径path的时候，使用相对路径进行保存，这样便于项目的可移植性
		 */
		
		//1.获取表单数据,遍历文件对象的数组
		//上传时间
		Date postTime = new Date();
		//获取上传的文件
		File [] uploads = model.getUploads();
		//获取上传的文件名
		String [] fileNames = model.getUploadsFileName();
		//获取上传的文件类型
		String [] contentTypes = model.getUploadsContentType();
		//获取保存路径
		String savePath = model.getSavePath();
		if(uploads!=null&&uploads.length>0){
			
			for(int i=0;i<model.getUploads().length;i++){
				String uploadFileName = fileNames[i];
				String fileURL = FileUtils.fileUploadReturnPath(uploads[i], uploadFileName, "",savePath);
				String fileSaveName = fileURL.substring(fileURL.lastIndexOf("/")+1);
				
				Attachment att = new Attachment();
				
				att.setAuthor((User) ActionContext.getContext().getSession().get("user"));
				//model.setDirectory(directory);
				att.setFileDisplayName(uploadFileName);
				att.setFileSaveName(fileSaveName);
				att.setFileURL(fileURL);
				att.setPostTime(postTime);//上传时间
				attachmentService.save(att);
			}
		}
		return "close";
	}
	public String editUI() throws Exception {
		return "saveUI";
	}
	public String edit() throws Exception {
		return "toList";
	}
	/**
	 * @Name:download
	 * @Description:文件下载(普通方式,javaweb)
	 * @Author:icelee
	 * @Version:v1.0
	 * @Create Date:
	 * @param file:无
	 * @return:无
	 */
	/*public String download(){
		try {
			HttpServletResponse rep= ServletActionContext.getResponse();
			//1:获取文件ID获取存储路径
			Attachment attachment = attachmentService.getById(model.getId());
				// /WEB-INF/upload/2016/02/14/38244d73-3b86-4d35-badf-1b795a28a019_休假单.doc
			String fileURL = attachment.getFileURL();
			String path = ServletActionContext.getServletContext().getRealPath("")+fileURL;
			//2.使用路径path,查找到对应的文件,转化成InputStream
			InputStream in = new FileInputStream(new File(path));
			//获取文件名称,注意中文文件名的问题
			String fileName = attachment.getFileDisplayName();
			//获取操作系统平台的默认编码,中文平台默认为GBK
			//String defaultEncoding = System.getProperty("file.encoding");
			//http header要求其内容必须为iso8859-1编码
			fileName = new String(fileName.getBytes("GBK"),"ISO8859-1");
			//	*下载文件的头部信息
			//rep.setContentType("application/vnd.ms-execel");指定下载内容的类型
			rep.setHeader("Content-disposition", "attachment;filename="+fileName);//inline:内联,浏览器内在线阅览;attachment:附件形式下载
			//3.从相应对象response中获取输出流outputStream
			OutputStream os = rep.getOutputStream();
			//4.将输入流的数据读取,写到输出流中
			//IOUtils.copy(in, os);
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length=in.read(buffer))!=-1){
				os.write(buffer, 0, length);
			}
			in.close();
			os.close();
		} catch (Exception e) {
			throw new RuntimeException("download方法有问题!!"); 
		}
		return NONE;
	}
	*/
	
	/**
	 * @Name:download
	 * @Description:文件下载(struts2的方式)
	 * @Author:icelee
	 * @Version:v1.0
	 * @Create Date:
	 * @param file:无
	 * @return:无
	 */
	public String download(){
		try {
			HttpServletRequest req= ServletActionContext.getRequest();
			Attachment attachment = attachmentService.getById(model.getId());
			String fileURL = attachment.getFileURL();
			String path = ServletActionContext.getServletContext().getRealPath("")+fileURL;
			
			String fileName = attachment.getFileDisplayName();
			fileName = new String(fileName.getBytes("GBK"),"ISO8859-1");
			req.setAttribute("fileName", fileName);
			
			InputStream in = new FileInputStream(new File(path));
			//与栈顶的InputStream关联.无需实现输出流,只需将in赋值给栈顶model对象的InputStream属性即可
			model.setInputStream(in);
		} catch (Exception e) {
			throw new RuntimeException("download方法有问题!!"); 
		}
		return "download";
	}
}
