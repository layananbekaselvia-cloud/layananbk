package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BubbleType
import com.example.model.ComicChoice
import com.example.model.ComicPanel
import com.example.model.ConsequenceType
import com.example.ui.theme.ComicBadgeGold
import com.example.ui.theme.ComicBadgeGreen
import com.example.ui.theme.ComicBadgeRed
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelPeachRose
import com.example.ui.theme.PastelRoseContainer
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun ComicPanelItem(
    panel: ComicPanel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Sound effect sticker badge if present
        if (!panel.soundEffect.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .align(if (panel.bubbleType == BubbleType.SPEECH_RIGHT) Alignment.End else Alignment.Start)
            ) {
                ComicSoundEffectBadge(panel.soundEffect)
            }
        }

        when (panel.bubbleType) {
            BubbleType.NARRATOR -> {
                NarratorBubble(text = panel.text, roleBadge = panel.roleBadge)
            }
            BubbleType.TEACHER_ADVICE -> {
                TeacherAdviceBubble(panel = panel)
            }
            BubbleType.THOUGHT -> {
                ThoughtBubble(panel = panel)
            }
            BubbleType.SPEECH_RIGHT -> {
                SpeechBubble(panel = panel, isRight = true)
            }
            BubbleType.SPEECH_LEFT, BubbleType.SOUND_EFFECT -> {
                SpeechBubble(panel = panel, isRight = false)
            }
        }

        // Branching consequence interactive choices
        if (panel.isDecisionPoint && panel.choiceA != null && panel.choiceB != null) {
            Spacer(modifier = Modifier.height(8.dp))
            ComicDecisionInteractiveCard(choiceA = panel.choiceA, choiceB = panel.choiceB)
        }
    }
}

@Composable
fun ComicSoundEffectBadge(text: String) {
    Surface(
        color = ComicBadgeGold,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 3.dp,
        modifier = Modifier
            .rotate(-3f)
            .border(1.5.dp, Color(0xFF5D4037), RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            color = Color(0xFF3E2723),
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun NarratorBubble(text: String, roleBadge: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFFFFF9E6), Color(0xFFFFF3CD))
                ),
                RoundedCornerShape(12.dp)
            )
            .border(1.5.dp, Color(0xFFD7CCC8), RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column {
            if (roleBadge.isNotBlank()) {
                Text(
                    text = "✦ $roleBadge ✦",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VintageBrassPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                text = text,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF3E2723)
            )
        }
    }
}

@Composable
fun SpeechBubble(panel: ComicPanel, isRight: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = if (isRight) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isRight) {
            CharacterAvatar(name = panel.speaker, isTeacher = false)
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.weight(1f, fill = false),
            horizontalAlignment = if (isRight) Alignment.End else Alignment.Start
        ) {
            // Speaker header badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = panel.speaker,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = if (isRight) VintageDenimBlue else PastelMintPrimary
                )
                if (panel.roleBadge.isNotBlank()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "• ${panel.roleBadge}",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }

            // Bubble container with comic border
            Box(
                modifier = Modifier
                    .shadow(3.dp, RoundedCornerShape(16.dp))
                    .background(
                        if (isRight) Color(0xFFF1F8FF) else Color(0xFFFFFFFF),
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isRight) 16.dp else 2.dp,
                            bottomEnd = if (isRight) 2.dp else 16.dp
                        )
                    )
                    .border(
                        2.dp,
                        if (isRight) Color(0xFF90CAF9) else Color(0xFF37474F),
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isRight) 16.dp else 2.dp,
                            bottomEnd = if (isRight) 2.dp else 16.dp
                        )
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = panel.text,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF212121),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        if (isRight) {
            Spacer(modifier = Modifier.width(8.dp))
            CharacterAvatar(name = panel.speaker, isTeacher = false)
        }
    }
}

@Composable
fun ThoughtBubble(panel: ComicPanel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {
            Box(
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFFDE7), RoundedCornerShape(20.dp))
                    .border(1.5.dp, Color(0xFFB0BEC5), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Batin",
                        tint = VintageBrassPrimary,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${panel.speaker} (Dalam Hati)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = VintageBrassPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"${panel.text}\"",
                            fontSize = 13.5.sp,
                            lineHeight = 19.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF37474F)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherAdviceBubble(panel: ComicPanel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = PastelMintContainer),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CharacterAvatar(name = "Bu Selvi", isTeacher = true)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Bu Selvi, S.Pd",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = PastelMintPrimary
                    )
                    Text(
                        text = "Guru Bimbingan dan Konseling (BK)",
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = PastelMintPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFA5D6A7), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = panel.text,
                    fontSize = 13.5.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1B5E20)
                )
            }
        }
    }
}

@Composable
fun CharacterAvatar(name: String, isTeacher: Boolean) {
    val bgColor = when {
        isTeacher -> PastelMintPrimary
        name.contains("Laras", ignoreCase = true) || name.contains("Nisa", ignoreCase = true) -> PastelPeachRose
        else -> VintageDenimBlue
    }
    val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")

    Box(
        modifier = Modifier
            .size(38.dp)
            .shadow(2.dp, CircleShape)
            .background(bgColor, CircleShape)
            .border(1.5.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
fun ComicDecisionInteractiveCard(
    choiceA: ComicChoice,
    choiceB: ComicChoice
) {
    var selectedChoice by remember { mutableStateOf<ComicChoice?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .border(2.dp, VintageBrassPrimary, RoundedCornerShape(16.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = null,
                    tint = VintageBrassPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "PILIHAN BERCABANG (KONSEKUENSI)",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = VintageBrassPrimary,
                    letterSpacing = 0.5.sp
                )
            }
            Text(
                text = "Pilih salah satu jalan keputusan untuk melihat alur konsekuensi nyata:",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Option A Button
            ChoiceButton(
                choice = choiceA,
                isSelected = selectedChoice == choiceA,
                isPositive = true,
                onClick = { selectedChoice = choiceA }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Option B Button
            ChoiceButton(
                choice = choiceB,
                isSelected = selectedChoice == choiceB,
                isPositive = false,
                onClick = { selectedChoice = choiceB }
            )

            // Result preview
            AnimatedVisibility(
                visible = selectedChoice != null,
                enter = fadeIn() + expandVertically()
            ) {
                selectedChoice?.let { choice ->
                    Column(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .background(
                                if (choice.consequenceType == ConsequenceType.POSITIVE) PastelMintContainer else PastelRoseContainer,
                                RoundedCornerShape(12.dp)
                            )
                            .border(
                                1.5.dp,
                                if (choice.consequenceType == ConsequenceType.POSITIVE) ComicBadgeGreen else ComicBadgeRed,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (choice.consequenceType == ConsequenceType.POSITIVE) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                                contentDescription = null,
                                tint = if (choice.consequenceType == ConsequenceType.POSITIVE) ComicBadgeGreen else ComicBadgeRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (choice.consequenceType == ConsequenceType.POSITIVE) "ALUR KONSEKUENSI POSITIF" else "ALUR KONSEKUENSI NEGATIF",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                color = if (choice.consequenceType == ConsequenceType.POSITIVE) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = choice.storyResult,
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = Color(0xFF263238)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = choice.teacherGuidance,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (choice.consequenceType == ConsequenceType.POSITIVE) PastelMintPrimary else Color(0xFFC2185B)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChoiceButton(
    choice: ComicChoice,
    isSelected: Boolean,
    isPositive: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) {
        if (isPositive) ComicBadgeGreen else ComicBadgeRed
    } else {
        Color(0xFFE0E0E0)
    }

    val bgColor = if (isSelected) {
        if (isPositive) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    } else {
        Color.White
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (isSelected) 3.dp else 1.dp, RoundedCornerShape(10.dp))
            .background(bgColor, RoundedCornerShape(10.dp))
            .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isPositive) Icons.Default.CheckCircle else Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = if (isPositive) ComicBadgeGreen else ComicBadgeRed,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = choice.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.5.sp,
                    color = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
                Text(
                    text = choice.description,
                    fontSize = 11.5.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
