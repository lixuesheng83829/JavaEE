<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>部门列表</title>
    <%@ include file="/WEB-INF/html5/public/common.jspf"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <style type="text/css">
    	.am-btn-default {
    		background-color:#fff;
    	}
    	a.icelee-a-default:visited {
			color:#3bb4f2;
		}
		a.icelee-a-default:hover {
			color:#444;
		}
    </style>
</head>
<body>
<div class="admin-content">
	<div class="am-cf am-padding">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">部门管理</strong></div>
	</div>
	
	<div class="am-g">
		<div class="am-u-sm-12">
	        <form class="am-form">
	          <table class="am-table am-table-striped am-table-hover table-main">
	            <thead>
	              <tr>
	                <th class="table-check">
	                	<input type="checkbox" />
	                </th>
	                <th class="table-id">ID</th>
	                <th class="table-title">部门名称</th>
	                <th class="table-type">上级部门</th>
	                <th class="table-author am-hide-sm-only">职能说明</th>
	                <th class="table-set">操作</th>
	              </tr>
	          	</thead>
	          	<!-- 部门列表数据 -->
	          	<tbody>
		            <s:iterator value="#departmentList" status="stat">
		            <tr>
		              <td><input type="checkbox" /></td>
		              <td>${stat.count}</td>
		              <td><s:a action="departmentAction_list?parentId=%{id}">${name}</s:a></td>
		              <td>${parent.name}</td>
		              <td class="am-hide-sm-only">${description}</td>
		              <td>
		                <div class="am-btn-toolbar">
		                  <div class="am-btn-group am-btn-group-xs">
		                    <s:a action="departmentAction_editUI?id=%{id}"
		                    	cssClass="icelee-a-default am-btn am-btn-default am-btn-xs am-text-secondary am-hide-sm-only">
		                    		<span class="am-icon-pencil-square-o"></span>修改
		                    </s:a>
		                    <s:a action="departmentAction_delete?id=%{id}&parentId=%{parentId}" 
		                    		onclick="return window.confirm('这将删除所有的下级部门，您确定要删除吗？')"
			                    cssClass="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
			                    	<span class="am-icon-trash-o"></span>删除
		                    </s:a>
		                   </div>
		                </div>
		              </td>
		            </tr>
		            </s:iterator>
		        </tbody>
	          </table>
	          
	          <div class="am-margin-bottom">
	            <s:a action="departmentAction_addUI?parentId=%{parentId}" cssClass="am-btn am-btn-primary">
	            	新建</s:a>
	            <s:if test="#parent != null">
	            	<s:a action="departmentAction_list?parentId=%{#parent.parent.id}" 
	            	cssClass="am-btn am-btn-secondary">返回上一级</s:a>
	            </s:if>
	          </div>
	          
	          <!--说明-->	
				<div class="am-panel am-panel-default"> 
					<blockquote>
					  <p>说明：<br />
					1.列表页面只显示一层的（同级的）部门数据，默认显示最顶级的部门列表。<br />
					2.点击部门名称，可以查看此部门相应的下级部门列表。<br />
					3.删除部门时，同时删除此部门的所有下级部门。</p>
					</blockquote>
				</div>
				
	        </form>
        </div>
	</div>
</div>
<%@ include file="/WEB-INF/html5/public/jsinclude.jspf" %>
</body>
</html>


