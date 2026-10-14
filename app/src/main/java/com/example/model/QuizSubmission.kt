package com.example.model

data class QuizSubmission(
    val id: String,
    val studentName: String,
    val studentClass: String,
    val score: Int,
    val correctCount: Int,
    val totalQuestions: Int,
    val timeSpentSeconds: Long,
    val completedAtFormatted: String,
    val timestampMillis: Long = System.currentTimeMillis()
) {
    val durationFormatted: String
        get() {
            val minutes = timeSpentSeconds / 60
            val seconds = timeSpentSeconds % 60
            return "%02d:%02d".format(minutes, seconds)
        }
}
