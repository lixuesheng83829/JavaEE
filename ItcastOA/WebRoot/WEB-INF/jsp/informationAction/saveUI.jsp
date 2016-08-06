<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>发表新文章</title>
	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/forum.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>
	<script type="text/javascript">
		$(function(){
		var oFCKeditor = new FCKeditor('content');//提交表单时本字段使用的参数名，用以向服务器端提交数据
		oFCKeditor.BasePath = "${pageContext.request.contextPath}/fckeditor/"; //必须设置,指定脚本依赖的样式等editor文件夹路经,必须以/结尾
		oFCKeditor.Height = "95%";
		oFCKeditor.Width = "95%";
		oFCKeditor.ToolbarSet = "bbs"; //设定工具栏拥有的工具,默认配置文件fckconfig.js,经拓展在myconfig.js中配置
		//oFCKeditor.Create();//通过在HTML创建输入工具框来使用FCKeditor的一种方式
		oFCKeditor.ReplaceTextarea();//替换id或name为指定值的textarea,在new FCKeditor中定义具体值
		});
	</script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 发表新文章
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
<s:form action="informationAction_%{id == null ? 'add':'edit'}" cssStyle="margin: 0; padding: 0;">
	<s:hidden name="id"></s:hidden>
	
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1">
			<div width=85% style="float:left">
			</div>
		</div>
		<div class="ItemBlockBorder">
			<table border="0" cellspacing="1" cellpadding="1" width="100%" id="InputArea">
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">文章标题</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<s:textfield  name="title" cssClass="InputStyle" cssStyle="width:100%"/></div>
					</td>
				</tr>
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">作者</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<s:textfield  name="author" cssClass="InputStyle" cssStyle="width:100%"/></div>
					</td>
				</tr>
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">是否显示</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<s:radio name="isShow" list="#{'1':'显示','0':'隐藏'}"></s:radio></div>
					</td>
				</tr>
				<%--FCKEDITOR输入框--%>
				<tr height="240">
					<td class="InputAreaBg"><div class="InputTitle">文章内容</div></td>
					<td class="InputAreaBg">
						<div class="InputContent">
							<s:textarea name="content" cssStyle="width:650px;height:200px"></s:textarea>
						</div>
					</td>
				</tr>
				<!-- 显示栏目列表 -->
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">发布栏目</div></td>
					<td class="InputAreaBg"><div class="InputContent">
						<div class="ItemBlockBorder">
		            <div class="ItemBlock">
		                <table cellpadding="0" cellspacing="0" class="mainForm">
		                    <tr>
								<td width="100">栏目列表</td>
		                        <td>
		                        
		                        	<s:select name="channelIds" list="channelList" 
		                        	listKey="id" listValue="name" multiple="true" size="10" cssClass="SelectStyle">
		                               
		                            </s:select>
		                            按住Ctrl键可以多选或取消选择
		                        </td>
		                    </tr>
		                </table>
		            </div>
        </div>
					</td>
				</tr>
				
				<tr height="30">
					<td class="InputAreaBg" colspan="2" align="center">
						<input type="image" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" style="margin-right:15px;"/>
						<a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/blue/images/button/goBack.png"/></a>
					</td>
				</tr>
			</table>
		</div>
	</center>
</s:form>
</div>

<div class="Description">
	说明：<br />
	
</div>

</body>
</html>

