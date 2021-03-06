package com.ems.unit;

import com.alibaba.fastjson.annotation.JSONField;
import com.ems.common.model.base.BaseXlUnitlink;

import java.util.*;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Unitlink extends BaseXlUnitlink<Unitlink> {

    UnitService service = new UnitService();
    @JSONField(ordinal = 100)
    public List<Unitlink> children;
    @JSONField(ordinal = 100)
    public List<Devices> devices;

    public void setChilds() {
        Integer io = getIo();
        if (io == null) {
            io = -1;
        }
        children = service.getUnitlink(getUid(), io, getDataid(), getUserid());
        //去重
        if (children != null && children.size() != 0) {
           for (int i = 0; i < children.size(); i++)
           {
               for (int j = children.size()-1;j > i ;j--)
               {
                   if (children.get(i).getName().equals(children.get(j).getName()))
                   {
                       children.remove(j);
                   }
               }
           }
        }

        devices = service.getChildevices(getUid(), getDataid(), getUserid());
        Set<Devices> devicesSet = new HashSet<>(devices);
        if (devicesSet.size() != 0) {
            devices = new ArrayList<>(devicesSet);
        }
        for (Devices device : devices) {
            device.setChilds();
        }
    }

}
