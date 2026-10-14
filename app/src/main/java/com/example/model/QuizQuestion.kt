package com.example.model

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val category: String,
    val isCustom: Boolean = false
) {
    val correctOptionText: String
        get() = if (correctIndex in options.indices) options[correctIndex] else ""

    val correctOptionLetter: String
        get() = when (correctIndex) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> "-"
        }
}
