package com.example.sipengembangandiridtedi.ui.notifikasi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Notifikasi
import com.example.sipengembangandiridtedi.data.Profil

@Composable
fun NotifikasiScreen(
    daftar: List<Notifikasi> = FakeData.notifikasi,
    profil: Profil = FakeData.profil,
    onBack: () -> Unit = {},
    onAvatar: () -> Unit = {},
    onTandaiDibaca: (Int) -> Unit = {},
    onTandaiSemua: () -> Unit = {},
    onOpenPengajuan: (Int) -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        Text("Notifikasi Screen")
    }
}
