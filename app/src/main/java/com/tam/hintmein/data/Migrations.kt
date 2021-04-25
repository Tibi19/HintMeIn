package com.tam.hintmein.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tam.hintmein.utils.Constants

object Migrations {

    // Migration from a 4 columns table (id, domain, username, text) to a 5 columns table (+ position)
    // The positions will be initialized incrementally during migration
    val MIGRATION_1_3 = object : Migration(1, 3) {

        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE hints_table ADD COLUMN position INTEGER NOT NULL DEFAULT 0")
            updatePositions(
                database,
                getIds(database)
            )
        }

        private fun getIds(database: SupportSQLiteDatabase): MutableList<Int> {
            val cursor = database.query("SELECT * FROM hints_table")
            val ids = mutableListOf<Int>()
            if (cursor.moveToFirst()) {
                do {
                    ids.add(cursor.getInt(0))
                } while (cursor.moveToNext())
            }
            return ids
        }

        // Update the position of each element incrementally, for each id
        private fun updatePositions(database: SupportSQLiteDatabase, ids: MutableList<Int>) {
            var count = 0
            ids.forEach {
                val values = ContentValues()
                values.put(Constants.HINT_POSITION, count++)
                database.update(
                    Constants.NAME_HINTS_TABLE,
                    SQLiteDatabase.CONFLICT_ABORT,
                    values,
                    Constants.HINT_ID + "=?",
                    arrayOf(it)
                )
            }
        }

    }

}