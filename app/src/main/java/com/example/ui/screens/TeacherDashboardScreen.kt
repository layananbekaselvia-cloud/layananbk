package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.model.QuizQuestion
import com.example.model.QuizSubmission
import com.example.ui.components.ClassScoreBarChart
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelPeachRose
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun TeacherDashboardScreen(
    repository: AppRepository,
    submissions: List<QuizSubmission>,
    questions: List<QuizQuestion>,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    BackHandler {
        onBack()
    }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Ringkasan & Diagram, 1: Hasil Siswa, 2: Manajemen Soal
    var selectedClassFilter by remember { mutableStateOf("Semua") }
    var searchQuery by remember { mutableStateOf("") }

    // Dialog state for editing question
    var questionToEdit by remember { mutableStateOf<QuizQuestion?>(null) }
    var isCreatingQuestion by remember { mutableStateOf(false) }
    var questionToDelete by remember { mutableStateOf<QuizQuestion?>(null) }
    var showResetConfirm by remember { mutableStateOf(false) }

    // Calculate Summary Stats
    val totalStudents = submissions.size
    val highestScore = submissions.maxOfOrNull { it.score } ?: 0
    val lowestScore = submissions.minOfOrNull { it.score } ?: 0
    val avgScore = if (submissions.isNotEmpty()) submissions.map { it.score }.average() else 0.0
    val fastestSubmission = submissions.minByOrNull { it.timeSpentSeconds }

    // Sharing helper for CSV and Reports
    val shareContent = { title: String, content: String, mime: String ->
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, content)
            type = mime
        }
        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F0))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header: Bu Selvi, Layanan BK
            Surface(
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("dashboard_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali ke Beranda",
                            tint = VintageDenimBlue
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(PastelMintPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dashboard Guru BK",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.5.sp,
                            color = VintageDenimBlue
                        )
                        Text(
                            text = "Bu Selvi, S.Pd • Layanan Bimbingan Konseling",
                            fontSize = 11.5.sp,
                            color = PastelMintPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Quick Download Action Strip
            Surface(
                color = Color(0xFFF3ECE0),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Export Excel
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                val csv = repository.generateExcelCsvContent()
                                shareContent("Hasil_Kuis_BK_Siswa.csv", csv, "text/csv")
                            }
                            .testTag("download_excel_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.TableChart,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Unduh Excel",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }

                    // Export PDF Report
                    Surface(
                        color = Color(0xFFEDE7F6),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                val report = repository.generateOfficialPdfReport()
                                shareContent("Laporan_Resmi_Asesmen_BK.txt", report, "text/plain")
                            }
                            .testTag("download_pdf_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = Color(0xFF512DA8),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Laporan BK",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF311B92)
                            )
                        }
                    }

                    // Export Soal & Kunci
                    Surface(
                        color = VintageBrassContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1.2f)
                            .clickable {
                                val doc = repository.generateQuestionsAndKeyDoc()
                                shareContent("Soal_dan_Kunci_Jawaban_BK.txt", doc, "text/plain")
                            }
                            .testTag("download_questions_key_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FileDownload,
                                contentDescription = null,
                                tint = VintageBrassPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Soal & Kunci",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = VintageBrassPrimary
                            )
                        }
                    }
                }
            }

            // Tab Navigation
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = VintageBrassPrimary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Diagram & Statistik", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Hasil Siswa (${submissions.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Edit Soal (${questions.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Tab Content
            when (selectedTab) {
                0 -> {
                    // TAB 0: Diagram & Statistik
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Summary 4-Grid Cards
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    StatSummaryCard(
                                        title = "Nilai Tertinggi",
                                        value = "$highestScore",
                                        subtitle = "Skor Maksimal",
                                        icon = Icons.Default.TrendingUp,
                                        accentColor = PastelMintPrimary,
                                        modifier = Modifier.weight(1f)
                                    )
                                    StatSummaryCard(
                                        title = "Nilai Terendah",
                                        value = "$lowestScore",
                                        subtitle = "Perlu Bimbingan",
                                        icon = Icons.Default.TrendingDown,
                                        accentColor = PastelPeachRose,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    StatSummaryCard(
                                        title = "Rata-rata Nilai",
                                        value = "%.1f".format(avgScore),
                                        subtitle = "Dari $totalStudents murid",
                                        icon = Icons.Default.School,
                                        accentColor = VintageDenimBlue,
                                        modifier = Modifier.weight(1f)
                                    )
                                    StatSummaryCard(
                                        title = "Waktu Tercepat",
                                        value = fastestSubmission?.durationFormatted ?: "-",
                                        subtitle = fastestSubmission?.studentName?.take(10) ?: "-",
                                        icon = Icons.Default.Speed,
                                        accentColor = VintageBrassPrimary,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        // Diagram Rata-rata Nilai per Kelas (9A - 9E)
                        item {
                            ClassScoreBarChart(submissions = submissions)
                        }

                        // Info Notes
                        item {
                            Surface(
                                color = PastelMintContainer,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = PastelMintPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Hasil asesmen dapat langsung diunduh dalam format Excel (CSV) untuk pelaporan bulanan BK dan dokumen akreditasi sekolah.",
                                        fontSize = 12.sp,
                                        color = Color(0xFF1B5E20),
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: Daftar Hasil Siswa
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        // Class Filter Tabs
                        val classFilters = listOf("Semua", "9A", "9B", "9C", "9D", "9E")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            classFilters.forEach { f ->
                                val isSelected = selectedClassFilter == f
                                Surface(
                                    color = if (isSelected) VintageDenimBlue else Color.White,
                                    shape = RoundedCornerShape(16.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) VintageDenimBlue else Color(0xFFCFD8DC)
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedClassFilter = f }
                                ) {
                                    Text(
                                        text = f,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else Color.DarkGray,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 6.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Search Field
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari nama siswa...", fontSize = 12.5.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val filteredSubmissions = submissions.filter { s ->
                            val matchClass = selectedClassFilter == "Semua" || s.studentClass == selectedClassFilter
                            val matchQuery = searchQuery.isBlank() || s.studentName.contains(searchQuery, ignoreCase = true)
                            matchClass && matchQuery
                        }

                        if (filteredSubmissions.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Belum ada data siswa untuk filter ini.",
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredSubmissions) { sub ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .border(1.dp, Color(0xFFE8E0D2), RoundedCornerShape(12.dp))
                                                .padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(
                                                        if (sub.score >= 75) PastelMintContainer else PastelPeachRose.copy(alpha = 0.2f),
                                                        CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${sub.score}",
                                                    fontWeight = FontWeight.Black,
                                                    fontSize = 14.sp,
                                                    color = if (sub.score >= 75) PastelMintPrimary else PastelPeachRose
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = sub.studentName,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 13.5.sp,
                                                    color = Color(0xFF263238)
                                                )
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Surface(
                                                        color = Color(0xFFEFE8DB),
                                                        shape = RoundedCornerShape(4.dp)
                                                    ) {
                                                        Text(
                                                            text = "Kelas ${sub.studentClass}",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = VintageDenimBlue,
                                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = "Waktu: ${sub.durationFormatted}",
                                                        fontSize = 11.sp,
                                                        color = Color.Gray
                                                    )
                                                }
                                            }

                                            Text(
                                                text = sub.completedAtFormatted,
                                                fontSize = 10.5.sp,
                                                color = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: Manajemen & Edit Soal Tes (tiap nomor soal ada pilihan edit dan hapus)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        // Action bar: Tambah Soal & Reset Default
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { isCreatingQuestion = true },
                                colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("add_question_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Tambah Soal", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { showResetConfirm = true },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RestartAlt,
                                    contentDescription = null,
                                    tint = VintageBrassPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset Soal BK", fontSize = 11.5.sp, color = VintageBrassPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Daftar ${questions.size} Soal HOTS (Tiap nomor dapat diedit atau dihapus):",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            itemsIndexed(questions) { index, q ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .border(1.2.dp, Color(0xFFE5DDD0), RoundedCornerShape(12.dp))
                                            .padding(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Surface(
                                                    color = VintageDenimBlue,
                                                    shape = RoundedCornerShape(6.dp)
                                                ) {
                                                    Text(
                                                        text = "No. ${index + 1}",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 11.5.sp,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = q.category,
                                                    fontSize = 11.sp,
                                                    color = VintageBrassPrimary,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }

                                            // Edit & Delete Action Buttons
                                            Row {
                                                IconButton(
                                                    onClick = { questionToEdit = q },
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .testTag("edit_q_${q.id}")
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Edit,
                                                        contentDescription = "Edit Soal",
                                                        tint = PastelMintPrimary,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }

                                                IconButton(
                                                    onClick = { questionToDelete = q },
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .testTag("delete_q_${q.id}")
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Hapus Soal",
                                                        tint = Color(0xFFD32F2F),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = q.question,
                                            fontSize = 12.5.sp,
                                            lineHeight = 17.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF263238)
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Surface(
                                            color = PastelMintContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "Kunci: ${q.correctOptionLetter}. ${q.correctOptionText}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1B5E20),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog for Editing Existing Question
    if (questionToEdit != null) {
        QuestionEditorDialog(
            question = questionToEdit,
            onDismiss = { questionToEdit = null },
            onSave = { updated ->
                repository.editQuestion(updated)
                questionToEdit = null
            }
        )
    }

    // Dialog for Adding New Question
    if (isCreatingQuestion) {
        QuestionEditorDialog(
            question = null,
            onDismiss = { isCreatingQuestion = false },
            onSave = { newQ ->
                repository.addQuestion(newQ)
                isCreatingQuestion = false
            }
        )
    }

    // Delete Confirmation Dialog
    if (questionToDelete != null) {
        AlertDialog(
            onDismissRequest = { questionToDelete = null },
            title = { Text("Hapus Soal?") },
            text = {
                Text(
                    "Apakah Anda yakin ingin menghapus soal: \"${questionToDelete?.question?.take(60)}...\"?",
                    fontSize = 13.5.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        questionToDelete?.let { repository.deleteQuestion(it.id) }
                        questionToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { questionToDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }

    // Reset to Default Confirmation Dialog
    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text("Reset ke Soal Standar BK?") },
            text = {
                Text(
                    "Semua soal akan dikembalikan ke 15 butir soal HOTS default Bimbingan Konseling Bu Selvi.",
                    fontSize = 13.5.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        repository.resetQuestionsToDefault()
                        showResetConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VintageBrassPrimary)
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirm = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun StatSummaryCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.2.dp, Color(0xFFE5DDD0), RoundedCornerShape(14.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = accentColor
            )
            Text(
                text = subtitle,
                fontSize = 10.5.sp,
                color = Color.Gray
            )
        }
    }
}
