package org.kitchen.pos.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun get(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        // 🔐 TODO: later plug SQLCipher SupportFactory for full encryption.
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pos_main.db"
        )
            .fallbackToDestructiveMigration() // adjust when you add migrations
            .build()
    }
}
