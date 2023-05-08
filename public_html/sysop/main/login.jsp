<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%

//폼체크
f.addElement("login_id", null, "hname:'아이디', required:'Y'");
f.addElement("password", null, "hname:'비밀번호', required:'Y'");

//변수
String loginId = f.get("login_id");
String password = f.get("password");

//객체
UserDao user = new UserDao();

//로그인
if(m.isPost() && f.validate()) {
    password = m.encrypt(password, "SHA-256");
    //아이디 불일치
    DataSet info = user.find("login_id = '" + loginId + "' AND user_type IN ('A', 'S')");
    if(!info.next()) {
        m.jsAlert("아이디 또는 비밀번호가 일치하지 않습니다.");
        return;
    }

    //비밀번호 불일치
    if(!password.equals(info.s("password"))) {
        m.jsAlert("아이디 또는 비밀번호가 일치하지 않습니다.");
        user.item("fail_count", info.i("fail_count") + 1);
        user.update("id = " + info.i("id") + "");
        return;
    }

    //중지 상태 또는 실패횟수 초과
    if(info.i("status") != 1 || info.i("fail_count") >= 5) {
        m.jsAlert("로그인할 수 없습니다. 관리자에게 문의해 주시길 바랍니다.");
        return;
    }

    //갱신
    user.item("fail_count", 0);
    if(!user.update("id = " + info.i("id") + "")) {
        m.jsAlert("갱신하는 중 오류가 발생했습니다.");
        return;
    }

    //인증
    auth.put("ID", info.i("id"));
    auth.put("LOGINID", info.s("login_id"));
    auth.put("NAME", info.s("user_nm"));
    auth.put("TYPE", info.s("user_type"));
    auth.save();

    //이동
    m.jsReplace("/sysop/user/user_list.jsp");
    return;
}

//출력
p.setLayout("blank");
p.setBody("main.login");
p.setVar("form_script", f.getScript());
p.display();

%>
