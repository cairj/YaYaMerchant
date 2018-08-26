package com.yaya.merchant.util.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.yaya.merchant.data.Region;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 魏新智 on 2015/12/28.
 */
public class RegionDao {

    private Context context;
    private Dao<Region, Integer> daoOpe;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public RegionDao(Context context){
        this.context = context;
        try {
            databaseHelper = DatabaseHelper.getHelper(context);
            daoOpe = databaseHelper.getDao(Region.class);
            db = databaseHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Region region){
        try{
            daoOpe.create(region);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据等级搜索地区
     * @param level
     * @return
     */
    public List<Region> query(int level){
        try {
            return daoOpe.queryBuilder().where().eq("region_type", level).query();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜搜已开放地区
     * @param level
     * @return
     */
    public List<Region> queryOpenedRegion(int level){
        try {
            return daoOpe.queryBuilder().where().eq("level", level).and().eq("isOpen", Region.OPENED).query();
//            return daoOpe.queryBuilder().where().eq("id", 1252).query();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 搜索已开放的子地区
     * @param id
     * @return
     */
    public List<Region> queryOpenedChildren(int id){
        try {
            return daoOpe.queryBuilder().where().eq("parentId", id).and().eq("isOpen", Region.OPENED).query();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /** 根据id找到地区 */
    public Region getRegion(int id){
        try {
            return daoOpe.queryBuilder().where().eq("id", id).query().get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 根据id寻找该地区的所有子地区
     * @param id
     * @return
     */
    public List<Region> queryChildren(int id){
        try {
            return daoOpe.queryBuilder().where().eq("parent_id", id).query();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Region> queryAll(){
        try {
            return daoOpe.queryForAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新地区数据
     * @param region
     */
    public void update(Region region){
        try{
            daoOpe.update(region);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据id，修改地区为已开放
     * @param id
     */
    public void updateRegionOpenedStatus(int id){
//        LogHelper.d("updateRegionOpenedStatus, id:"+id);
        try {
            daoOpe.updateRaw("update tb_region set isOpen=0 where id="+id);
            //daoOpe.updateBuilder().updateColumnValue("isOpen", 0).where().eq("id", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*ContentValues values = new ContentValues();
        values.put("isOpen", 0);
        db.update("tb_region", values, "id = ?", new String[]{String.valueOf(id)});*/
    }
}
