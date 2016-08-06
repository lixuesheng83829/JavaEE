<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head lang="zh-cn">
  <title>用户登录</title>
  <%@ include file="/WEB-INF/html5/public/common.jspf" %>
  <meta name="format-detection" content="telephone=no">
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
	<script>
			//被嵌套时就刷新上级窗口，window在最外面非嵌套时，它的parent是它自己本身，非空
			if (window.parent != window) {
				window.parent.location.reload(true);
			}
			//修改验证码图片的生成地址,添加时间戳避免页面缓存,保证每次均为新申请
			function changeCode(obj) {     
        		var timenow = new Date().getTime();     
           		//每次请求需要一个不同的参数，否则可能会返回同样的验证码     
        		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。     
        		obj.src="${pageContext.request.contextPath}/securityCodeImageAction_securityCode.action?timestamp="+timenow;     
    		}
	</script>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>ICELEE 内容管理系统</h1>
    <p>Content Management System</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <br>

    <s:form id="doc-vld-msg" action="userAction_login" cssClass="am-form am-form-horizontal">
      <fieldset>
      		<legend>用户登录</legend>
      <!-- 显示错误信息 -->
      <s:if test="null!=fieldErrors.login[0]">
		  <div class="am-alert am-alert-danger">
			<s:property value="%{fieldErrors.login[0]}"/>
		  </div>
	  </s:if>
      <div class="am-form-group">
	      <label for="loginName" class="am-u-md-4 am-u-lg-3 am-form-label">用户名：</label>
	      <div class="am-u-md-8 am-u-lg-9">
	      	<s:textfield type="text" id="loginName" name="loginName" minlength="3" placeholder="输入用户名（至少 3 个字符）" required="required"/>
	      </div>
	  </div>
    
      <div class="am-form-group">
	      <label for="password" class="am-u-md-4 am-u-lg-3 am-form-label">密码：</label>
	      <div class="am-u-md-8 am-u-lg-9">
	      	<s:password name="password" id="password"  required="required"/>
	      </div>
      </div>
      
      <div class="am-form-group">
	      <label for="checkNumber" class="am-u-md-4 am-u-lg-3 am-form-label " >验证码：</label>
	      <div class="am-u-md-8 am-u-lg-6">
	      	<s:textfield name="checkNumber" id="checkNumber"/>
	      </div>
	      <div class="am-u-md-8 am-u-lg-3"> 
	      	<IMG SRC="${pageContext.request.contextPath}/securityCodeImageAction_securityCode.action" 
				class="am-img-thumbnail am-radius" onclick="changeCode(this)" title="点击可更换图片" style="cursor:hand"/>
		  </div>
	  </div>
      
      <label for="remember-me">
        <input id="remember-me" type="checkbox">
       	 记住密码
      </label>
      <br />
      <div class="am-cf">
        <input type="submit" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fr">
        <input type="submit" name="" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr">
      </div>
      </fieldset>
    </s:form>
    <hr>
    <p>© 2016 AllMobilize, Licensed under www.zjsafety.com license.</p>
  </div>
</div>
</body>
</html>
