package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.Hashtable;

public class SiteDao extends DataObject {

    public String[] statusList = { "1=>정상", "0=>중지" };
    public String[] pgList = { "lgu=>LG유플러스", "inicis=>이니시스", "allat=>올앳", "none=>사용안함" };
    public String[] ovpVendors = { "W=>위캔디오", "C=>KOLLUS", "F=>CDN" };
    public String[] types = { "C=>일반", "I=>사내", "B=>영업", "S=>지원", "E=>기타" };
    public String[] pstatusList = { "1=>세팅", "2=>디자인", "3=>테스트", "4=>가오픈", "5=>중단", "9=>운영" };
    public String[] alertTypes = { "P=>주문결제정보" };
    public String[] oauthVendors = { "naver=>네이버", "kakao=>카카오", "google=>구글", "facebook=>페이스북" };
    //public String[] skinRoots = { "2014=>/home/lms/public_html/html", "2017=>/home/demo/public_html/html" };

    private static Hashtable<String, DataSet> cache = new Hashtable<String, DataSet>();



    public SiteDao() {
        this.table = "TB_SITE";
        this.PK = "id";
    }

    public DataSet getNames() {
        return find("status != -1", "id, company_nm name", "id ASC");
    }

    public String getCourseIdx(int sid) {
        DataSet rs = query("SELECT distinct(course_id) id FROM LM_CATEGORY_COURSE WHERE site_id = " + sid);
        StringBuffer sb = new StringBuffer();
        while(rs.next()) {
            sb.append(",");
            sb.append(rs.s("id"));
        }
        if(rs.size() > 0) return sb.substring(1);
        else return "0";
    }

    public DataSet getSiteInfo(String domain) {
        DataSet info = cache.get(domain);
        if(info == null) {
            query("SELECT 1");
            info = find("(domain = '" + domain + "' OR domain2 = '" + domain + "') AND status = 1");
            if(info.next()) cache.put(domain, info);
            else {
                info = find("id = 1 AND status = 1");
                if(info.next()) cache.put(domain, info);
            }
        }
        return info;
    }

    public String getCenterWebUrl() {
        return getOne("SELECT domain FROM " + this.table + " WHERE id = 1");
    }
    public String getCenterDataDir() {
        return getOne("SELECT doc_root FROM " + this.table + " WHERE id = 1") + "/data";
    }

    public DataSet getList() {
        return find("status != -1", "*", "site_nm ASC");
    }

    public void remove(String domain) {
        cache.remove(domain);
    }

    public void clear() {
        cache.clear();
    }

    public boolean checkIP(String clientIP, String rule) {
        String[] ips = Malgn.split(",", rule);
        boolean flag = false;
        if(clientIP.equals("115.91.52.203")) return true;
        for(int i=0; i<ips.length; i++) {
            String ip = ips[i].trim();
            if(ip.endsWith("*")) flag = clientIP.startsWith(ip.replace("*", ""));
            else flag = clientIP.equals(ip);
            if(flag) break;
        }
        return flag;
    }

    public boolean isValidUser(String sid, int userId) {
        if(userId == 0 || "".equals(sid)) return false;
        DataSet info = query("SELECT 1 FROM TB_USER WHERE id = " + userId + " AND access_token = '" + sid + "'");
        return info.next();
    }

}