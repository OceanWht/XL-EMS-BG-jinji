package com.ems.warning;

import com.ems.common.model.WarningInfo;
import com.ems.common.model.WarningSetting;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


/**
 * 本 ems 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * WarningService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class WarningService {
	
	/**
	 * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
	 * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
	 * 			http://www.jfinal.com/doc/5-13
	 */
	private WarningSetting dao = new WarningSetting().dao();
	private WarningInfo idao = new WarningInfo().dao();


	public Page<WarningSetting> paginate(int pageNumber, int pageSize,String userid,int dtype) {
		return dao.paginate(pageNumber, 10000, "select *", "from warning_setting where dtype="+dtype+" and userid='"+userid+"' order by id asc");
	}

	public Page<WarningInfo> ipaginate(int pageNumber, int pageSize,String userid,int type,int wtype,String sdt,String edt) {

		String sql="";
		if (sdt.length()>8){

			sql += " and addtime>='"+sdt+"'";

		}
		if (edt.length()>8){

			sql += " and addtime<='"+edt+"'";

		}
		if(wtype==1){

			sql += " and warningtype<30";
		}
		if(wtype==2){

			sql += " and warningtype>=30";
		}
		System.out.println("select * from warning_info  where dtype="+type+"  and   userid='"+userid+"' "+sql+"  order by readflag asc,id desc");
		return idao.paginate(pageNumber, pageSize, "select *", "from warning_info  where dtype="+type+"  and   userid='"+userid+"' "+sql+"  order by readflag asc,id desc");
	}

	public void readmsg(int dtype,String userid){

        String sql = "update warning_info set readflag=1 where  dtype="+dtype+" and  userid='"+userid+"' ";
        Db.update(sql);
    }

	public List<Record> warningcount(int dtype,int wtype,int read, String warningdate,String userid){

		String sql = "select warningtype,read,count(*) as warningnum from warning_info where  dtype="+dtype+" and  userid='"+userid+"' ";

		if(dtype>-1){
			sql+=" and dtype="+dtype;
		}

		if(wtype>-1){
			sql+=" and warningtype="+wtype;
		}
		if(read>-1){

			sql+=" and read="+read;
		}
		if(warningdate!=null){

			sql+=" and warningdate="+warningdate;
		}

		sql += " group by warningtype ";
		if(read>-1){

			sql+=" ,read ";
		}
		return Db.find(sql);
	}
	public  Record  overview(int dtype,String userid){

		//String sql = "select count(*) as warningnum from warning_info where  dtype="+dtype+" and  userid='"+userid+"' ";
		String sql = "select (select count(*)   from warning_info where dtype="+dtype+" and  userid='"+userid+"'  and readflag=0 ) as unread,";

		//sql+="  (select  (TO_DAYS(NOW()) - TO_DAYS(addtime)) as daynum  from warning_info where  dtype="+dtype+" and  userid='"+userid+"'  order by daynum asc LIMIT 0,1) as safeday,";

		//获取数据库全年的异常天数，如果当前日期的年等于系统时间的年，则取年月日，去重计数返回
		sql+="(select  count( distinct IF(date_format(addtime ,'%Y')=year(SYSDATE()),date_format(addtime ,'%Y-%m-%d' ),null)) from warning_info where  dtype="+dtype+" and  userid=\'"+userid+"\') as safeday,";

		sql+="(select  100-count(*)*2   from warning_info where  dtype="+dtype+" and  userid='"+userid+"' and  DATE_FORMAT( addtime, '%Y%m' ) = DATE_FORMAT( CURDATE() , '%Y%m' ) ) as healthscore,";

		sql+="(select  count(*) from warning_info where  dtype="+dtype+" and  userid='"+userid+"' and (TO_DAYS(NOW()) - TO_DAYS(addtime)) =1 ) as yestodaynum";

		return Db.findFirst(sql);
	}
	public  List<Record> overview2(String userid){

		String sql="";
		sql +="select CONCAT(tm,'_dataid',dtype) as dataname,num ";
		sql +=" from(select dtype,case when  TO_DAYS(NOW())- TO_DAYS(addtime)=0 then 't'  else 'y'  end as tm,count(*) as num from warning_info";
		sql +=" where TO_DAYS(NOW())- TO_DAYS(addtime)<2  and userid='"+userid+"'   GROUP BY dtype,tm) as tt ";
		sql +=" UNION ";
		sql +=" select 	case when  TO_DAYS(NOW())- TO_DAYS(addtime)=0 then 't_count'  else 'y_count' end as tm, count(*) as num";
		sql +=" from warning_info where TO_DAYS(NOW())- TO_DAYS(addtime)<2 and userid='"+userid+"' 	GROUP BY  tm ;";
		return Db.find(sql);

	}
	public void deleteWarningSetting(int dtype,String userid){

		 Db.delete("delete from  warning_setting where dtype='"+dtype+"' and userid='"+userid+"'");

	}

	public WarningSetting findById(int id) {
		return dao.findById(id);
	}


	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
