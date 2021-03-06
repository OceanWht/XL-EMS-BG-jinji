package com.ems.common.model;

import com.ems.unit.Devices;
import com.ems.unit.Unitlink;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("eer_base", "id", EerBase.class);
		arp.addMapping("eer_coal", "id", EerCoal.class);
		arp.addMapping("eer_unit", "id", EerUnit.class);
		arp.addMapping("fee_item", "Id", FeeItem.class);
		arp.addMapping("userinfo", "Id", Userinfo.class);
		arp.addMapping("usersetting", "id", Usersetting.class);
		arp.addMapping("usertoken", "Id", Usertoken.class);
		arp.addMapping("warning_info", "id", WarningInfo.class);
		arp.addMapping("warning_setting", "id", WarningSetting.class);
		arp.addMapping("xl_electric", "id", XlElectric.class);
		arp.addMapping("xl_analogdoc", "id", XlAnalogdoc.class);
		arp.addMapping("xl_datacode", "id", XlDatacode.class);
		arp.addMapping("xl_daydata", "id", XlDaydata.class);
		arp.addMapping("xl_daydata_e", "id", XlDaydataE.class);
		arp.addMapping("xl_devices", "id", XlDevices.class);
		arp.addMapping("xl_devices_current", "id", XlDevicesCurrent.class);
		arp.addMapping("xl_devices_e", "id", XlDevicesE.class);
		arp.addMapping("xl_groupanalog", "id", XlGroupanalog.class);
		arp.addMapping("xl_monthdata", "id", XlMonthdata.class);
		arp.addMapping("xl_unitcalcgroup", "id", XlUnitcalcgroup.class);
		arp.addMapping("xl_unitlink", "id", XlUnitlink.class);
		arp.addMapping("xl_unitlink_e", "id", XlUnitlinkE.class);
		arp.addMapping("xl_unitlink", "id", Unitlink.class);
		arp.addMapping("v_ud", "id", Devices.class);
		arp.addMapping("xl_account", "id", XlAccount.class);
		arp.addMapping("eer_userinfo_temp", "id", EerUserinfoTemp.class);
		arp.addMapping("ems_company", "id", EmsCompany.class);
	}
}

