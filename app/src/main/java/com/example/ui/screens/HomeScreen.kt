package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ComicChapter
import com.example.model.StudentProfile
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelPeachRose
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun HomeScreen(
    student: StudentProfile,
    chapters: List<ComicChapter>,
    onSelectChapter: (ComicChapter) -> Unit,
    onStartQuiz: () -> Unit,
    onOpenTeacherDashboard: () -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F0))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .border(1.dp, Color(0xFFE2D8C6), RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(PastelMintPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = student.studentClass,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = student.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = VintageDenimBlue
                            )
                            Text(
                                text = "Murid Kelas ${student.studentClass}",
                                fontSize = 10.5.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Teacher Dashboard PIN Button
                        Surface(
                            color = VintageBrassContainer,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .clickable { onOpenTeacherDashboard() }
                                .testTag("home_teacher_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = VintageBrassPrimary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Guru BK",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VintageBrassPrimary
                                )
                            }
                        }

                        // Ganti Akun / Keluar
                        Surface(
                            color = Color(0xFFF0EBE0),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.clickable { onLogout() }
                        ) {
                            Text(
                                text = "Ganti",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Hero Banner Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.comic_cover_banner_1791988033854),
                            contentDescription = "Cover BK Sahabat",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xCC000000))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Surface(
                                color = VintageBrassPrimary,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "WEBTOON EDUKATIF BK",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Jaga Batasan, Raih Masa Depan",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Bersama Bu Selvi, S.Pd • Bimbingan Konseling SMP",
                                color = Color(0xFFE0E0E0),
                                fontSize = 11.5.sp
                            )
                        }
                    }
                }
            }

            // HOTS Quiz CTA Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onStartQuiz() }
                        .testTag("start_quiz_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PastelMintPrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Kuis 15 Soal HOTS BK",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = Color(0xFFFFD54F),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "15 Menit",
                                        color = Color(0xFF3E2723),
                                        fontWeight = FontWeight.Black,
                                        fontSize = 9.sp,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Soal tingkat tinggi kasus remaja nyata. Uji pemahaman setelah membaca komik!",
                                color = Color(0xFFE8F5E9),
                                fontSize = 11.5.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // Chapter List Title
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "EPISODE WEBTOON KOMIK",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.5.sp,
                        color = VintageDenimBlue,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "${chapters.size} Episode Lengkap",
                        fontSize = 11.5.sp,
                        color = Color.Gray
                    )
                }
            }

            // Chapters
            items(chapters) { chapter ->
                EpisodeCard(
                    chapter = chapter,
                    onClick = { onSelectChapter(chapter) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun EpisodeCard(
    chapter: ComicChapter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chapter_item_${chapter.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .border(1.2.dp, Color(0xFFE5DDD0), RoundedCornerShape(16.dp))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter Number Badge
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        when (chapter.id) {
                            1 -> PastelPeachRose
                            2 -> VintageBrassPrimary
                            3 -> VintageDenimBlue
                            else -> PastelMintPrimary
                        },
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "EP",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${chapter.id}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = Color(0xFFF4ECE1),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = chapter.themeTag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = VintageBrassPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chapter.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp,
                    color = Color(0xFF263238),
                    lineHeight = 18.sp
                )
                Text(
                    text = chapter.summary,
                    fontSize = 11.5.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Buka Episode",
                tint = Color.LightGray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
