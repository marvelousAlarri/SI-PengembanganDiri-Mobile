package com.example.sipengembangandiridtedi.ui.beranda

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.DanaInfo
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Pengajuan
import com.example.sipengembangandiridtedi.data.Profil
import com.example.sipengembangandiridtedi.ui.components.AppTopBar
import com.example.sipengembangandiridtedi.ui.components.DtDivider
import com.example.sipengembangandiridtedi.ui.components.StatusChip
import com.example.sipengembangandiridtedi.ui.components.dtCard
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons
import com.example.sipengembangandiridtedi.util.inisial
import com.example.sipengembangandiridtedi.util.toRupiah

@Composable
fun BerandaScreen(
    profil: Profil = FakeData.profil,
    dana: DanaInfo = FakeData.dana,
    ringkasan: List<Pengajuan> = FakeData.riwayat,
    pengajuanAktif: Int = FakeData.pengajuanAktif,
    notifCount: Int = FakeData.notifBelumDibaca,
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {},
    onLihatSemua: () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        AppTopBar(
            title = "Beranda",
            notifCount = notifCount,
            initials = profil.nama.inisial(),
            onBell = onBell,
            onAvatar = onAvatar
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 104.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Selamat datang, ${profil.nama}!",
                        color = Dt.TextMain,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "Pantau dana pengembangan diri dan kelola pengajuan Anda dengan mudah",
                        color = Dt.TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
            item { SisaDanaCard(dana) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatInfoCard(
                        icon = DtIcons.Coins,
                        label = "Total Dana Tahunan",
                        value = dana.total.toRupiah(),
                        caption = "Dana pengembangan",
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    StatInfoCard(
                        icon = DtIcons.File,
                        label = "Pengajuan Aktif",
                        value = "$pengajuanAktif Pengajuan",
                        caption = "sedang diproses",
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Ringkasan Riwayat Pengajuan",
                        color = Dt.TextMain,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "Lihat Semua",
                        color = Dt.Blue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = onLihatSemua)
                    )
                }
            }
            item {
                Column(Modifier.fillMaxWidth().dtCard(16.dp)) {
                    ringkasan.forEachIndexed { i, p ->
                        if (i > 0) DtDivider(Modifier.padding(horizontal = 16.dp))
                        RingkasanRow(p) { onItemClick(p.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun SisaDanaCard(dana: DanaInfo) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(Color(0xFFE6FBF5), Color(0xFFEFF9FF))))
            .border(1.5.dp, Dt.TealBorder, shape)
            .padding(16.dp)
    ) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Dt.Teal),
            contentAlignment = Alignment.Center
        ) {
            Icon(DtIcons.Wallet, null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text("Sisa Dana", color = Dt.TealDark, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Text(
            dana.sisa.toRupiah(),
            color = Dt.TextMain,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text("${dana.persenSisa}% dari total dana", color = Dt.TextMuted, fontSize = 12.sp)
        Spacer(Modifier.height(14.dp))
        // progress bar: bagian terisi = dana terpakai
        Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)).background(Dt.Border)) {
            Box(
                Modifier
                    .fillMaxWidth(dana.persenTerpakai / 100f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Dt.Blue)
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${dana.persenTerpakai}% dana telah digunakan", color = Dt.TextMuted, fontSize = 12.sp)
            Text("${dana.terpakai.toRupiah()} terpakai", color = Dt.TextMuted, fontSize = 12.sp)
        }
    }
}

@Composable
private fun StatInfoCard(
    icon: ImageVector,
    label: String,
    value: String,
    caption: String,
    modifier: Modifier = Modifier
) {
    Column(modifier.dtCard(16.dp).padding(16.dp)) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Dt.BlueSoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Dt.Blue, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text(label, color = Dt.BlueDark, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        Text(value, color = Dt.TextMain, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        Text(caption, color = Dt.TextMuted, fontSize = 12.sp)
    }
}

@Composable
private fun RingkasanRow(p: Pengajuan, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(p.judul, color = Dt.TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(
                "${p.tanggalSingkat} · ${p.nominalDiajukan.toRupiah()}",
                color = Dt.TextMuted,
                fontSize = 12.sp
            )
        }
        Spacer(Modifier.width(8.dp))
        StatusChip(p.status)
        Spacer(Modifier.width(8.dp))
        Icon(DtIcons.ChevronRight, null, tint = Dt.TextMain, modifier = Modifier.size(18.dp))
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun BerandaPreview() {
    BerandaScreen()
}