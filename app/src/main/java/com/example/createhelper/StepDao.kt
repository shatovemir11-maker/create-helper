package com.example.createhelper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StepDao {
    @Query("SELECT * FROM steps WHERE lessonId = :lessonId ORDER BY stepNumber")
    fun getStepsForLesson(lessonId: Int): List<StepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(steps: List<StepEntity>)
}