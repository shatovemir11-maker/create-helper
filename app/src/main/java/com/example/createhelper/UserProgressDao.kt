package com.example.createhelper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE lessonId = :lessonId")
    suspend fun getProgress(lessonId: Int): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: UserProgressEntity)

    @Query("SELECT * FROM user_progress")
    suspend fun getAllProgress(): List<UserProgressEntity>
}