<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    ClassInfo classInfo = (ClassInfo)request.getAttribute("classInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改班级</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var classNo = document.getElementById("classInfo.classNo").value;
    if(classNo=="") {
        alert('请输入班级编号!');
        return false;
    }
    var className = document.getElementById("classInfo.className").value;
    if(className=="") {
        alert('请输入班级名称!');
        return false;
    }
    var collegeName = document.getElementById("classInfo.collegeName").value;
    if(collegeName=="") {
        alert('请输入所在学院!');
        return false;
    }
    var specialName = document.getElementById("classInfo.specialName").value;
    if(specialName=="") {
        alert('请输入所属专业!');
        return false;
    }
    var banzhuren = document.getElementById("classInfo.banzhuren").value;
    if(banzhuren=="") {
        alert('请输入班主任!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="ClassInfo/ClassInfo_ModifyClassInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>班级编号:</td>
    <td width=70%><input id="classInfo.classNo" name="classInfo.classNo" type="text" value="<%=classInfo.getClassNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>班级名称:</td>
    <td width=70%><input id="classInfo.className" name="classInfo.className" type="text" size="20" value='<%=classInfo.getClassName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所在学院:</td>
    <td width=70%><input id="classInfo.collegeName" name="classInfo.collegeName" type="text" size="20" value='<%=classInfo.getCollegeName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所属专业:</td>
    <td width=70%><input id="classInfo.specialName" name="classInfo.specialName" type="text" size="20" value='<%=classInfo.getSpecialName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>成立日期:</td>
    <% DateFormat bornDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="classInfo.bornDate"  name="classInfo.bornDate" onclick="setDay(this);" value='<%=bornDateSDF.format(classInfo.getBornDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>班主任:</td>
    <td width=70%><input id="classInfo.banzhuren" name="classInfo.banzhuren" type="text" size="20" value='<%=classInfo.getBanzhuren() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
