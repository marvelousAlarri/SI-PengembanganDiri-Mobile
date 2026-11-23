package com.example.sipengembangandiridtedi.ui.screens.landing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.components.AppTopBar
import com.example.sipengembangandiridtedi.ui.components.PrimaryButton
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BlueDark
import com.example.sipengembangandiridtedi.ui.theme.BluePrimary
import com.example.sipengembangandiridtedi.ui.theme.BlueSoft
import com.example.sipengembangandiridtedi.ui.theme.HeroBottom
import com.example.sipengembangandiridtedi.ui.theme.HeroTop
import com.example.sipengembangandiridtedi.ui.theme.SkyBar
import com.example.sipengembangandiridtedi.ui.theme.TealOk
import com.example.sipengembangandiridtedi.ui.theme.TextPrimary
import com.example.sipengembangandiridtedi.ui.theme.TextSecondary
import com.example.sipengembangandiridtedi.ui.theme.TrackGray

@Composable
fun LandingScreen(onLogin: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
    ) {
        AppTopBar(
            trailing = {
                OutlinedButton(
                    onClick = onLogin,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, BlueDark),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BlueDark),
                ) {
                    Text("Login", fontWeight = FontWeight.SemiBold)
                }
            },
        )

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            HeroSection(onLogin)
            AboutSection()
        }
    }
}

@Composable
private fun HeroSection(onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(HeroTop, HeroBottom)))
            .padding(horizontal = 20.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Selamat Datang di Sistem Pengembangan Diri DTEDI",
            fontSize = 32.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Platform untuk mendukung proses pengajuan dan pengelolaan dana pengembangan diri bagi dosen secara lebih terstruktur dan terdokumentasi.",
            fontSize = 15.sp,
            lineHeight = 23.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        PrimaryButton(
            text = "Masuk ke Sistem",
            onClick = onLogin,
            modifier = Modifier.width(202.dp),
            containerColor = BlueDark,
            shape = CircleShape,
            trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
        )
        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            PengajuanDanaCard(Modifier.weight(1f))
            SisaDanaCard(Modifier.weight(1f))
        }
    }
}

@Composable
private fun PengajuanDanaCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Pengajuan Dana",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BluePrimary,
            )
            repeat(3) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = TealOk,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .weight(1f)
                            .height(5.dp)
                            .clip(CircleShape)
                            .background(TrackGray),
                    )
                }
            }
        }
    }
}

@Composable
private fun SisaDanaCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Sisa Dana",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BluePrimary,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Rp 3.750.000",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))

            // Progress bar
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(TrackGray),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                        .background(BluePrimary),
                )
            }

            Spacer(Modifier.height(14.dp))

            // Grafik batang
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                listOf(14, 22, 30, 44).forEach { barHeight ->
                    Box(
                        Modifier
                            .weight(1f)
                            .height(barHeight.dp)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(SkyBar),
                    )
                }
            }
        }
    }
}

@Composable
private fun AboutSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 28.dp)
            .navigationBarsPadding(),
    ) {
        Box(
            Modifier
                .clip(CircleShape)
                .background(BlueSoft)
                .padding(horizontal = 14.dp, vertical = 6.dp),
        ) {
            Text(
                text = "Tentang Sistem",
                color = BluePrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Apa Itu Sistem Pengembangan Diri?",
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Sistem Pengembangan Diri digunakan untuk membantu proses pengajuan, pemeriksaan, persetujuan, dan pengelolaan dana pengembangan diri. Sistem ini mendukung proses administrasi yang lebih terstruktur dengan menyediakan informasi pengajuan, dokumen pendukung, status proses, serta riwayat pengajuan.",
            fontSize = 15.sp,
            lineHeight = 23.sp,
            color = TextSecondary,
            textAlign = TextAlign.Justify,
        )

        Spacer(Modifier.height(32.dp))

        PhoneIllustration()
    }
}

// Ilustrasi sederhana dari komponen Compose. Kalau mau persis desain,
// ekspor SVG-nya dari Figma lalu ganti dengan Image(painterResource(...)).
@Composable
private fun PhoneIllustration() {
    val purple = Color(0xFF6D5EF3)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            Modifier
                .width(150.dp)
                .fillMaxHeight()
                .border(5.dp, TextPrimary, RoundedCornerShape(28.dp))
                .padding(18.dp),
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE5E7EB))
                    .padding(12.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) { index ->
                        Box(
                            Modifier
                                .fillMaxWidth(if (index == 0) 0.6f else 1f)
                                .height(4.dp)
                                .background(if (index == 0) purple else Color(0xFFCBD0D8)),
                        )
                    }
                }
            }
        }

        FloatingTag(Modifier.align(Alignment.TopCenter).offset(x = (-100).dp, y = 70.dp), purple)
        FloatingTag(Modifier.align(Alignment.TopCenter).offset(x = 100.dp, y = 20.dp), purple)
    }
}

@Composable
private fun FloatingTag(modifier: Modifier = Modifier, color: Color) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Box(Modifier.width(36.dp).height(3.dp).background(color))
        Box(Modifier.width(28.dp).height(3.dp).background(color))
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun LandingScreenPreview() {
    AppTheme { LandingScreen(onLogin = {}) }
}
