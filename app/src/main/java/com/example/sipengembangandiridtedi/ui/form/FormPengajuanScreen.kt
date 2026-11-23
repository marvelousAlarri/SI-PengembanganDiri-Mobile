package com.example.sipengembangandiridtedi.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Pengajuan
import com.example.sipengembangandiridtedi.data.Profil

enum class FormMode { BUAT, DRAF, EDIT }

@Composable
fun FormPengajuanScreen(
    mode: FormMode = FormMode.BUAT,
    profil: Profil = FakeData.profil,
    awal: Pengajuan? = null,
    onClose: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        Text("Form Pengajuan Screen")
    }
}
