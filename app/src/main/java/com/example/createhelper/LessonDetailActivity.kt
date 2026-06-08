package com.example.createhelper

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonDetailActivity : AppCompatActivity() {

    private lateinit var tvLessonTitle: TextView
    private lateinit var tvStepContent: TextView
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var btnComplete: Button
    private lateinit var ivStepImage: ImageView
    private lateinit var progressBar: ProgressBar

    private var steps = listOf<StepEntity>()
    private var currentStep = 0
    private var lessonId = 1
    private var completed = false
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_detail)

        tvLessonTitle = findViewById(R.id.tvLessonTitle)
        tvStepContent = findViewById(R.id.tvStepContent)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnComplete = findViewById(R.id.btnComplete)
        ivStepImage = findViewById(R.id.ivStepImage)
        progressBar = findViewById(R.id.progressBar)

        lessonId = intent.getIntExtra("lessonId", 1)
        val lessonTitle = intent.getStringExtra("lessonTitle") ?: "Урок $lessonId"
        tvLessonTitle.text = lessonTitle

        db = AppDatabase.getDatabase(applicationContext)

        // Загружаем данные и затем включаем кнопки
        loadLessonAndSetupButtons()
    }

    private fun loadLessonAndSetupButtons() {
        CoroutineScope(Dispatchers.IO).launch {
            steps = db.stepDao().getStepsForLesson(lessonId)
            val progress = db.userProgressDao().getProgress(lessonId)
            val savedStep = progress?.currentStep ?: 0
            completed = progress?.completed == true

            withContext(Dispatchers.Main) {
                if (steps.isEmpty()) {
                    tvStepContent.text = "Шаги для этого урока ещё не добавлены."
                    btnPrev.isEnabled = false
                    btnNext.isEnabled = false
                    btnComplete.visibility = View.GONE
                    progressBar.progress = 0
                } else {
                    // Устанавливаем начальный шаг
                    currentStep = if (completed) steps.size - 1 else savedStep
                    showStep(currentStep)

                    // активация кнопок
                    setupButtonListeners()

                    if (completed) {
                        Toast.makeText(this@LessonDetailActivity, "Урок уже пройден", Toast.LENGTH_SHORT).show()
                        btnNext.isEnabled = false
                        btnComplete.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupButtonListeners() {
        btnPrev.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                showStep(currentStep)
                saveProgress()
            }
        }

        btnNext.setOnClickListener {
            if (currentStep < steps.size - 1) {
                currentStep++
                showStep(currentStep)
                saveProgress()
            }
        }

        btnComplete.setOnClickListener {
            completed = true
            CoroutineScope(Dispatchers.IO).launch {
                db.userProgressDao().saveProgress(
                    UserProgressEntity(lessonId, currentStep, true)
                )
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LessonDetailActivity, "🎉 Урок пройден!", Toast.LENGTH_SHORT).show()
                    btnComplete.visibility = View.GONE
                    btnNext.isEnabled = false
                }
            }
        }
    }

    private fun showStep(position: Int) {
        val step = steps[position]
        tvStepContent.text = step.content

        // Изображение
        if (!step.imageRes.isNullOrEmpty()) {
            val resId = resources.getIdentifier(step.imageRes, "drawable", packageName)
            if (resId != 0) {
                ivStepImage.setImageResource(resId)
                ivStepImage.visibility = View.VISIBLE
            } else {
                ivStepImage.visibility = View.GONE
            }
        } else {
            ivStepImage.visibility = View.GONE
        }

        // Прогресс-бар
        val progress = ((position + 1).toFloat() / steps.size.toFloat() * 100).toInt()
        progressBar.progress = progress

        // Состояние кнопок
        btnPrev.isEnabled = position > 0
        val isLast = (position == steps.size - 1)
        btnNext.isEnabled = !isLast
        btnComplete.visibility = if (isLast && !completed) View.VISIBLE else View.GONE
    }

    private fun saveProgress() {
        if (completed) return
        CoroutineScope(Dispatchers.IO).launch {
            db.userProgressDao().saveProgress(
                UserProgressEntity(lessonId, currentStep, false)
            )
        }
    }
}