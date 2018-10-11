package com.ipd.taixiuser.db

import android.database.sqlite.SQLiteDatabase
import com.ipd.taixiuser.ui.SplashActivity
import java.io.File

/**
 * Created by jumpbox on 2017/9/1.
 */
class CityOpenHelper {
    private val db: SQLiteDatabase by lazy {
        val cityDBPath = File(SplashActivity.DB_PATH)
        SQLiteDatabase.openOrCreateDatabase(cityDBPath, null)
    }

    fun openSQLite(): SQLiteDatabase {
        return db
    }
}