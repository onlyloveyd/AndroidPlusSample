package tech.kicky

import android.app.Application
import androidx.room.Room
import tech.kicky.coroutine.db.AppDatabase

/**
 * Main App
 * author: yidong
 * 2021/1/13
 */
class App : Application() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "coroutine"
        ).build()
    }
}