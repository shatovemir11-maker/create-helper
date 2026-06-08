package com.example.createhelper

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {

    fun seed(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val lessonDao = db.lessonDao()
            val stepDao = db.stepDao()

            // Уроки
            lessonDao.insertAll(
                listOf(
                    LessonEntity(1, "Введение в механику Create"),
                    LessonEntity(2, "Шестерни и вращение"),
                    LessonEntity(3, "Передаточные числа"),
                    LessonEntity(4, "Стресс-единицы (Stress Units)"),
                    LessonEntity(5, "Конвейеры и транспорт"),
                    LessonEntity(6, "Логические элементы и редстоун"),
                    LessonEntity(7, "Сборка первого механизма")
                )
            )

            // Шаги (часть с картинками)
            stepDao.insertAll(
                listOf(
                    StepEntity(lessonId = 1, stepNumber = 1, content = "Добро пожаловать в мир механизмов Create! Сегодня вы узнаете, с чего начать."),
                    StepEntity(lessonId = 1, stepNumber = 2, content = "Установите мод Create и запустите Minecraft. Откройте творческий режим для экспериментов."),
                    StepEntity(lessonId = 1, stepNumber = 3, content = "Найдите в инвентаре вкладку Create и изучите базовые блоки: вал, шестерня, подшипник."),

                    StepEntity(lessonId = 2, stepNumber = 1, content = "Шестерни — основа любой механики. Познакомьтесь с прямой и угловой передачей."),
                    StepEntity(lessonId = 2, stepNumber = 2, content = "Поставьте вал, присоедините к нему водяное колесо и посмотрите, как возникает вращение.",
                        imageRes = "ic_step_gear"),
                    StepEntity(lessonId = 2, stepNumber = 3, content = "Попробуйте соединить две шестерни под углом 90 градусов и убедитесь, что вращение передаётся."),

                    StepEntity(lessonId = 3, stepNumber = 1, content = "Передаточное число определяет скорость вращения. Большая шестерня крутится медленнее, но даёт больше крутящего момента."),
                    StepEntity(lessonId = 3, stepNumber = 2, content = "Создайте редуктор: маленькая шестерня ведущая, большая — ведомая. Измерьте скорость обеих."),
                    StepEntity(lessonId = 3, stepNumber = 3, content = "Эксперимент: попробуйте мультипликатор (большая ведущая, маленькая ведомая). Что происходит со скоростью?"),

                    StepEntity(lessonId = 4, stepNumber = 1, content = "Каждый механизм потребляет стресс-единицы. Если нагрузка превышает мощность источника — всё останавливается."),
                    StepEntity(lessonId = 4, stepNumber = 2, content = "Подсоедините к одному водяному колесу несколько механизмов и наблюдайте за показателем стресса.",
                        imageRes = "ic_step_stress"),
                    StepEntity(lessonId = 4, stepNumber = 3, content = "Научитесь добавлять дополнительные источники вращения, чтобы компенсировать нагрузку."),

                    StepEntity(lessonId = 5, stepNumber = 1, content = "Конвейеры позволяют перемещать предметы. Разместите ленту и подайте на неё ресурсы.",
                        imageRes = "ic_step_conveyor"),
                    StepEntity(lessonId = 5, stepNumber = 2, content = "Используйте воронки и механические руки для автоматической сортировки предметов."),

                    StepEntity(lessonId = 6, stepNumber = 1, content = "Логические элементы Create позволяют управлять механизмами по сигналу редстоуна."),
                    StepEntity(lessonId = 6, stepNumber = 2, content = "Постройте простую схему: кнопка → редстоун → муфта, чтобы включать и выключать вал."),

                    StepEntity(lessonId = 7, stepNumber = 1, content = "Соберите всё вместе: механическую дверь, открывающуюся по нажатию кнопки."),
                    StepEntity(lessonId = 7, stepNumber = 2, content = "Проверьте стресс-единицы, добавьте конвейер для подачи ресурсов и логическую схему управления.")
                )
            )
        }
    }
}