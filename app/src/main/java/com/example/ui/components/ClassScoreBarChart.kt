package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizSubmission
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelPeachRose
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun ClassScoreBarChart(
    submissions: List<QuizSubmission>,
    modifier: Modifier = Modifier
) {
    val classes = listOf("9A", "9B", "9C", "9D", "9E")
    val classStats = classes.map { cls ->
        val list = submissions.filter { it.studentClass == cls }
        val count = list.size
        val avg = if (count > 0) list.map { it.score }.average().toFloat() else 0f
        Triple(cls, avg, count)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.5.dp, Color(0xFFE2D9C8), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DIAGRAM RATA-RATA NILAI PER KELAS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = VintageBrassPrimary
                )
                Text(
                    text = "Skala 0 - 100",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Canvas Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val barColors = listOf(
                    Color(0xFF4E876A), // Mint
                    Color(0xFF284869), // Denim
                    Color(0xFFD97724), // Brass
                    Color(0xFFD65A68), // Rose
                    Color(0xFF6B4E71)  // Purple
                )

                Canvas(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    val width = size.width
                    val height = size.height
                    val bottomPadding = 36f
                    val topPadding = 20f
                    val chartHeight = height - bottomPadding - topPadding

                    // Draw horizontal guide lines (0, 50, 100)
                    val steps = listOf(0.25f, 0.5f, 0.75f, 1f)
                    steps.forEach { step ->
                        val y = topPadding + chartHeight * (1f - step)
                        drawLine(
                            color = Color(0xFFEAE2D5),
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1.5f
                        )
                    }

                    val slotWidth = width / classes.size
                    val barWidth = slotWidth * 0.48f

                    classStats.forEachIndexed { index, (cls, avg, count) ->
                        val barHeight = (avg / 100f) * chartHeight
                        val left = index * slotWidth + (slotWidth - barWidth) / 2f
                        val top = topPadding + (chartHeight - barHeight)
                        val barColor = barColors.getOrElse(index) { PastelMintPrimary }

                        // Draw background track
                        drawRoundRect(
                            color = Color(0xFFF3ECE0),
                            topLeft = Offset(left, topPadding),
                            size = Size(barWidth, chartHeight),
                            cornerRadius = CornerRadius(8f, 8f)
                        )

                        // Draw filled bar
                        if (barHeight > 0) {
                            drawRoundRect(
                                color = barColor,
                                topLeft = Offset(left, top),
                                size = Size(barWidth, barHeight),
                                cornerRadius = CornerRadius(8f, 8f)
                            )
                        }

                        // Draw text via native canvas
                        drawContext.canvas.nativeCanvas.apply {
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.DKGRAY
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.CENTER
                                isFakeBoldText = true
                            }

                            // Class name label
                            drawText(cls, left + barWidth / 2f, height - 8f, paint)

                            // Score text on top of bar
                            val scorePaint = android.graphics.Paint().apply {
                                color = if (avg > 0) android.graphics.Color.BLACK else android.graphics.Color.LTGRAY
                                textSize = 26f
                                textAlign = android.graphics.Paint.Align.CENTER
                                isFakeBoldText = true
                            }
                            val scoreLabel = if (avg > 0) "%.0f".format(avg) else "0"
                            drawText(scoreLabel, left + barWidth / 2f, (top - 8f).coerceAtLeast(topPadding + 20f), scorePaint)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend & Student counts per class
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                classStats.forEach { (cls, avg, count) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = cls,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = VintageDenimBlue
                        )
                        Text(
                            text = "$count murid",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
