<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Huodong" %>
<%@ page import="com.chengxusheji.domain.Shetuan" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Shetuan��Ϣ
    List<Shetuan> shetuanList = (List<Shetuan>)request.getAttribute("shetuanList");
    Huodong huodong = (Huodong)request.getAttribute("huodong");

%>
<HTML><HEAD><TITLE>�鿴���Ż</TITLE>
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
    <td width=30%>�id:</td>
    <td width=70%><%=huodong.getHuodongId() %></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><%=huodong.getHuodongName() %></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><%=huodong.getHuodongDesc() %></td>
  </tr>

  <tr>
    <td width=30%>�ʱ��:</td>
        <% java.text.DateFormat huodongTimeSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=huodongTimeSDF.format(huodong.getHuodongTime()) %></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%>
      <%=huodong.getShetuanObj().getShetuanName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ע:</td>
    <td width=70%><%=huodong.getHuodongMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
