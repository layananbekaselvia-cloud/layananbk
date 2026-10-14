package com.example.model

data class StudentProfile(
    val name: String = "",
    val studentClass: String = "9A"
) {
    val isValid: Boolean get() = name.trim().length >= 2
}
