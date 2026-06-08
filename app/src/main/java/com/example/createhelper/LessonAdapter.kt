package com.example.createhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LessonAdapter(
    private val lessons: List<Lesson>,
    private val completedLessonIds: Set<Int>,         // <-- ID завершённых уроков
    private val onLessonClick: (Lesson) -> Unit
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private val iconMap = mapOf(
        1 to R.drawable.ic_lesson_intro,
        2 to R.drawable.ic_lesson_gear,
        3 to R.drawable.ic_lesson_ratio,
        4 to R.drawable.ic_lesson_stress,
        5 to R.drawable.ic_lesson_conveyor,
        6 to R.drawable.ic_lesson_logic,
        7 to R.drawable.ic_lesson_assembly
    )

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivLessonIcon)
        val tvTitle: TextView = itemView.findViewById(R.id.tvLessonTitle)
        val ivCompleted: ImageView = itemView.findViewById(R.id.ivCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.ivIcon.setImageResource(iconMap[lesson.id] ?: R.drawable.ic_lesson_default)
        holder.tvTitle.text = lesson.title

        // Показываем зелёную галочку, если урок завершён
        holder.ivCompleted.visibility =
            if (lesson.id in completedLessonIds) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onLessonClick(lesson)
        }
    }

    override fun getItemCount(): Int = lessons.size
}