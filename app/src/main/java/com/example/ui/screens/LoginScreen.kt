package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.PastelMintContainer
import com.example.ui.theme.PastelMintPrimary
import com.example.ui.theme.PastelRoseContainer
import com.example.ui.theme.VintageBrassContainer
import com.example.ui.theme.VintageBrassPrimary
import com.example.ui.theme.VintageDenimBlue

@Composable
fun LoginScreen(
    onLoginSuccess: (name: String, studentClass: String) -> Unit,
    onOpenTeacherDashboard: () -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("9A") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val classOptions = listOf("9A", "9B", "9C", "9D", "9E")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF7F3E9),
                        Color(0xFFEFE9DB),
                        Color(0xFFE5DDD0)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar: BK Mascot and Teacher Dashboard quick link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Layanan BK SMP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PastelMintPrimary
                    )
                }

                Surface(
                    color = VintageBrassContainer,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .clickable { onOpenTeacherDashboard() }
                        .testTag("teacher_dashboard_pin_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "PIN Guru",
                            tint = VintageBrassPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Dashboard Guru (PIN)",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = VintageBrassPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cover Image Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.comic_cover_banner_1791988033854),
                        contentDescription = "Webtoon BK Siswa & Bu Selvi",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0x99000000))
                                )
                            )
                    )
                    Text(
                        text = "Webtoon Komik BK: Sahabat Remaja",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Main Login Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .border(1.5.dp, Color(0xFFDCCFBC), RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Masuk & Mulai Membaca",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = VintageDenimBlue
                    )
                    Text(
                        text = "Isi nama lengkap dan pilih kelasmu (9A - 9E) untuk memulai petualangan komik dan asesmen BK.",
                        fontSize = 12.5.sp,
                        color = Color.Gray,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    // Input Nama Lengkap
                    Text(
                        text = "Nama Lengkap Murid",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = {
                            nameInput = it
                            if (errorMessage != null) errorMessage = null
                        },
                        placeholder = { Text("Contoh: Muhammad Rizky", fontSize = 13.5.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = PastelMintPrimary
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PastelMintPrimary,
                            unfocusedBorderColor = Color(0xFFCFD8DC),
                            focusedContainerColor = Color(0xFFFAFAFA),
                            unfocusedContainerColor = Color(0xFFFAFAFA)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_name_input")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pilih Kelas (9A, 9B, 9C, 9D, 9E)
                    Text(
                        text = "Pilih Kelas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        classOptions.forEach { cls ->
                            val isSelected = selectedClass == cls
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .shadow(if (isSelected) 2.dp else 0.dp, RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) PastelMintPrimary else Color(0xFFF5F5F5),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isSelected) PastelMintPrimary else Color(0xFFE0E0E0),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedClass = cls }
                                    .testTag("class_choice_$cls"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cls,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else Color(0xFF424242)
                                )
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFD32F2F),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Login Button
                    Button(
                        onClick = {
                            if (nameInput.trim().length < 2) {
                                errorMessage = "Harap masukkan nama lengkapmu minimal 2 huruf!"
                            } else {
                                onLoginSuccess(nameInput.trim(), selectedClass)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("start_reading_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Lanjut Baca Komik",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Counselor Welcome Note Card
            Surface(
                color = PastelMintContainer,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(PastelMintPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BS",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Pesan Hangat Bu Selvi (Guru BK):",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelMintPrimary
                        )
                        Text(
                            text = "\"Selamat membaca anak-anakku. Belajar menjaga diri dan batasan adalah bukti kedewasaan sejati!\"",
                            fontSize = 12.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = Color(0xFF1B5E20)
                        )
                    }
                }
            }
        }
    }
}
