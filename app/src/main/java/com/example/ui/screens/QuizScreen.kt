package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizQuestion
import com.example.ui.theme.ComicBadgeGold
import com.example.ui.theme.ComicBadgeGreen
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelPeachRose
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    questions: List<QuizQuestion>,
    quizStartTimeMillis: Long,
    onBack: () -> Unit,
    onSubmitQuiz: (score: Int, correctCount: Int, total: Int, durationSeconds: Long) -> Unit
) {
    val totalTimeSeconds = 15 * 60L // 15 Menit = 900 detik

    var currentIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionId -> selectedOptionIndex
    var showSubmitConfirmation by remember { mutableStateOf(false) }
    var showExitWarning by remember { mutableStateOf(false) }

    // Persistent Countdown based on System.currentTimeMillis()
    var remainingSeconds by remember {
        val elapsed = (System.currentTimeMillis() - quizStartTimeMillis) / 1000L
        mutableLongStateOf((totalTimeSeconds - elapsed).coerceAtLeast(0L))
    }

    LaunchedEffect(quizStartTimeMillis) {
        while (true) {
            val elapsed = (System.currentTimeMillis() - quizStartTimeMillis) / 1000L
            val left = (totalTimeSeconds - elapsed).coerceAtLeast(0L)
            remainingSeconds = left
            if (left <= 0L) {
                // Time up! Auto submit!
                val correctCount = questions.count { q -> userAnswers[q.id] == q.correctIndex }
                val score = if (questions.isNotEmpty()) (correctCount * 100) / questions.size else 0
                onSubmitQuiz(score, correctCount, questions.size, totalTimeSeconds)
                break
            }
            delay(1000L)
        }
    }

    BackHandler {
        showExitWarning = true
    }

    val currentQuestion = questions.getOrNull(currentIndex)
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = "%02d:%02d".format(minutes, seconds)
    val isTimerLow = remainingSeconds < 120 // less than 2 minutes left

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6F0))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar with Timer and Progress
            Surface(
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { showExitWarning = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Tutup Kuis",
                                tint = Color.Gray
                            )
                        }

                        // Vintage Clockwork Timer Widget
                        Surface(
                            color = if (isTimerLow) Color(0xFFFFEBEE) else VintageBrassContainer,
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isTimerLow) Color(0xFFE53935) else VintageBrassPrimary
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Sisa Waktu",
                                    tint = if (isTimerLow) Color(0xFFD32F2F) else VintageBrassPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = timerText,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (isTimerLow) Color(0xFFD32F2F) else VintageBrassPrimary
                                )
                            }
                        }

                        // Answered counter
                        Surface(
                            color = PastelMintContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${userAnswers.size}/${questions.size} Terjawab",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = PastelMintPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    val progress = if (questions.isNotEmpty()) (currentIndex + 1).toFloat() / questions.size else 0f
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PastelMintPrimary,
                        trackColor = Color(0xFFE0E0E0)
                    )
                }
            }

            // Question Display - Satu per satu (One by One)
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Question Number and Category
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = VintageDenimBlue,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Soal ${currentIndex + 1} dari ${questions.size}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            color = Color(0xFFEFE8DB),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = currentQuestion.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF5D4037),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Question Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .border(1.5.dp, Color(0xFFE0D7C6), RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = currentQuestion.question,
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E272C)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Multiple Choice Options (A, B, C, D)
                    val optionLetters = listOf("A", "B", "C", "D")
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = userAnswers[currentQuestion.id] == optIndex
                        val letter = optionLetters.getOrElse(optIndex) { "-" }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    userAnswers[currentQuestion.id] = optIndex
                                }
                                .testTag("quiz_opt_${currentIndex}_$optIndex"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) PastelMintContainer else Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .border(
                                        1.5.dp,
                                        if (isSelected) PastelMintPrimary else Color(0xFFE5DDD0),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Option letter badge
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            if (isSelected) PastelMintPrimary else Color(0xFFF0EBE0),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = letter,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color.White else Color(0xFF4E342E)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = optionText,
                                    fontSize = 13.5.sp,
                                    lineHeight = 19.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color(0xFF0F3823) else Color(0xFF263238),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // Bottom Navigation Bar (Prev, Next / Submit)
            Surface(
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Question Button
                    OutlinedButton(
                        onClick = {
                            if (currentIndex > 0) currentIndex--
                        },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("quiz_prev_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sebelumnya", fontSize = 12.5.sp)
                    }

                    // Next or Finish Button
                    if (currentIndex < questions.size - 1) {
                        Button(
                            onClick = {
                                if (currentIndex < questions.size - 1) currentIndex++
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = VintageBrassPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("quiz_next_button")
                        ) {
                            Text("Selanjutnya", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = { showSubmitConfirmation = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("quiz_submit_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Kumpulkan", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitConfirmation) {
        val answeredCount = userAnswers.size
        val unansweredCount = questions.size - answeredCount

        AlertDialog(
            onDismissRequest = { showSubmitConfirmation = false },
            title = {
                Text(
                    text = "Kumpulkan Jawaban Kuis?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Kamu telah menjawab $answeredCount dari ${questions.size} soal.",
                        fontSize = 13.5.sp
                    )
                    if (unansweredCount > 0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Peringatan: Masih ada $unansweredCount soal yang belum dijawab!",
                            color = Color(0xFFC62828),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Apakah kamu yakin ingin menyelesaikan sekarang?",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmation = false
                        val correctCount = questions.count { q -> userAnswers[q.id] == q.correctIndex }
                        val score = if (questions.isNotEmpty()) (correctCount * 100) / questions.size else 0
                        val spent = totalTimeSeconds - remainingSeconds
                        onSubmitQuiz(score, correctCount, questions.size, spent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary)
                ) {
                    Text("Ya, Kumpulkan", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitConfirmation = false }) {
                    Text("Periksa Lagi")
                }
            }
        )
    }

    // Exit Warning Dialog
    if (showExitWarning) {
        AlertDialog(
            onDismissRequest = { showExitWarning = false },
            title = { Text("Tinggalkan Kuis?") },
            text = {
                Text(
                    "Timer 15 menit akan terus berjalan jika kamu keluar dari layar ini. Apakah kamu yakin ingin kembali ke beranda?",
                    fontSize = 13.5.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitWarning = false
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitWarning = false }) {
                    Text("Lanjutkan Kuis")
                }
            }
        )
    }
}
