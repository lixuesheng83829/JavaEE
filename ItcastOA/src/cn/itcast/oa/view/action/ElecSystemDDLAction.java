package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Attachment;
import cn.itcast.oa.domain.ElecSystemDDL;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.IElecSystemDDLService;
import cn.itcast.oa.util.FileUtils;
import cn.itcast.oa.util.HqlHelper;

import com.opensymphony.xwork2.ActionContext;
@Controller("elecSystemDDLAction")
@Scope("prototype")
public class ElecSystemDDLAction extends BaseAction<ElecSystemDDL>{

	private static final long serialVersionUID = 1L;
	
	/**  
	* @Name: list
	* @Description: 数据字典列表
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-6-9（创建日期）
	* @Parameters: 无
	* @Return: String：跳转到/WEB-INF/html5/elecSystemDDLAction/list.jsp
	*/
	public String list() throws Exception {
		PageBean elecSystemDDLPageBean = elecSystemDDLService.findAllByDistinct(this.pageNum);
		ActionContext.getContext().put("elecSystemDDLPageBean", elecSystemDDLPageBean);
		
		return "list";
	}
	
	
	/*public String delete() throws Exception {
		elecSystemDDLService.delete(model.getSeqID());
		return "toList";
	}*/
	public String addUI() throws Exception {
//		List<Channel> topList = channelService.findTopList();
//		List<Channel> channelList = ChannelUtils.getAll(topList);
//		ActionContext.getContext().put("channelList", channelList);
		//TODO 1.准备数据(文件上产的目录结构)
		return "saveUI";
	}
	
	/**  
	* @Name: editUI
	* @Description: 跳转到编辑数据字典的页面
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-7-30（创建日期）
	* @Parameters: 无
	* @Return: String：跳转到/WEB-INF/html5/elecSystemDDLAction/dictionaryEdit.jsp
	*/
	public String editUI() throws Exception {
		//1.获取数据类型
		String keyword = model.getKeyword();
		//2.以数据类型为条件查询数据字典，返回List<ElecSystemDDL>
		List<ElecSystemDDL> elecSystemDDLList = elecSystemDDLService.findSystemDDLListByKeyword(keyword,pageNum);
		ActionContext.getContext().put("elecSystemDDLList", elecSystemDDLList);
		return "editUI";
	}
	/**  
	* @Name: save
	* @Description: 保存数据字典
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-8-1（创建日期）
	* @Parameters: 无
	* @Return: String：重定向到/WEB-INF/html5/elecSystemDDLAction/dictionaryEdit.jsp
	*/
	public String save() throws Exception {
		elecSystemDDLService.saveSystemDDL(model);
		return "save";
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
	/*public String download(){
		try {
			HttpServletRequest req= ServletActionContext.getRequest();
			Attachment attachment = attachmentService.getById(model.getSeqID());
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
	}*/
}
