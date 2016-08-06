<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html class="no-js">
<head>
  <title>导航菜单</title>
  <%@ include file="/WEB-INF/html5/public/common.jspf" %>
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/i/app-icon72x72@2x.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>
<div id="Menu" class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
	    <ul class="am-list admin-sidebar-list" id="collapase-nav">
	    	<%--顶级菜单 --%>
	        <s:iterator value="#application.topPrivilegeList">
		        <s:if test="#session.user.hasPrivilegeByName(name)">
		        	<li class="am-panel">
			        	<a data-am-collapse="{parent:'#collapase-nav', target:'#nav-${id}'}">
			        		<img src="style/images/MenuIcon/${icon}" class="Icon" /> ${name}<i class="am-icon-angle-right am-fr am-margin-right"></i>
			        	</a>
			        	 <%-- 二级菜单,display: none;默认不展开二级菜单--%>
			        	<ul class="am-list am-list-border am-collapse admin-sidebar-sub" id="nav-${id}">
					        <s:iterator value="children">
	                		<s:if test="#session.user.hasPrivilegeByName(name)">
						        <li>
						        	<a href="${pageContext.request.contextPath}/${url}.action" target="right">
						        		<i class="am-icon-user am-margin-left-sm"></i> ${name} 
						        	</a>
						        </li>
						    </s:if>
	                		</s:iterator>
					    </ul>
		        	</li>
		        </s:if>
	        </s:iterator>
	    </ul>
    </div>
   </div>
</div>
<%@ include file="/WEB-INF/html5/public/jsinclude.jspf" %>
</body>
</html>

