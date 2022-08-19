package com.ucielcorp.chatfirenase.Views

import android.app.Application
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider

class EmojiApplication : Application() {
    @Override
    override fun onCreate() {
        super.onCreate()
        //Install emoji manager
        EmojiManager.install(GoogleEmojiProvider())
    }
}