package com.example.createhelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Заполнение базы данных
        val db = AppDatabase.getDatabase(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            if (db.lessonDao().getAllLessons().isEmpty()) {
                DatabaseSeeder.seed(db)
            }
        }

        // Кнопка
        val btnStart: Button = findViewById(R.id.btnStartLearning)
        btnStart.setOnClickListener {
            val intent = Intent(this, LessonsActivity::class.java)
            startActivity(intent)
        }
    }
}