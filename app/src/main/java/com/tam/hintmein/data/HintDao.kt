package com.tam.hintmein.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HintDao {

    @Insert
    suspend fun insert(hint: Hint)

    @Update
    suspend fun update(hint: Hint)

    @Delete
    suspend fun delete(hint:Hint)

    @Query("SELECT * FROM hints_table ORDER BY position")
    fun getHints(): LiveData<MutableList<Hint>>
}