<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%
//출력
p.setLayout(null);
p.setBody("main.index");
p.setVar("form_script", f.getScript());
p.display();
%>