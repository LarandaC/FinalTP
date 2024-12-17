package com.example.wallpics.utils

interface Downloader {
    fun downloadFile(url:String, title: String?): Long
}