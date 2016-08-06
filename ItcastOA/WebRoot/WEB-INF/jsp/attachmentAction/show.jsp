<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>附件列表</title>
	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
	<script language="javascript" src="${pageContext.request.contextPath}/script/function.js" charset="utf-8"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/forum.css" />
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 附件列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<%--此处的id为上一环节版块列表传递的id参数，该参数已由forumAction实现的modeldriven自动接收封装，并放在value stack中 --%>
<s:form action="attachmentAction_list"><%--该form是为了提交筛选查询参数 post方式--%>
<div id="MainArea">
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1" style="width: 98%;">
			<font class="MenuPoint"> &gt; </font>
			<s:a action="attachmentAction_list" >附件管理</s:a>
			<%--<font class="MenuPoint"> &gt; </font>
			${forum.name}
			--%>
			<span style="margin-left:30px;">
				<input style="font-size:12px; color:black; height=20;width=80" id="BT_Add" type="button" value="上传附件" name="BT_Add" 
						 onclick="openWindow('${pageContext.request.contextPath }/attachmentAction_addUI.action','900','300')">
				<%--<s:a action="attachmentAction_addUI">
					<img align="absmiddle" src="${pageContext.request.contextPath}/style/blue/images/button/publishNewTopic.png"/>
				</s:a>--%>
			</span>
		</div>
		
		<div class="ForumPageTableBorder">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!--表头-->
				<tr align="center" valign="middle">
					<td width="3" class="ForumPageTableTitleLeft">
						<img border="0" width="1" height="1" src="${pageContext.request.contextPath}/style/images/blank.gif" />
					</td>
					<td class="ForumPageTableTitle">附件名称</td>
					<td width="70" class="ForumPageTableTitle">上传者</td>
					<td width="80" class="ForumPageTableTitle">上传时间</td>
					<td width="140" class="ForumPageTableTitle">所属目录</td>
					<td width="40" class="ForumPageTableTitle">操作</td>
					<td width="3" class="ForumPageTableTitleRight">
						<img border="0" width="1" height="1" src="${pageContext.request.contextPath}/style/images/blank.gif" />
					</td>
				</tr>
				<tr height="1" class="ForumPageTableTitleLine"><td colspan="8"></td></tr>
				<tr height=3><td colspan=8></td></tr>
					
				<!--主题列表-->
				<tbody class="dataContainer">
					<s:iterator value="recordList">
					<tr height="35" id="d0" class="template">
						<td></td>
						<td class="Topic">
							<s:a cssClass="Default" action="attachmentAction_download?id=%{id}">${fileDisplayName}</s:a>
						</td>
						<td width="70" class="ForumTopicPageDataLine" align="center">
							${author.name}
						</td>
						<td width="80" class="ForumTopicPageDataLine" align="center"><s:date name="postTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td width="140" class="ForumTopicPageDataLine">
							${directory.name}&nbsp;
						</td>
						<td width="100" class="ForumTopicPageDataLine" align="center">
							删除&nbsp;&nbsp;修改
						</td>
						<td></td>
					</tr>
					</s:iterator>
					</tbody>
					<!--主题列表结束-->	
						
					<tr height="3"><td colspan="9"></td></tr>
				
			</table>
			
			<!--其他操作-->
			<div id="TableTail">
				<div id="TableTail_inside">
					<table border="0" cellspacing="0" cellpadding="0" height="100%" align="left">
						<tr valign=bottom>
							<td></td>
							<td>
								<%--<s:select name="viewType" list="#{0:'全部主题', 1:'全部精华贴'}"/>
								<s:select name="orderBy" onchange="onSortByChange(this.value)"
									list="#{0:'默认排序（按最后更新时间排序，但所有置顶帖都在前面）', 1:'按最后更新时间排序', 2:'按主题发表时间排序', 3:'按回复数量排序'}"
								/>
								<s:select name="asc" list="#{false:'降序', true:'升序'}"/>
								<input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" align="ABSMIDDLE"/>--%>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</center>
</div>
</s:form>
<!--分页信息-->
<%@ include file="/WEB-INF/jsp/public/pageView.jspf" %>



<div class="Description">
	说明：<br />
	1，主题默认按最后更新的时间降序排列。最后更新时间是指主题最后回复的时间，如果没有回复，就是主题发表的时间。<br />
	2，帖子有普通、置顶、精华之分。置顶贴始终显示在最上面，精华贴用不同的图标标示。<br />
<s:debug></s:debug>
</div>

</body>
</html>



