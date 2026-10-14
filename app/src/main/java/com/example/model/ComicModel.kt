package com.example.model

enum class BubbleType {
    SPEECH_LEFT,
    SPEECH_RIGHT,
    THOUGHT,
    NARRATOR,
    SOUND_EFFECT,
    TEACHER_ADVICE
}

data class ComicChoice(
    val title: String,
    val description: String,
    val consequenceType: ConsequenceType,
    val storyResult: String,
    val teacherGuidance: String
)

enum class ConsequenceType {
    POSITIVE,
    NEGATIVE
}

data class ComicPanel(
    val speaker: String,
    val roleBadge: String = "",
    val text: String,
    val bubbleType: BubbleType = BubbleType.SPEECH_LEFT,
    val soundEffect: String? = null,
    val isDecisionPoint: Boolean = false,
    val choiceA: ComicChoice? = null,
    val choiceB: ComicChoice? = null
)

data class ComicChapter(
    val id: Int,
    val title: String,
    val subtitle: String,
    val themeTag: String,
    val summary: String,
    val coverResId: Int? = null,
    val panels: List<ComicPanel>
)
