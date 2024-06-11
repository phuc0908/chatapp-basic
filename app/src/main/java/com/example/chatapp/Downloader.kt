package com.example.chatapp

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import java.util.UUID

class Downloader (
    private val context: Context
){
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    fun downloadFile(url : String): Long{
        val fileName = "${UUID.randomUUID()}.png"
       val request = DownloadManager.Request(url.toUri())
           .setMimeType("image/png")
           .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
           .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
           .setTitle("Picture Download")
           .addRequestHeader("Auth","")
           .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        return downloadManager.enqueue(request)
    }

    fun downloadVideo(url : String): Long {
        val fileName = "${UUID.randomUUID()}.mp4"
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("video/mp4")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Video Download")
            .addRequestHeader("Auth","")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        return downloadManager.enqueue(request)
    }
}