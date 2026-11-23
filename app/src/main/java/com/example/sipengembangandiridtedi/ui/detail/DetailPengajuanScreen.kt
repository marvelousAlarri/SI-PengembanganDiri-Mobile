package com.example.sipengembangandiridtedi.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Pengajuan
import com.example.sipengembangandiridtedi.data.Profil

@Composable
fun DetailPengajuanScreen(
    detail: Pengajuan = FakeData.riwayat.first(),
    profil: Profil = FakeData.profil,
    notifCount: Int = FakeData.notifBelumDibaca,
    onBack: () -> Unit = {},
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {},
    onEdit: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        Text("Detail Pengajuan Screen")
    }
}
