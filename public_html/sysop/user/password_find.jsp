<%@ page import="dao.UserDao" %>
<%@ page import="malgnsoft.db.DataSet" %>
<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%
//제한

//폼체크
f.addElement("login_id", null, "hname:'아이디', required:'Y'");
f.addElement("email", null, "hname:'이메일', required:'Y'");

//객체
UserDao user = new UserDao();
MailDao mail = new MailDao();
MailTemplateDao mailTemplate = new MailTemplateDao();

//변수
String email = f.get("email");

if(m.isPost() && f.validate()) {
    DataSet uinfo = user.find("login_id = '" + f.get("login_id") + "'");
    if(!uinfo.next()) {
        m.jsAlert("아이디를 확인해주세요.");
        return;
    }

    if(mail.isMail(email)) {

        String newPasswd = m.getUniqId();

        //갱신
        if(-1 == user.execute("UPDATE " + user.table + " SET password = '" + m.encrypt(newPasswd,"SHA-256") + "', fail_count = 0 WHERE id = " + uinfo.i("id") + "")) {
            m.jsAlert("수정하는 중 오류가 발생했습니다."); return; // common.error.modify
        }

        //발송
        p.setVar("new_passwd", newPasswd);
        mailTemplate.sendMail(uinfo, "findpw_newpw", "새로운 비밀번호가 발급되었습니다.", p);

        //세션
        m.setSession("SITE_ID", "");
        m.setSession("LOGIN_ID", "");
        m.setSession("EMAIL", "");
        m.setSession("USER_NM", "");
        m.setSession("AUTH_NO", "");

        m.jsAlert("새로운 비밀번호를 이메일로 발급하여 드렸습니다. 이메일을 확인하세요."); // member.success.newpassword
        m.jsReplace("/sysop/main/login.jsp");
        return;
    } else {
        m.jsAlert("유효하지 않은 이메일입니다."); // common.error.invalid.email
        return;
    }
}

//출력
p.setLayout("sysop");
p.setBody("user.password_find");
p.setVar("form_script", f.getScript());
p.display();
%>