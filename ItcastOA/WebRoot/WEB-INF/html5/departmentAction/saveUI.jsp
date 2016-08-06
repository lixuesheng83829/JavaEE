<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>部门设置</title>
	<%@ include file="/WEB-INF/html5/public/common.jspf" %>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>

<div class="admin-content">
	<div class="am-cf am-padding">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">部门信息</strong></div>
	</div>
	
	<div class="am-tabs-bd">
		<ul class="am-tabs-nav am-nav am-nav-tabs">
	      <li class="am-active"><a href="#tab1">部门信息</a></li>
	    </ul>
		<div class="am-tab-panel am-fade am-in am-active" id="tab1">
		
        <s:form action="departmentAction_%{id==null ? 'add' : 'edit'}" cssClass="am-form">
        	<s:hidden name="id"></s:hidden>
        	
	          <div class="am-g am-margin-top">
	            <div class="am-u-sm-4 am-u-md-2 am-text-right">上级部门</div>
	            <div class="am-u-sm-8 am-u-md-4">
	              <!-- 上级部门 -->
	              <s:select name="parentId" cssClass="SelectStyle"
	                     list="#departmentList" listKey="id" listValue="name"
	                     headerKey="" headerValue="请选择部门"/>
	              
	            </div>
	            <div class="am-hide-sm-only am-u-md-6">*必填，不可重复</div>
	          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">
              	部门名称
            </div>
            <div class="am-u-sm-8 am-u-md-4 am-u-end col-end">
              <s:textfield name="name" cssClass="am-input-sm"/>
            </div>
          </div>

          <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-12 am-u-md-2 am-text-right admin-form-text">
              	职能说明
            </div>
            <div class="am-u-sm-12 am-u-md-10">
              <s:textarea name="description" rows="10" placeholder="请使用富文本编辑插件"></s:textarea>
            </div>
          </div>
			
			
		<div class="am-margin">
		    <button type="submit" class="am-btn am-btn-primary am-btn-xs">提交保存</button>
		    <a href="javascript:history.go(-1);">
		    	<button type="button" class="am-btn am-btn-primary am-btn-xs">放弃保存</button>
		    </a>
		 </div>
		 
        </s:form>
      </div>
      </div>
</div>

<!--显示表单内容-->

<div class="Description">
	说明：<br />
	1，上级部门的列表是有层次结构的（树形）。<br/>
	2，如果是修改：上级部门列表中不能显示当前修改的部门及其子孙部门。因为不能选择自已或自已的子部门作为上级部门。<br />
</div>

</body>
</html>
