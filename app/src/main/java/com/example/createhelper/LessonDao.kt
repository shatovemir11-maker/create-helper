package com.example.createhelper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY id")
    fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM lessons WHERE id = :id")
    fun getLessonById(id: Int): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(lessons: List<LessonEntity>)
}