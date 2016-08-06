<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>栏目列表</title>
    <%@ include file="/WEB-INF/html5/public/common.jspf"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <style type="text/css">
    	.am-btn-default {background-color:#fff;}
    	a.icelee-a-default:visited {color:#3bb4f2;}
		a.icelee-a-default:hover {color:#444;}
		.table-title {max-width: 300px;}
		.table-author{max-width: 100px;}
		.amz-toolbar{position:fixed;z-index:999}
		.amz-toolbar a{display: none;opacity:.7}
		.amz-toolbar a:hover{opacity:1}
		.amz-toolbar a.am-active{display:block}
		.amz-toolbar {
			display:block;opacity:1;
			color:#fff;
			margin-top:10px;
			-webkit-transition:all .3s ease-in;
			transition:all .3s ease-in}
		.amz-toolbar .am-icon-faq:hover{
			-webkit-transform:rotate(720deg);
			-ms-transform:rotate(720deg);
			transform:rotate(720deg)}
		@media only screen and (min-width:641px){
			.amz-toolbar{bottom:10px}
		}
    </style>
</head>
<body>

<div class="admin-content">
	<div class="am-cf am-padding" id="content-header">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">文章管理</strong></div>
	</div>
	
	<div class="am-g">
      <div class="am-u-sm-12 am-u-md-6">
        <div class="am-btn-toolbar">
          
          <!-- 显示错误信息 -->
	      <s:if test="null!=#request.delInfoByIds">
			  <div class="am-alert am-alert-danger">
				<s:property value="%{#request.delInfoByIds}"/>
			  </div>
		  </s:if>
          
          <div class="am-btn-group am-btn-group-xs">
            <s:a action="informationAction_addUI" cssClass="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</s:a>
            <a class="am-btn am-btn-default" onclick="deleteAll(${currentPage})"><span class="am-icon-trash-o"></span> 删除</a>
          </div>
        </div>
      </div>
      <div class="am-u-sm-12 am-u-md-3">
        <div class="am-form-group">
          <select data-am-selected="{btnSize: 'sm'}">
          </select>
        </div>
      </div>
      <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
          <input type="text" class="am-form-field">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="button">搜索</button>
          </span>
        </div>
      </div>
    </div>
	
	<div class="am-g">
      <div class="am-u-sm-12">
      
      	<s:form id="information" action="informationAction_list" cssClass="am-form">
      	
          <table class="am-table am-table-striped am-table-hover table-main">
            <thead>
              <tr>
                <th class="table-check"><input type="checkbox" /></th>
                <th class="table-id">ID</th>
                <th class="table-title">文章标题</th>
                <th class="table-author am-hide-sm-down">作者</th>
                <th class="table-date">发布时间</th>
                <th class="table-type am-hide-md-down">所属栏目</th>
                <th class="table-type am-hide-md-down">显示</th>
                <th class="table-set">操作</th>
              </tr>
            </thead>
          	<tbody>
          		<!-- 文章信息start -->
          		<s:iterator value="recordList" status="stat">
	            <tr>
	              <td><input name="infoIds" type="checkbox" value="${id}"/></td>
	              <td>${stat.count}</td>
	              
	              <td class="table-title am-text-truncate">
              		<s:a action="informationAction_show?id=%{id}" title="%{title}" >
              			${title}
              		</s:a>
	              </td>
	              
	              <td class="am-g table-author am-hide-sm-down">
	              	<div class="am-text-truncate" style="max-width:100px;" title="${author}">
	              		${author}
	              	</div>
	              </td>
	              <td class="am-g table-date"><s:date name="postDate" format="yyyy-MM-dd HH:mm:ss"/></td>
	              <td class="am-hide-md-down">
	              	<s:iterator value="channels">${name}；</s:iterator>
	              </td>
	              <td class="am-hide-md-down">
	              	<s:if test="isShow">是</s:if><s:else>否</s:else>
	              </td>
	              <td>
	                <div class="am-btn-toolbar">
	                  <div class="am-btn-group am-btn-group-xs">
	                  
	                    <s:a action="informationAction_editUI?id=%{id}" 
	                    cssClass="icelee-a-default am-btn am-btn-default am-btn-xs am-text-secondary">
		                    	<span class="am-icon-pencil-square-o"></span> 编辑</s:a>
		                    	
		                <s:a action="informationAction_delete?id=%{id}" onclick="return window.confirm('您确定要删除吗？')" 
		                cssClass="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
		                    	<span class="am-icon-trash-o"></span> 删除</s:a>
	                  </div>
	                </div>
	              </td>
	            </tr>
	            </s:iterator>
	            <!-- 文章信息end -->
           	</tbody>
		  </table>
		</s:form>
		
	  </div>
	</div>
	<hr />
    <!--分页信息-->
    <%@ include file="/WEB-INF/html5/public/pageView.jspf" %>
</div>
<!-- 回到顶部 -->
<div data-am-widget="gotop" class="am-gotop am-gotop-fixed">
  <a href="#top" title="回到顶部">
    <img class="am-gotop-icon-custom" src="http://amazeui.b0.upaiyun.com/assets/i/cpts/gotop/goTop.gif"/>
  </a>
</div>

<%@ include file="/WEB-INF/html5/public/jsinclude.jspf" %>
<script type="text/javascript">
	function deleteInfos(pageNum){
		$("#information").attr("action","${pageContext.request.contextPath}/informationAction_delByIds.action");
		$("#information").append("<input type='hidden' name='pageNum' value='"+ pageNum +"'/>");
		$("#information").submit();
	}
	/**jquery对象*/
   function deleteAll(pageNum){
	 var $selectInfo = $("input[type=checkbox][name=infoIds]");
	 var flag = false;
	 $selectInfo.each(function(){
		 if(this.checked){
			 flag=true;
			 return false;//有一个选中就可以退出循环
		 }
	 });
     if(!flag){
     	alert("请选择需要删除的信息,否则不能执行该操作!");
     	return false;
     }
     else{
     	var confirmflag = window.confirm("你确定执行批量删除吗？");
     	if(!confirmflag){
     		return false;
     	}
     	else{
     		$("#information").attr("action","${pageContext.request.contextPath}/informationAction_delByIds.action");
			$("#information").append("<input type='hidden' name='pageNum' value='"+ pageNum +"'/>");
			$("#information").submit();
     		return true;
     	}
	 }
   }
</script>
</body>
</html>



