package com.tam.hintmein.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tam.hintmein.utils.Constants

@Entity(tableName = Constants.NAME_HINTS_TABLE)
data class Hint(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = Constants.HINT_DOMAIN)
    var domain: String,
    @ColumnInfo(name = Constants.HINT_USERNAME)
    var username: String,
    @ColumnInfo(name = Constants.HINT_TEXT)
    var text: String,
    @ColumnInfo(name = Constants.HINT_POSITION)
    var position: Int
)