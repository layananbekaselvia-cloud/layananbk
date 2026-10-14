package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ComicChapter
import com.example.ui.components.ComicPanelItem
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun ComicReaderScreen(
    chapter: ComicChapter,
    hasPreviousChapter: Boolean,
    hasNextChapter: Boolean,
    onBack: () -> Unit,
    onPreviousChapter: () -> Unit,
    onNextChapter: () -> Unit,
    onStartQuiz: () -> Unit
) {
    BackHandler {
        onBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6F0))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Surface(
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("comic_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = VintageDenimBlue
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Episode ${chapter.id}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VintageBrassPrimary
                        )
                        Text(
                            text = chapter.title,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = VintageDenimBlue,
                            maxLines = 1
                        )
                    }

                    Surface(
                        color = VintageBrassContainer,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = chapter.themeTag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = VintageBrassPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Webtoon Vertical Canvas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Chapter Header Presentation Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            // Cover illustration if available
                            if (chapter.coverResId != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = chapter.coverResId),
                                        contentDescription = "Cover Episode",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = chapter.subtitle,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = VintageDenimBlue
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = chapter.summary,
                                    fontSize = 12.5.sp,
                                    lineHeight = 17.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }

                // Webtoon Comic Panels
                items(chapter.panels) { panel ->
                    ComicPanelItem(panel = panel)
                }

                // Episode End Action Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .border(1.5.dp, Color(0xFFDCCFBC), RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Akhir Episode ${chapter.id}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                color = VintageDenimBlue
                            )
                            Text(
                                text = "Ingat selalu pesan Bu Selvi dan terapkan batasan sehat dalam keseharianmu!",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (hasPreviousChapter) {
                                    OutlinedButton(
                                        onClick = onPreviousChapter,
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Eps Sebelumnya", fontSize = 12.sp)
                                    }
                                }

                                if (hasNextChapter) {
                                    Button(
                                        onClick = onNextChapter,
                                        colors = ButtonDefaults.buttonColors(containerColor = VintageBrassPrimary),
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("Eps Selanjutnya", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Start Quiz Button
                            Button(
                                onClick = onStartQuiz,
                                colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("reader_start_quiz_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Uji Pemahaman: Mulai Kuis 15 Soal HOTS",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
