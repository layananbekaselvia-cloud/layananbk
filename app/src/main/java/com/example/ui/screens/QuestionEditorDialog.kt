package com.example.ui.screens

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizQuestion
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.VintageBrassPrimary

@Composable
fun QuestionEditorDialog(
    question: QuizQuestion?,
    onDismiss: () -> Unit,
    onSave: (QuizQuestion) -> Unit
) {
    var questionText by remember { mutableStateOf(question?.question ?: "") }
    var categoryText by remember { mutableStateOf(question?.category ?: "Bimbingan Konseling") }
    var opt0 by remember { mutableStateOf(question?.options?.getOrNull(0) ?: "") }
    var opt1 by remember { mutableStateOf(question?.options?.getOrNull(1) ?: "") }
    var opt2 by remember { mutableStateOf(question?.options?.getOrNull(2) ?: "") }
    var opt3 by remember { mutableStateOf(question?.options?.getOrNull(3) ?: "") }
    var correctIndex by remember { mutableIntStateOf(question?.correctIndex ?: 0) }
    var explanationText by remember { mutableStateOf(question?.explanation ?: "") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = VintageBrassPrimary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (question == null) "Tambah Soal Baru" else "Edit Soal Nomor ${question.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Category
                Text("Kategori / Topik Materi", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = categoryText,
                    onValueChange = { categoryText = it },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Question Text
                Text("Pertanyaan Soal HOTS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    maxLines = 4,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Options with radio for correct answer
                Text("Pilihan Jawaban (Tandai Kunci yang Benar):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VintageBrassPrimary)
                Spacer(modifier = Modifier.height(6.dp))

                val options = listOf(
                    Triple("A", opt0) { v: String -> opt0 = v },
                    Triple("B", opt1) { v: String -> opt1 = v },
                    Triple("C", opt2) { v: String -> opt2 = v },
                    Triple("D", opt3) { v: String -> opt3 = v }
                )

                options.forEachIndexed { idx, (label, value, setter) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = correctIndex == idx,
                            onClick = { correctIndex = idx }
                        )
                        Text(
                            text = "$label.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.width(20.dp)
                        )
                        OutlinedTextField(
                            value = value,
                            onValueChange = setter,
                            placeholder = { Text("Pilihan $label", fontSize = 12.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Explanation
                Text("Penjelasan / Pembahasan BK:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = explanationText,
                    onValueChange = { explanationText = it },
                    maxLines = 3,
                    placeholder = { Text("Alasan pedagogis jawaban ini benar...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMsg != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMsg ?: "",
                        color = Color(0xFFD32F2F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (questionText.isBlank() || opt0.isBlank() || opt1.isBlank() || opt2.isBlank() || opt3.isBlank()) {
                        errorMsg = "Semua kolom pertanyaan dan 4 pilihan A-D harus diisi!"
                    } else {
                        val updated = QuizQuestion(
                            id = question?.id ?: 0,
                            question = questionText.trim(),
                            options = listOf(opt0.trim(), opt1.trim(), opt2.trim(), opt3.trim()),
                            correctIndex = correctIndex,
                            explanation = if (explanationText.isNotBlank()) explanationText.trim() else "Penjelasan materi bimbingan konseling.",
                            category = if (categoryText.isNotBlank()) categoryText.trim() else "BK Sahabat Remaja",
                            isCustom = true
                        )
                        onSave(updated)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("save_question_button")
            ) {
                Text("Simpan Soal", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
