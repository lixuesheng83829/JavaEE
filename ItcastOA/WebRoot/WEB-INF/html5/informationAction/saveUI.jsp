<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>部门设置</title>
	<%@ include file="/WEB-INF/html5/public/common.jspf" %>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
	<%@ include file="/WEB-INF/html5/public/jsinclude.jspf" %>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ckeditor/config.js"></script>
	<script type="text/javascript">
		$(function(){
			CKEDITOR.replace('content');
		});
	</script>
	
</head>
<body>
<div class="admin-content">
	<!-- 标题显示 -->
	<div class="am-cf am-padding">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">新建文章</strong></div>
	</div>
	
	    <div class="am-tabs am-margin" data-am-tabs>
	    
			<ul class="am-tabs-nav am-nav am-nav-tabs">
		      <li class="am-active"><a href="#tab1">文章信息</a></li>
		    </ul>
	    	
	    	<div class="am-tabs-bd">
	    	
			<div class="am-tab-panel am-fade am-in am-active" id="tab1">
				<s:form action="informationAction_%{id == null ? 'add':'edit'}" cssClass="am-form">
		        	  <s:hidden name="id"></s:hidden>
	
			          <div class="am-g am-margin-top">
				            <div class="am-u-sm-4 am-u-md-2 am-text-right">文章标题</div>
				            <div class="am-u-sm-8 am-u-md-10 am-u-end col-end">
				              	<s:textfield name="title" cssClass="am-input-sm"/>
				            </div>
			          </div>
			          
			          <div class="am-g am-margin-top">
				            <div class="am-u-sm-4 am-u-md-2 am-text-right">
				              	作者
				            </div>
				            <div class="am-u-sm-8 am-u-md-10 am-u-end col-end">
				              	<s:textfield name="author" cssClass="am-input-sm"/>
				            </div>
			          </div>
			          
			          <div class="am-g am-margin-top">
			            <div class="am-u-sm-4 am-u-md-2 am-text-right">
			              	是否显示
			            </div>
			            <div class="am-u-sm-8 am-u-md-10 am-u-end col-end">
			              <s:radio name="isShow" list="#{'1':'显示','0':'隐藏'}"></s:radio>
			            </div>
			          </div>
		
			          <div class="am-g am-margin-top-sm">
			            <div class="am-hide-sm-down am-u-md-2 am-text-right admin-form-text">
			              	文章内容
			            </div>
			            <div class="am-show-sm-down am-u-sm-12 am-text-left admin-form-text">
			              	文章内容
			            </div>
			            <div class="am-u-sm-12 am-u-md-10">
			            	<s:textarea name="content" placeholder="22请输入文章内容"></s:textarea>
			            </div>
			          </div>
			          
			          <div class="am-g am-margin-top-sm">
			            <div class="am-hide-sm-down am-u-md-2 am-text-right admin-form-text">
			              	栏目列表
			            </div>
			            <div class="am-show-sm-down am-u-sm-12 am-text-left admin-form-text">
			              	栏目列表
			            </div>
			            <div class="am-u-sm-12 am-u-md-10">
			            	<s:select name="channelIds" list="channelList" cssClass="SelectStyle"
			                        	listKey="id" listValue="name" multiple="true" size="10">
			                </s:select>按住Ctrl键可以多选或取消选择
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
</div>
<!--显示表单内容-->

<s:debug></s:debug>	

</body>
</html>

