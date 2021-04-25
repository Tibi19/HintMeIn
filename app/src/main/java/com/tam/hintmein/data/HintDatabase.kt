package com.tam.hintmein.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tam.hintmein.utils.Constants

@Database(
    entities = [Hint::class],
    version = Constants.VERSION_HINTS_TABLE,
    exportSchema = false
)
abstract class HintDatabase : RoomDatabase() {

    abstract val hintDao: HintDao

    companion object {

        @Volatile
        private var INSTANCE: HintDatabase? = null

        fun getInstance(context: Context): HintDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HintDatabase::class.java,
                    Constants.HINT_DATABASE
                ).addMigrations(Migrations.MIGRATION_1_3)
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }

    }

}