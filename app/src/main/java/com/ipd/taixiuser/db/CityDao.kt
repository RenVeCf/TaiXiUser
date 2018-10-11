package com.ipd.taixiuser.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ipd.taixiuser.bean.CityBean
import java.util.*

/**
 * Created by jumpbox on 2017/9/1.
 */
class CityDao {
    val db: SQLiteDatabase by lazy { CityOpenHelper().openSQLite() }
    val TABLE_NAME = "location"

    fun getCityByParentId(parentId: String): List<CityBean> {
        if (db == null) {
            return ArrayList()
        }
        val cursor = db.rawQuery("select * from $TABLE_NAME where pid = ?", arrayOf(parentId))
        val list = ArrayList<CityBean>()
        while (cursor.moveToNext()) {
            val cityBean = CityBean(
                    cursor.getInt(cursor.getColumnIndex("id"))
                    , cursor.getString(cursor.getColumnIndex("title"))
                    , cursor.getInt(cursor.getColumnIndex("pid"))
                    , cursor.getInt(cursor.getColumnIndex("hot"))
                    , cursor.getString(cursor.getColumnIndex("key")))
            list.add(cityBean)
        }
        return list
    }

    fun getAllProvince(): List<CityBean> {
        if (db == null) {
            return ArrayList()
        }
        val cursor = db.rawQuery("select * from $TABLE_NAME where pid = 0", null)
        val list = ArrayList<CityBean>()
        while (cursor.moveToNext()) {
            val cityBean = CityBean(
                    cursor.getInt(cursor.getColumnIndex("id"))
                    , cursor.getString(cursor.getColumnIndex("title"))
                    , cursor.getInt(cursor.getColumnIndex("pid"))
                    , cursor.getInt(cursor.getColumnIndex("hot"))
                    , cursor.getString(cursor.getColumnIndex("key")))

            list.add(cityBean)
        }
        return list
    }

    fun getCityById(id: String): CityBean? {
        if (db == null) {
            return null
        }
        val cursor = db.rawQuery("select * from $TABLE_NAME where id = ?", arrayOf(id))
        if (cursor.moveToFirst()) {
            return getCityBean(cursor)
        }
        return null
    }


    fun getDetailCityByAreaId(areaId: String): String {
        val areaInfo = getCityById(areaId) ?: return ""
        val cityInfo = getCityById(areaInfo.pid.toString()) ?: return ""
        val provinceInfo = getCityById(cityInfo.pid.toString()) ?: return ""
        return provinceInfo.title + " " + cityInfo.title + " " + areaInfo.title
    }

    private fun getCityBean(cursor: Cursor): CityBean {
        return CityBean(
                cursor.getInt(cursor.getColumnIndex("id"))
                , cursor.getString(cursor.getColumnIndex("title"))
                , cursor.getInt(cursor.getColumnIndex("pid"))
                , cursor.getInt(cursor.getColumnIndex("hot"))
                , cursor.getString(cursor.getColumnIndex("key")))
    }

}