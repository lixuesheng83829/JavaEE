<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>栏目列表</title>
    <%@ include file="/WEB-INF/html5/public/common.jspf"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>

<div class="admin-content">
	<div class="am-cf am-padding">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">文章管理</strong></div>
	</div>
	<div class="am-g">
      <div class="am-u-sm-12">
        <form class="am-form">
          <table class="am-table am-table-striped am-table-hover table-main">
            <thead>
              <tr>
                <th class="table-check"><input type="checkbox" /></th><th class="table-id">ID</th><th class="table-title">标题</th><th class="table-type">类别</th><th class="table-author am-hide-sm-only">作者</th><th class="table-date am-hide-sm-only">修改日期</th><th class="table-set">操作</th>
              </tr>
            </thead>
          	<tbody>
	            <tr>
	              <td><input type="checkbox" /></td>
	              <td>1</td>
	              <td><a href="#">Business management</a></td>
	              <td>default</td>
	              <td class="am-hide-sm-only">测试1号</td>
	              <td class="am-hide-sm-only">2014年9月4日 7:28:47</td>
	              <td>
	                <div class="am-btn-toolbar">
	                  <div class="am-btn-group am-btn-group-xs">
	                    <button class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	                    <button class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-copy"></span> 复制</button>
	                    <button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</button>
	                  </div>
	                </div>
	              </td>
	            </tr>
           	</tbody>
		  </table>
		</form>
	  </div>
	</div>
</div>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 栏目管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>
 
<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="150px">栏目名称</td>
				<td width="150px">上级栏目名称</td>
				<td width="200px">栏目简介</td>
				<td>相关操作</td>
            </tr>
        </thead>
 
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" >
			<s:iterator value="#channelList">
			<tr class="TableDetail1 template">
				<td><s:a action="channelAction_list?parentId=%{id}"> ${name}&nbsp;</s:a></td>
				<td>${parent.name}&nbsp;</td>
				<td>${description}&nbsp;</td>
				<td>
					<s:a action="channelAction_delete?id=%{id}&parentId=%{parentId}" onclick="return window.confirm('这将删除所有的下级栏目，您确定要删除吗？')">删除</s:a>
					<s:a action="channelAction_editUI?id=%{id}" >修改</s:a>
					&nbsp;
				</td>
			</tr>
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <s:a action="channelAction_addUI?parentId=%{parentId}"><img src="${pageContext.request.contextPath}/style/images/createNew.png" /></s:a>
            <s:if test="#parent != null">
            	<s:a action="channelAction_list?parentId=%{#parent.parent.id}"><img src="${pageContext.request.contextPath}/style/images/goBack.png" /></s:a>
            </s:if>
        </div>
    </div>
</div>
 
<!--说明-->	
<div id="Description"> 
	说明：<br />
	1，列表页面只显示一层的（同级的）部门数据，默认显示最顶级的部门列表。<br />
	2，点击部门名称，可以查看此部门相应的下级部门列表。<br />
	3，删除部门时，同时删除此部门的所有下级部门。
	<s:debug></s:debug>
</div>
</body>
</html>


