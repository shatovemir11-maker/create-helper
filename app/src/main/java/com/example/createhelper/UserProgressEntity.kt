package com.example.createhelper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val lessonId: Int,       // ID урока
    val currentStep: Int = 0,            // на каком шаге остановились
    val completed: Boolean = false       // пройден ли урок полностью
)