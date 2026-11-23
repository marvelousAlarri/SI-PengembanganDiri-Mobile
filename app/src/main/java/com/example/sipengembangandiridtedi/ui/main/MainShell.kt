package com.example.sipengembangandiridtedi.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.ui.beranda.BerandaScreen
import com.example.sipengembangandiridtedi.ui.components.AppBottomBar
import com.example.sipengembangandiridtedi.ui.components.FabBuatPengajuan
import com.example.sipengembangandiridtedi.ui.components.MainTab
import com.example.sipengembangandiridtedi.ui.components.ProfileBottomSheet
import com.example.sipengembangandiridtedi.ui.panduan.PanduanScreen
import com.example.sipengembangandiridtedi.ui.pengajuan.PengajuanScreen
import com.example.sipengembangandiridtedi.ui.riwayat.RiwayatScreen
import com.example.sipengembangandiridtedi.ui.theme.Dt

/**
 * Kerangka halaman dosen: 4 tab (Beranda, Pengajuan, Riwayat, Panduan) + bottom bar + FAB.
 * Panggil dari AppNavHost setelah login berhasil.
 */
@Composable
fun MainShell(onLogout: () -> Unit) {
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val tab = MainTab.values().firstOrNull { it.route == route }
    var showProfile by remember { mutableStateOf(false) }
    val profil = FakeData.profil          // TODO API: GET /api/me

    fun goTab(t: MainTab) {
        nav.navigate(t.route) {
            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = Dt.Bg,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = { AppBottomBar(selected = tab, onSelect = { goTab(it) }) },
        floatingActionButton = {
            if (tab == MainTab.Beranda || tab == MainTab.Pengajuan) {
                FabBuatPengajuan(onClick = { /* Tahap 2: buka form Buat Pengajuan */ })
            }
        }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = MainTab.Beranda.route,
            modifier = Modifier.padding(inner)
        ) {
            composable(MainTab.Beranda.route) {
                BerandaScreen(
                    profil = profil,
                    onAvatar = { showProfile = true },
                    onLihatSemua = { goTab(MainTab.Riwayat) }
                )
            }
            composable(MainTab.Pengajuan.route) {
                PengajuanScreen(profil = profil, onAvatar = { showProfile = true })
            }
            composable(MainTab.Riwayat.route) {
                RiwayatScreen(profil = profil, onAvatar = { showProfile = true })
            }
            composable(MainTab.Panduan.route) {
                PanduanScreen(profil = profil, onAvatar = { showProfile = true })
            }
        }
    }

    if (showProfile) {
        ProfileBottomSheet(
            profil = profil,
            onDismiss = { showProfile = false },
            onLogout = {
                showProfile = false
                onLogout()
            }
        )
    }
}