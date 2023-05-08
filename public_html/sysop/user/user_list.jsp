<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%
//제한
//폼체크
//객체

if(m.isPost() && f.validate()) {

}

//출력
p.setLayout("sysop");
p.setBody("user.user_list");
p.setVar("form_script", f.getScript());
p.display();
%>