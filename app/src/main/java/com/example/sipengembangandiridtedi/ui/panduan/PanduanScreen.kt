package com.example.sipengembangandiridtedi.ui.panduan

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.FaqItem
import com.example.sipengembangandiridtedi.data.JenisKegiatan
import com.example.sipengembangandiridtedi.data.Profil
import com.example.sipengembangandiridtedi.ui.components.AccordionCard
import com.example.sipengembangandiridtedi.ui.components.AppTopBar
import com.example.sipengembangandiridtedi.ui.components.DtDivider
import com.example.sipengembangandiridtedi.ui.components.DtOutlineButton
import com.example.sipengembangandiridtedi.ui.components.SearchField
import com.example.sipengembangandiridtedi.ui.components.dtCard
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons
import com.example.sipengembangandiridtedi.util.inisial

@Composable
fun PanduanScreen(
    profil: Profil = FakeData.profil,
    jenisKegiatan: List<JenisKegiatan> = FakeData.jenisKegiatan,   // TODO API: GET /api/categories
    faq: List<FaqItem> = FakeData.faq,                              // TODO API: belum ada endpoint FAQ
    notifCount: Int = FakeData.notifBelumDibaca,
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {}
) {
    var tab by rememberSaveable { mutableStateOf(0) }            // 0 = Panduan, 1 = FAQ
    var jenisTerpilih by remember { mutableStateOf<JenisKegiatan?>(null) }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(
            title = if (tab == 0) "Panduan" else "FAQ",
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
                    Text(
                        if (tab == 0) "Panduan Pengembangan Diri" else "Pertanyaan yang Sering Diajukan",
                        color = Dt.TextMain,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        if (tab == 0) "Pelajari ketentuan dan persyaratan pengajuan dana bantuan pengembangan diri."
                        else "Temukan jawaban seputar pengajuan dana bantuan pengembangan diri.",
                        color = Dt.TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TabPill("Panduan", tab == 0) { tab = 0 }
                    TabPill("FAQ", tab == 1) { tab = 1 }
                }
            }
            if (tab == 0) {
                item { PanduanContent(jenisKegiatan) { jenisTerpilih = it } }
            } else {
                item { FaqContent(faq) }
            }
        }
    }

    jenisTerpilih?.let { JenisSheet(it) { jenisTerpilih = null } }
}

@Composable
private fun TabPill(text: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(50)
    Text(
        text,
        color = if (selected) Color.White else Dt.TextMain,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .clip(shape)
            .background(if (selected) Dt.Blue else Dt.Surface)
            .border(1.dp, if (selected) Dt.Blue else Dt.Border, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

/* ------------------------------ PANDUAN ------------------------------ */

@Composable
private fun PanduanContent(jenis: List<JenisKegiatan>, onJenisClick: (JenisKegiatan) -> Unit) {
    var open by rememberSaveable { mutableStateOf(1) }   // 0 = semua tertutup
    fun toggle(n: Int) { open = if (open == n) 0 else n }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AccordionCard(1, "Ketentuan Umum", open == 1, { toggle(1) }) {
            Column(Modifier.dtCard(12.dp, color = Dt.Bg).padding(14.dp)) {
                Text("Penerima bantuan", color = Dt.TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Bantuan diberikan kepada dosen Departemen Teknik Elektro dan Informatika, Sekolah Vokasi, Universitas Gadjah Mada yang berstatus aktif.",
                    color = Dt.TextMuted,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(12.dp))
                Text("Ketersediaan dana", color = Dt.TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Dana bantuan bersifat terbatas sesuai dengan ketersediaan dana di Departemen Teknik Elektro dan Informatika.",
                    color = Dt.TextMuted,
                    fontSize = 13.sp
                )
            }
        }
        AccordionCard(2, "Persyaratan dan Ketentuan bagi Pengusul", open == 2, { toggle(2) }) {
            // Teks placeholder: ganti dengan isi asli dari desain/dokumen.
            Column(Modifier.dtCard(12.dp, color = Dt.Bg).padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(
                    "Pengusul adalah dosen aktif Departemen Teknik Elektro dan Informatika.",
                    "Nominal pengajuan tidak melebihi sisa dana pengembangan diri.",
                    "Seluruh dokumen persyaratan diunggah dalam format PDF (maks. 5 MB)."
                ).forEach { Text("•  $it", color = Dt.TextMuted, fontSize = 13.sp) }
            }
        }
        AccordionCard(3, "Jenis Pengembangan Diri", open == 3, { toggle(3) }) {
            Text(
                "Pilih jenis pengembangan diri untuk melihat deskripsi dokumen yang diperlukan.",
                color = Dt.TextMuted,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                jenis.forEach { j ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Dt.BlueSoft)
                            .clickable { onJenisClick(j) }
                            .padding(horizontal = 14.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            j.nama,
                            color = Dt.TextMain,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(DtIcons.ChevronRight, null, tint = Dt.TextMain, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JenisSheet(jenis: JenisKegiatan, onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Dt.Surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                "Jenis Pengembangan Diri",
                color = Dt.Blue,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50))
                    .background(Dt.BlueSoft)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(40.dp).clip(CircleShape).background(Dt.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(DtIcons.CheckCircle, null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(12.dp))
                Text(jenis.nama, color = Dt.TextMain, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(14.dp))
            Column(Modifier.fillMaxWidth().dtCard(12.dp).padding(14.dp)) {
                Text("Deskripsi Singkat", color = Dt.TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(jenis.deskripsi, color = Dt.TextMuted, fontSize = 13.sp)
            }
            Spacer(Modifier.height(12.dp))
            Column(Modifier.fillMaxWidth().dtCard(12.dp, color = Dt.BlueTint, borderColor = Dt.BlueBorder).padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(DtIcons.Folder, null, tint = Dt.Blue, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Dokumen Persyaratan", color = Dt.TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                jenis.dokumen.forEachIndexed { i, d ->
                    if (i > 0) DtDivider(color = Dt.BlueBorder)
                    Row(Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(DtIcons.File, null, tint = Dt.Blue, modifier = Modifier.size(16.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(d, color = Dt.TextMain, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            DtOutlineButton("Tutup", onClick = onDismiss, modifier = Modifier.fillMaxWidth())
        }
    }
}

/* -------------------------------- FAQ -------------------------------- */

@Composable
private fun FaqContent(faq: List<FaqItem>) {
    var open by rememberSaveable { mutableStateOf(2) }
    var query by rememberSaveable { mutableStateOf("") }
    var expandedIndex by remember { mutableStateOf(-1) }
    fun toggle(n: Int) { open = if (open == n) 0 else n }

    val hasil = faq.filter { query.isBlank() || it.tanya.contains(query, ignoreCase = true) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AccordionCard(1, "Cari Pertanyaan", open == 1, { toggle(1) }) {
            SearchField(query, { query = it }, "Ketik kata kunci...")
        }
        AccordionCard(2, "Daftar FAQ", open == 2, { toggle(2) }) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (hasil.isEmpty()) {
                    Text("Pertanyaan tidak ditemukan.", color = Dt.TextMuted, fontSize = 13.sp, modifier = Modifier.padding(8.dp))
                }
                hasil.forEachIndexed { i, item ->
                    val expanded = expandedIndex == i
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .dtCard(12.dp)
                            .clickable { expandedIndex = if (expanded) -1 else i }
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                item.tanya,
                                color = Dt.TextMain,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                if (expanded) DtIcons.ChevronUp else DtIcons.ChevronDown,
                                null,
                                tint = Dt.TextMain,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        if (expanded) {
                            Spacer(Modifier.height(6.dp))
                            Text(item.jawab, color = Dt.TextMuted, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun PanduanPreview() {
    PanduanScreen()
}