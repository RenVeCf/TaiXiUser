package com.ipd.taixiuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taixiuser.MainActivity
import com.ipd.taixiuser.R
import com.ipd.taixiuser.platform.global.GlobalApplication
import com.ipd.taixiuser.platform.http.RxScheduler
import rx.Observable
import rx.Subscriber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Created by jumpbox on 2018/5/9.
 */
class SplashActivity : BaseActivity() {

    companion object {
        val DB_NAME = "location.db"
        val DB_PATH by lazy {
            val databasePath = GlobalApplication.mContext.getDatabasePath("address").absolutePath + "/"
            if (!File(databasePath).exists()) {
                File(databasePath).mkdirs()
            }
            databasePath + DB_NAME
        }
    }

    override fun getBaseLayout(): Int = R.layout.activity_splash

    override fun initView(bundle: Bundle?) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    override fun loadData() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .map<Long> {
                    copyDB2DatabasesDir()
                    it
                }
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Subscriber<Long>() {
                    override fun onError(e: Throwable?) {
                    }

                    override fun onNext(t: Long?) {
                    }

                    override fun onCompleted() {
                        MainActivity.launch(mActivity)
                        finish()
                    }

                })
    }

    override fun initListener() {
    }

    private fun copyDB2DatabasesDir() {
        if (File(DB_PATH).exists()) {
            return
        }

        var inputStream: InputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            inputStream = resources.assets.open(DB_NAME)
            fileOutputStream = FileOutputStream(DB_PATH)

            inputStream.copyTo(fileOutputStream, 1024)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}