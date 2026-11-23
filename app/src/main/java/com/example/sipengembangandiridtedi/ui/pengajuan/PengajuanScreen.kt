package com.example.sipengembangandiridtedi.ui.pengajuan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Pengajuan
import com.example.sipengembangandiridtedi.data.Profil
import com.example.sipengembangandiridtedi.ui.components.AppTopBar
import com.example.sipengembangandiridtedi.ui.components.CountBadge
import com.example.sipengembangandiridtedi.ui.components.FilterPill
import com.example.sipengembangandiridtedi.ui.components.IconOutlineButton
import com.example.sipengembangandiridtedi.ui.components.SearchField
import com.example.sipengembangandiridtedi.ui.components.dtCard
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons
import com.example.sipengembangandiridtedi.util.inisial
import com.example.sipengembangandiridtedi.util.toRupiah

private const val SEMUA_JENIS = "Semua Jenis Kegiatan"

@Composable
fun PengajuanScreen(
    profil: Profil = FakeData.profil,
    notifCount: Int = FakeData.notifBelumDibaca,
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {},
    onLanjutkanDraf: (Int) -> Unit = {}
) {
    // TODO API: draf belum ada di API backend, jadi disimpan lokal (dummy).
    val draf = remember { mutableStateListOf<Pengajuan>().apply { addAll(FakeData.draf) } }
    var query by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf(SEMUA_JENIS) }

    val hasil = draf.filter { p ->
        val digits = query.filter { it.isDigit() }
        val cocokQuery = query.isBlank() ||
                p.judul.contains(query, ignoreCase = true) ||
                p.tanggal.contains(query, ignoreCase = true) ||
                (digits.isNotEmpty() && p.nominalDiajukan.toString().contains(digits))
        val cocokJenis = jenis == SEMUA_JENIS || p.kategori == jenis
        cocokQuery && cocokJenis
    }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(
            title = "Pengajuan",
            notifCount = notifCount,
            initials = profil.nama.inisial(),
            onBell = onBell,
            onAvatar = onAvatar
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 104.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Pengajuan Saya", color = Dt.TextMain, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text(
                        "Lihat dan kelola seluruh pengajuan dana pengembangan diri Anda di sini.",
                        color = Dt.TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
            item { SearchField(query, { query = it }, "Cari tanggal, nominal...") }
            item {
                FilterPill(
                    selected = jenis,
                    options = listOf(SEMUA_JENIS) + FakeData.jenisKegiatan.map { it.nama },
                    onSelect = { jenis = it }
                )
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Draf Pengajuan", color = Dt.TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    CountBadge("${draf.size} draft")
                }
            }
            items(hasil, key = { it.id }) { p ->
                DrafCard(
                    p = p,
                    onLanjut = { onLanjutkanDraf(p.id) },
                    onHapus = { draf.removeAll { it.id == p.id } }
                )
            }
        }
    }
}

@Composable
private fun DrafCard(p: Pengajuan, onLanjut: () -> Unit, onHapus: () -> Unit) {
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
            Text(p.tanggal, color = Dt.TextMuted, fontSize = 11.sp)
        }
        Spacer(Modifier.padding(top = 8.dp))
        Text("Nominal Diajukan", color = Dt.TextMuted, fontSize = 12.sp)
        Text(p.nominalDiajukan.toRupiah(), color = Dt.TextMain, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.padding(top = 6.dp))
        Text(
            p.deskripsi,
            color = Dt.TextMuted,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.padding(top = 10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconOutlineButton(
                icon = DtIcons.ArrowRight,
                contentDescription = "Lanjutkan draf",
                tint = Dt.TealDark,
                borderColor = Dt.TealBorder,
                background = Color.White,
                onClick = onLanjut
            )
            Spacer(Modifier.width(8.dp))
            IconOutlineButton(
                icon = DtIcons.Trash,
                contentDescription = "Hapus draf",
                tint = Dt.Red,
                borderColor = Dt.RedBorder,
                background = Dt.RedSoft,
                onClick = onHapus
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun PengajuanPreview() {
    PengajuanScreen()
}