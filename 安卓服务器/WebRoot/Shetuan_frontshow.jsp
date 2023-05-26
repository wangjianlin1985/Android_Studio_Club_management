<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Shetuan" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Shetuan shetuan = (Shetuan)request.getAttribute("shetuan");

%>
<HTML><HEAD><TITLE>查看社团</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>负责人账号:</td>
    <td width=70%><%=shetuan.getStUserName() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=shetuan.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>社团名称:</td>
    <td width=70%><%=shetuan.getShetuanName() %></td>
  </tr>

  <tr>
    <td width=30%>社团logo:</td>
    <td width=70%><img src="<%=basePath %><%=shetuan.getShetuanPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>社团简介:</td>
    <td width=70%><%=shetuan.getShetuanDesc() %></td>
  </tr>

  <tr>
    <td width=30%>成立日期:</td>
        <% java.text.DateFormat bornDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=bornDateSDF.format(shetuan.getBornDate()) %></td>
  </tr>

  <tr>
    <td width=30%>负责人:</td>
    <td width=70%><%=shetuan.getFuzeren() %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=shetuan.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>社团备注:</td>
    <td width=70%><%=shetuan.getShetuanMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
