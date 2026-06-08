package com.example.createhelper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonsActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        db = AppDatabase.getDatabase(applicationContext)

        val rvLessons: RecyclerView = findViewById(R.id.rvLessons)
        rvLessons.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val lessons = db.lessonDao().getAllLessons()
            val progressList = db.userProgressDao().getAllProgress()
            val completedIds = progressList.filter { it.completed }.map { it.lessonId }.toSet()

            withContext(Dispatchers.Main) {
                val adapter = LessonAdapter(
                    lessons.map { Lesson(it.id, it.title) },
                    completedIds
                ) { lesson ->
                    val intent = Intent(this@LessonsActivity, LessonDetailActivity::class.java)
                    intent.putExtra("lessonId", lesson.id)
                    intent.putExtra("lessonTitle", lesson.title)
                    startActivity(intent)
                }
                rvLessons.adapter = adapter
            }
        }
    }
}