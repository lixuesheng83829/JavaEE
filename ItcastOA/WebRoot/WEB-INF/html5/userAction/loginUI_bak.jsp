<%@ page language="java" pageEncoding="utf-8"%>
<HTML>
	<HEAD>
		<TITLE>Itcast OA</TITLE>
		<%@ include file="/WEB-INF/jsp/public/common.jspf"%>
		<LINK HREF="${pageContext.request.contextPath}/style/blue/login.css" type=text/css rel=stylesheet />
		<script type="text/javascript">
			$(function() {
				document.forms[0].loginName.focus();
			});
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
			function validateCheckNumber(){
				var checkNumber = $("#checkNumber").val();
				$.post("securityCodeImageAction_checkNumber.action",{"checkNumber":checkNumber},function(data){
    				<%--alert(data);--%>
    				if(data=="1"){
    					$("#check").html("<font color='red'>验证码输入有误</font>");
    				}else if(data=="2"){
    					$("#check").html("<font color='red'>session中没有已生成的验证码</font>");
    				}else{
    					$("#check").html("<font color='green'>验证码正确</font>");
    				}
    			},"json");
			}
		</script>
	</HEAD>

	<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody>

		<!-- 显示表单 -->
		<s:form action="userAction_login" focusElement="loginNameInput">
			<DIV ID="CenterAreaBg">
				<DIV ID="CenterArea">
					<DIV ID="LogoImg">
						<IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/logo.png" />
					</DIV>
					<DIV ID="LoginInfo">
						<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
							<tr>
								<td colspan="3">
									<!-- 显示错误信息 -->
									<font color="red"><s:fielderror />
									</font>
								</td>
							</tr>
							<TR>
								<TD width=45 CLASS="Subject">
									<IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/login/userId.gif" />
								</TD>
								<TD>
									<s:textfield name="loginName" size="20" tabindex="1" cssClass="TextField required" id="loginNameInput" />
								</TD>
								<TD ROWSPAN="2" STYLE="padding-left: 10px;">
									<INPUT TYPE="image" tabindex="4" SRC="${pageContext.request.contextPath}/style/blue/images/login/userLogin_button.gif" />
								</TD>
							</TR>
							<TR>
								<TD CLASS="Subject">
									<IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/login/password.gif" />
								</TD>
								<TD>
									<s:password name="password" id="aa" size="20" tabindex="2" showPassword="false" cssClass="TextField required" />
								</TD>
							</TR>
							<TR>
								<TD width=45 CLASS="Subject">
									<IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/login/userId.gif" />
								</TD>
								<TD>
									<input id="checkNumber" name="checkNumber" size="20" tabindex="3" onblur="validateCheckNumber()" class="TextField required"/>
									<div id="check"></div>
								</TD>
								<TD STYLE="padding-left: 10px;">
									<IMG SRC="${pageContext.request.contextPath}/securityCodeImageAction_securityCode.action" 
									onclick="changeCode(this)" title="点击可更换图片" style="cursor:hand"/>
								</TD>
							</TR>
						</TABLE>
					</DIV>
					<DIV ID="CopyRight">
						<A HREF="javascript:void(0)">&copy; 2016 版权所有 icelee</A>
					</DIV>
				</DIV>
			</DIV>
		</s:form>
	</BODY>

</HTML>

