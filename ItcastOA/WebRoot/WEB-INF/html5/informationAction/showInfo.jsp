<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>文章信息</title>
	<%@ include file="/WEB-INF/html5/public/common.jspf" %>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
	<style type="text/css">
		.am-btn-default {background-color:#fff;}
	</style>
</head>
<body>
<div class="admin-content">
	<!-- 标题显示 -->
	<div class="am-cf am-padding">
	    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">文章预览</strong></div>
	</div>
	<div class="am-g">
      <div class="am-u-sm-12 am-u-md-6">
        <div class="am-btn-toolbar">
          <div class="am-btn-group am-btn-group-xs">
            <a class="am-btn am-btn-default" href="javascript:history.go(-1);"><span class="am-icon-reply"></span> 返回</a>
          </div>
        </div>
      </div>
    </div>
	<hr class="am-article-divider"/>
	<div class="am-g am-g-fixed">
		<div class="am-u-md-9">
			<article class="am-article">
			  <div class="am-article-hd">
			    <h1 class="am-article-title">${title}</h1>
				    <p class="am-article-meta"><i class="am-icon-edit am-icon-fw"></i>${author}&nbsp;&nbsp;&nbsp;&nbsp;<i class="am-icon-calendar am-icon-fw"></i>${postDate}</p>
				    <p class="am-article-meta"></p>
			  </div>
			
			  <div class="am-article-bd">
			    <p class="am-article-lead"></p>
			    ${content}
			  </div>
			</article>
			
		</div>
	</div>
</div>
<div data-am-widget="gotop" class="am-gotop am-gotop-fixed" style="right:190px;">
  <a href="#top" title="回到顶部">
    <img class="am-gotop-icon-custom" src="http://amazeui.b0.upaiyun.com/assets/i/cpts/gotop/goTop.gif"/>
  </a>
</div>
<%@ include file="/WEB-INF/html5/public/jsinclude.jspf" %>
</body>
</html>

