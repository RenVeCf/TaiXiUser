package com.ipd.taixiuser.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils
import com.ipd.taixiuser.platform.http.HttpUrl
import java.io.File

object PictureUtils {
    fun savePhotoAndRefreshGallery(context: Context, url: String, callback: (code: Int) -> Unit) {
        ThreadUtils.runOnThread {
            try {
                val saveFile = Glide.with(context).load(HttpUrl.IMAGE_URL + url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get()
                ThreadUtils.runOnUIThread {
                    MediaStore.Images.Media.insertImage(context.contentResolver, saveFile.absolutePath, saveFile.name, null)
                    // 最后通知图库更新
                    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + saveFile.absolutePath))
                    context.sendBroadcast(intent)

                    callback.invoke(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ThreadUtils.runOnUIThread {
                    callback.invoke(1)
                }
            }
        }
    }

    fun savePhotos(context: Context, urls: List<String>, callback: (code: Int, uris: List<Uri>?) -> Unit) {
        ThreadUtils.runOnThread {
            try {
                val fileList = ArrayList<Uri>()
                urls.forEach {
                    val saveFile = Glide.with(context).load(HttpUrl.IMAGE_URL + it)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get()

                    val targetFile = File("${CommonUtils.getPhotoSavePath(context)}/${saveFile.name}.png")
                    if (!targetFile.exists()) {
                        saveFile.copyTo(targetFile)
                    }
                    fileList.add(Uri.fromFile(targetFile))
                }

                ThreadUtils.runOnUIThread {
                    callback.invoke(0, fileList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ThreadUtils.runOnUIThread {
                    callback.invoke(1, null)
                }
            }
        }
    }
}
