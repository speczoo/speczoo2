
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    
    String[] paths = request.getParameterValues("Name"); 
	for(int i=0;i<paths.length;i++){
		
		filepaths[i]=basePath+(String)paths[i];
		out.println("~~~~~~~~~~"+filepaths[i]);

	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>Specview</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/index.css">
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">

    
    </script>
</head>

<body>
	This is a test jsp.
	<%-- 
	<jsp:plugin code="spv.SpecviewApplet.class" codebase="graduation1/../resource/applet" archive="specview_app.jar" type="applet" width="800"
            height="600"></jsp:plugin>
         --%>  

           
     
</body>
</html>
            
<%--  <jsp:plugin code="spv.SpecviewApplet.class" codebase="./applet" archive="specview_app.jar" type="applet" width="800"
            height="600"></jsp:plugin>
            --%>
            
            
   