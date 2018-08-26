package com.yaya.merchant.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 魏新智 on 2015/3/18.
 */
@DatabaseTable(tableName = "sys_region")
public class Region implements Serializable {

    public static final int HAS_PARENT = 0;
    public static final int NO_PARENT = 1;

    public static final int HAS_CHILD = 0;
    public static final int NO_CHILD = 1;

    public static final int COLLAPSED = 0;//收缩
    public static final int EXPANDED = 1;//展开

    public static final int PROVINCE_LEVEL = 1;
    public static final int CITY_LEVEL = 2;
    public static final int DISTRICT_LEVEL = 3;

    public static final int OPENED = 0;//已开放
    public static final int CLOSED = 1;//未开放

    public static final String HOLE_CITY = "全市";

//    @DatabaseField(id = true)
    @DatabaseField(columnName = "region_id")
    private int id;//当前节点的id
    @DatabaseField(columnName = "region_name")
    private String name ;//节点上面显示的信息，省份、城市、县区的名称
    @DatabaseField(columnName = "parent_id")
    private int parentId; //父节点的id
    @DatabaseField(columnName = "region_type")
    private int regionType; //节点类型
    @DatabaseField(columnName = "agency_id")
    private int agencyId; //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRegionType() {
        return regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public Region(){

    }

    public Region(int id, String name, int parentId, int regionType, int agencyId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.regionType = regionType;
        this.agencyId = agencyId;
    }

    @Override
    public String toString() {
        return "id:"+ id + ", name:"+name;
    }
}
