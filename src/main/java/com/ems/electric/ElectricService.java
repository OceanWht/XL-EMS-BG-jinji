package com.ems.electric;

import com.alibaba.druid.util.StringUtils;
import com.ems.common.EmsConfig;
import com.ems.common.model.WarningInfo;
import com.ems.common.model.WarningSetting;
import com.ems.common.model.XlElectric;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * <p>
 * WarningService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class ElectricService {

    /**
     * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
     * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
     * http://www.jfinal.com/doc/5-13
     */
    private XlElectric dao = new XlElectric().dao();


    public Page<XlElectric> paginate(int pageNumber, int pageSize, String uid, String calctype, String day) {
        String sql = "";

        if (!StringUtils.isEmpty(calctype)) {
            sql += " and calctype =" + calctype;
        }
        if (!StringUtils.isEmpty(day)) {
            sql += " and day =" + day;
        }
        System.out.println("select * from xl_electric  where uid =" + uid + sql);
//        String sql1 = "select * from xl_electric  where uid =" + uid + sql + " limit "+(pageNumber - 1)*pageSize+","+pageSize;
//        String sql2 = "select count(1) from xl_electric  where uid =" + uid + sql;
//        List<XlElectric> el = new ArrayList<>();
//        int count = 0;
//        Prop p1 = PropKit.use("ems.config").appendIfExists("ems.config");
//        try {
//            Connection conn = null;
//            Statement stat = null;
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = (Connection) DriverManager.getConnection(p1.get("jdbcUrl"), p1.get("user"), p1.get("password").trim());
//            stat = conn.createStatement();
//            ResultSet rs = stat.executeQuery(sql1);
//            while (rs.next()) {
//                XlElectric p = new XlElectric();
//                p.setId(rs.getInt("id"));
//                p.setUid(rs.getString("uid"));
//                p.setCalctype(rs.getString("calctype"));
//                p.setDay(rs.getString("day"));
//                el.add(p);
//            }
//            rs = stat.executeQuery(sql2);
//            while (rs.next()) {
//                count = rs.getInt(1);
//            }
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (stat != null) {
//                        stat.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (conn != null) {
//                            conn.close();
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        int totalPage = 0;
//        if(count % pageSize == 0){
//            totalPage = count / pageSize;
//        }else{
//            totalPage = count / pageSize + 1;
//        }
//        return new Page<XlElectric>(el,pageNumber,pageSize,totalPage,count);
        return dao.paginate(pageNumber,pageSize,"select *"," from xl_electric where uid ="+uid +sql);
}

}
