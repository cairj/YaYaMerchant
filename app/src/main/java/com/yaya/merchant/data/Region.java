package com.yaya.merchant.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 魏新智 on 2015/3/18.
 */
@DatabaseTable(tableName = "tb_region")
public class Region implements Serializable {

    public static final int HAS_PARENT = 0;
    public static final int NO_PARENT = 1;

    public static final int HAS_CHILD = 0;
    public static final int NO_CHILD = 1;

    public static final int COLLAPSED = 0;//收缩
    public static final int EXPANDED = 1;//展开

    public static final int PROVINCE_LEVEL = 0;
    public static final int CITY_LEVEL = 1;
    public static final int DISTRICT_LEVEL = 2;

    public static final int OPENED = 0;//已开放
    public static final int CLOSED = 1;//未开放

    public static final String HOLE_CITY = "全市";

//    @DatabaseField(id = true)
    @DatabaseField(columnName = "id")
    private int id;//当前节点的id
    @DatabaseField(columnName = "name")
    private String name ;//节点上面显示的信息，省份、城市、县区的名称
    @DatabaseField(columnName = "hasParent")
    private int hasParent; //是否有父节点，0代表true，1代表false
    @DatabaseField(columnName = "hasChild")
    private int hasChild ; //是否有子节点，0代表true，1代表false
    @DatabaseField(columnName = "parentId")
    private int parentId; //父节点的id
    @DatabaseField(columnName = "level")
    private int level;//当前节点所在的层次
    @DatabaseField(columnName = "isOpen")
    private int isOpen;//是否开放 0代表true开放，1代表false未开放

    private int folderStatus;//当前是否展开，默认为0代表收缩，1代表展开

    private String parentName;//父节点名称

    private int bannerId;//在数据库的存储字段为“woeid”,以前是用于查询雅虎天气的城市代码，修改为首页的bannerId

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

    public int getHasParent(){
        return hasParent;
    }

    public void setHasParent(int hasParent) {
        this.hasParent = hasParent;
    }

    public int getHasChild(){
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFolderStatus() {
        return folderStatus;
    }

    public void setFolderStatus(int folderStatus) {
        this.folderStatus = folderStatus;
    }

    public int getBannerId(){
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public Region(){

    }

    public Region(int id, String name, int hasParent, int hasChild,
                  int parentId, int level, int folderStatus) {
        super();
        this.id = id;
        this.name = name;
        this.hasParent = hasParent;
        this.hasChild = hasChild;
        this.parentId = parentId;
        this.level = level;
        this.folderStatus = folderStatus;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "id:"+ id + ", name:"+name +"folderStatus"+ folderStatus;
    }
}
