package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.*;

public class MailTemplateDao extends DataObject {

    public String[] statusList = { "1=>정상", "0=>중지" };

    public String[] systemCodes = {
            "join=>회원가입", "findpw_authno=>인증번호"
            , "findpw_newpw=>비밀번호 재발급", "course=>수강신청"
            , "account=>입금계좌 안내", "payment=>결제완료"
            , "newarticle=>게시물등록", "qna_answer=>답변등록"
            , "formmail=>이메일 문의", "receive=>수신정보 변경"
            , "sleep_pre=>휴면전환 예고", "delivery=>배송완료"
    };

    private Malgn m;
    private MailDao mail;
//    private MailUserDao mailUser;

    public MailTemplateDao() {
        this.table = "TB_MAIL_TEMPLATE";
        this.PK = "id";

        m = new Malgn();
        mail = new MailDao();
//        mailUser = new MailUserDao();
    }

    public DataSet getList() {
        return this.find("status = 1", "template_cd id, template_nm name, template_nm value", "reg_date DESC");
    }

    public String getTemplate(String templateCd) {
        return this.getOne("SELECT content FROM " + this.table + " WHERE template_cd = '" + templateCd + "' AND status = 1");
    }

    public String fetchTemplate(String templateCd, Page p) throws Exception {
        if("".equals(templateCd) || p == null) return "";

        return p.fetchString(this.getTemplate(templateCd));
    }

    public boolean sendMail(DataSet uinfo, String templateCd, String subject, Page p) throws Exception {
        if("".equals(templateCd) || "".equals(subject) || p == null) return false;
        if(!mail.isMail(uinfo.s("email"))) return false;

        p.setRoot("/home/min52/public_html/sysop/html");
        p.setLayout("mail");
        p.setVar("user", uinfo);

        String mbody = "로그인 시 아래 임시 비밀번호를 입력해주세요";

        p.setVar("subject", subject);
        p.setVar("MBODY", mbody);

        //발송자
        String sender = "min52@malgnsoft.com";

        //발송
        m.mailFrom = sender;
        m.mail(uinfo.s("email"), "[맑은소프트] " + subject, p.fetchAll());

        //등록-메일
//        return mail.insertMail(-9, sender, subject, mbody, uinfo, "I");
        return true;
    }
}