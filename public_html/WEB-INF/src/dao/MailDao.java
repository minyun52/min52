package dao;

import java.io.File;
import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.regex.*;

public class MailDao extends DataObject {

    public String[] templates = {
            "default=>알림", "qna_answer=>질문답변", "join=>회원가입"
            , "course=>수강신청", "ebook=>전자책대여", "findpw_authno=>인증번호", "findpw_newpw=>새비밀번호"
            , "close1=>수강종료1일전", "close7=>수강종료7일전"
            , "payment=>결제완료", "account=>가상계좌"
    };
    public String[] types = { "I=>전체(수신거부자 포함)", "A=>수신동의자" };
    public String[] alltypes = { "I=>전체(수신거부자 포함)", "A=>수신동의자", "S=>시스템" };

    public MailDao() {
        this.table = "TB_MAIL";
    }

    public boolean isMail(String value) {
        Pattern pattern = Pattern.compile("^[a-z0-9A-Z\\_\\.\\-]+@([a-z0-9A-Z\\.\\-]+)\\.([a-zA-Z]+)$");
        Matcher match = pattern.matcher(value);
        return match.find();
    }


//    public boolean insertMail(int userId, String sender, String subject, String content, DataSet uinfo) {
//        return insertMail(userId, sender, subject, content, uinfo, "S");
//    }

//    public boolean insertMail(int userId, String sender, String subject, String content, DataSet uinfo, String mailType) {
//
//        MailUserDao mailUser = new MailUserDao();
//
//        this.item("module", "user");
//        this.item("module_id", 0);
//        this.item("user_id", userId);
//        this.item("mail_type", mailType);
//        this.item("sender", sender);
//        this.item("subject", subject);
//        this.item("content", content);
//        this.item("resend_id", 0);
//        this.item("send_cnt", 1);
//        this.item("fail_cnt", 0);
//        this.item("reg_date", Malgn.time("yyyyMMddHHmmss"));
//        this.item("status", 1);
//        int newId = this.insert(true);
//        if(newId == 0) return false;
//
//        mailUser.item("mail_id", newId);
//        mailUser.item("email", uinfo.s("email"));
//        mailUser.item("user_id", uinfo.s("id"));
//        mailUser.item("user_nm", uinfo.s("user_nm"));
//        mailUser.item("send_yn", "Y");
//        if(!mailUser.insert()) {
//            this.delete("id = " + newId + "");
//            return false;
//        }
//        return true;
//    }


    public DataSet getFiles(String path) throws Exception {
        DataSet list = new DataSet();
        File dir = new File(path);
        if(!dir.exists()) return list;

        File[] files = dir.listFiles();
        for(int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            list.addRow();
            list.put("id", filename.substring(0, filename.indexOf(".")));
            list.put("name", Malgn.getItem(list.s("id"), templates));
        }
        return list;
    }

    public String maskMail(String value) {
        if(!this.isMail(value)) return value;

        String account = Malgn.split("@", value)[0];
        int length = account.length();
        if(length < 3) {
            return Malgn.strpad(value.replace(account, ""), length, "*");
        } else if(length == 3) {
            return value.replaceAll("\\b(\\S+)[^@][^@]+@(\\S+)", "$1**@$2");
        } else {
            return value.replaceAll("\\b(\\S+)[^@][^@][^@][^@]+@(\\S+)", "$1****@$2");
        }
    }
}