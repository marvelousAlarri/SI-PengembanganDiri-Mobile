package com.example.sipengembangandiridtedi.ui.riwayat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Pengajuan
import com.example.sipengembangandiridtedi.data.Profil
import com.example.sipengembangandiridtedi.data.StatusPengajuan
import com.example.sipengembangandiridtedi.ui.components.AppTopBar
import com.example.sipengembangandiridtedi.ui.components.CountBadge
import com.example.sipengembangandiridtedi.ui.components.DtOutlineButton
import com.example.sipengembangandiridtedi.ui.components.FilterPill
import com.example.sipengembangandiridtedi.ui.components.SearchField
import com.example.sipengembangandiridtedi.ui.components.StatusChip
import com.example.sipengembangandiridtedi.ui.components.dtCard
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons
import com.example.sipengembangandiridtedi.util.inisial
import com.example.sipengembangandiridtedi.util.toRupiah

private const val SEMUA_JENIS = "Semua Jenis Kegiatan"
private const val SEMUA_STATUS = "Semua Status"

@Composable
fun RiwayatScreen(
    profil: Profil = FakeData.profil,
    data: List<Pengajuan> = FakeData.riwayat,   // TODO API: GET /api/submissions
    notifCount: Int = FakeData.notifBelumDibaca,
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {},
    onDetail: (Int) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf(SEMUA_JENIS) }
    var status by remember { mutableStateOf(SEMUA_STATUS) }

    val hasil = data.filter { p ->
        val digits = query.filter { it.isDigit() }
        val cocokQuery = query.isBlank() ||
                p.judul.contains(query, ignoreCase = true) ||
                p.tanggal.contains(query, ignoreCase = true) ||
                (digits.isNotEmpty() && p.nominalDiajukan.toString().contains(digits))
        val cocokJenis = jenis == SEMUA_JENIS || p.kategori == jenis
        val cocokStatus = status == SEMUA_STATUS || p.status.label == status
        cocokQuery && cocokJenis && cocokStatus
    }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(
            title = "Riwayat",
            notifCount = notifCount,
            initials = profil.nama.inisial(),
            onBell = onBell,
            onAvatar = onAvatar
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Riwayat Pengajuan", color = Dt.TextMain, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text(
                        "Lihat dan kelola seluruh pengajuan dana pengembangan diri Anda di sini.",
                        color = Dt.TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
            item { SearchField(query, { query = it }, "Cari tanggal, nominal...") }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterPill(
                        selected = jenis,
                        options = listOf(SEMUA_JENIS) + FakeData.jenisKegiatan.map { it.nama },
                        onSelect = { jenis = it },
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    FilterPill(
                        selected = status,
                        options = listOf(SEMUA_STATUS) + StatusPengajuan.values().map { it.label },
                        onSelect = { status = it },
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Data Pengajuan", color = Dt.TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    CountBadge("${hasil.size} pengajuan")
                }
            }
            items(hasil, key = { it.id }) { p ->
                RiwayatCard(p) { onDetail(p.id) }
            }
        }
    }
}

@Composable
private fun RiwayatCard(p: Pengajuan, onDetail: () -> Unit) {
    Column(Modifier.fillMaxWidth().dtCard(16.dp).padding(16.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                p.judul,
                color = Dt.TextMain,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            StatusChip(p.status)
        }
        Text(p.tanggal, color = Dt.TextMuted, fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))
        Row {
            Column {
                Text("Diajukan", color = Dt.TextMuted, fontSize = 12.sp)
                Text(p.nominalDiajukan.toRupiah(), color = Dt.TextMain, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.width(24.dp))
            Column {
                Text("Disetujui", color = Dt.TextMuted, fontSize = 12.sp)
                Text(
                    p.nominalDisetujui?.toRupiah() ?: "–",
                    color = Dt.TextMain,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            DtOutlineButton("Detail", onClick = onDetail, trailingIcon = DtIcons.ArrowRight)
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun RiwayatPreview() {
    RiwayatScreen()
}