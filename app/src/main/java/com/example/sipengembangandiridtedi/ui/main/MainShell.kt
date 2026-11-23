package com.example.sipengembangandiridtedi.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sipengembangandiridtedi.data.FakeData
import com.example.sipengembangandiridtedi.data.Notifikasi
import com.example.sipengembangandiridtedi.ui.beranda.BerandaScreen
import com.example.sipengembangandiridtedi.ui.components.AppBottomBar
import com.example.sipengembangandiridtedi.ui.components.FabBuatPengajuan
import com.example.sipengembangandiridtedi.ui.components.MainTab
import com.example.sipengembangandiridtedi.ui.components.ProfileBottomSheet
import com.example.sipengembangandiridtedi.ui.detail.DetailPengajuanScreen
import com.example.sipengembangandiridtedi.ui.form.FormMode
import com.example.sipengembangandiridtedi.ui.form.FormPengajuanScreen
import com.example.sipengembangandiridtedi.ui.notifikasi.NotifikasiScreen
import com.example.sipengembangandiridtedi.ui.panduan.PanduanScreen
import com.example.sipengembangandiridtedi.ui.pengajuan.PengajuanScreen
import com.example.sipengembangandiridtedi.ui.riwayat.RiwayatScreen
import com.example.sipengembangandiridtedi.ui.theme.Dt

/** Rute tambahan di dalam MainShell (di luar 4 tab). */
private object ShellRoute {
    const val Notifikasi = "notifikasi"
    const val Detail = "detail/{id}"
    const val FormBuat = "form/buat"
    const val FormDraf = "form/draf/{id}"
    const val FormEdit = "form/edit/{id}"

    fun detail(id: Int) = "detail/$id"
    fun formDraf(id: Int) = "form/draf/$id"
    fun formEdit(id: Int) = "form/edit/$id"
}

/**
 * Kerangka halaman dosen: 4 tab (Beranda, Pengajuan, Riwayat, Panduan) + bottom bar + FAB,
 * ditambah layar Notifikasi, Detail Pengajuan, dan Form (Buat / Draf / Edit).
 * Panggil dari AppNavHost setelah login berhasil.
 */
@Composable
fun MainShell(onLogout: () -> Unit) {
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    var showProfile by remember { mutableStateOf(false) }
    val profil = FakeData.profil          // TODO API: GET /api/me

    // Notifikasi disimpan di state supaya tombol "tandai dibaca" langsung terlihat.
    // TODO API: belum ada endpoint notifikasi di backend.
    val notifikasi = remember { mutableStateListOf<Notifikasi>().apply { addAll(FakeData.notifikasi) } }
    val notifCount = notifikasi.count { !it.dibaca }

    // Tab yang menyala di bottom bar: Detail dianggap bagian dari Riwayat, Notifikasi tidak menyalakan tab.
    val tabAktif = MainTab.values().firstOrNull { it.route == route }
    val tabSorot = tabAktif ?: if (route == ShellRoute.Detail) MainTab.Riwayat else null
    val tampilkanBottomBar = route?.startsWith("form") != true

    fun goTab(t: MainTab) {
        nav.navigate(t.route) {
            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun bukaNotifikasi() = nav.navigate(ShellRoute.Notifikasi) { launchSingleTop = true }
    fun bukaDetail(id: Int) = nav.navigate(ShellRoute.detail(id))

    Scaffold(
        containerColor = Dt.Bg,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (tampilkanBottomBar) AppBottomBar(selected = tabSorot, onSelect = { goTab(it) })
        },
        floatingActionButton = {
            if (tabAktif == MainTab.Beranda || tabAktif == MainTab.Pengajuan) {
                FabBuatPengajuan(onClick = { nav.navigate(ShellRoute.FormBuat) })
            }
        }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = MainTab.Beranda.route,
            modifier = Modifier.padding(inner)
        ) {
            /* ------------------------------ 4 tab ------------------------------ */
            composable(MainTab.Beranda.route) {
                BerandaScreen(
                    profil = profil,
                    notifCount = notifCount,
                    onBell = { bukaNotifikasi() },
                    onAvatar = { showProfile = true },
                    onLihatSemua = { goTab(MainTab.Riwayat) },
                    onItemClick = { bukaDetail(it) }
                )
            }
            composable(MainTab.Pengajuan.route) {
                PengajuanScreen(
                    profil = profil,
                    notifCount = notifCount,
                    onBell = { bukaNotifikasi() },
                    onAvatar = { showProfile = true },
                    onLanjutkanDraf = { nav.navigate(ShellRoute.formDraf(it)) }
                )
            }
            composable(MainTab.Riwayat.route) {
                RiwayatScreen(
                    profil = profil,
                    notifCount = notifCount,
                    onBell = { bukaNotifikasi() },
                    onAvatar = { showProfile = true },
                    onDetail = { bukaDetail(it) }
                )
            }
            composable(MainTab.Panduan.route) {
                PanduanScreen(
                    profil = profil,
                    notifCount = notifCount,
                    onBell = { bukaNotifikasi() },
                    onAvatar = { showProfile = true }
                )
            }

            /* --------------------------- layar tambahan --------------------------- */
            composable(ShellRoute.Notifikasi) {
                NotifikasiScreen(
                    daftar = notifikasi,
                    profil = profil,
                    onBack = { nav.popBackStack() },
                    onAvatar = { showProfile = true },
                    onTandaiDibaca = { id ->
                        val i = notifikasi.indexOfFirst { it.id == id }
                        if (i >= 0) notifikasi[i] = notifikasi[i].copy(dibaca = true)
                    },
                    onTandaiSemua = {
                        notifikasi.indices.forEach { i ->
                            if (!notifikasi[i].dibaca) notifikasi[i] = notifikasi[i].copy(dibaca = true)
                        }
                    },
                    onOpenPengajuan = { bukaDetail(it) }
                )
            }

            composable(
                ShellRoute.Detail,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { e ->
                val id = e.arguments?.getInt("id") ?: -1
                val detail = FakeData.detail(id)      // TODO API: GET /api/submissions/{id}
                if (detail != null) {
                    DetailPengajuanScreen(
                        detail = detail,
                        profil = profil,
                        notifCount = notifCount,
                        onBack = { nav.popBackStack() },
                        onBell = { bukaNotifikasi() },
                        onAvatar = { showProfile = true },
                        onEdit = { nav.navigate(ShellRoute.formEdit(id)) }
                    )
                }
            }

            composable(ShellRoute.FormBuat) {
                FormPengajuanScreen(
                    mode = FormMode.BUAT,
                    profil = profil,
                    onClose = { nav.popBackStack() },
                    onSubmit = { goTab(MainTab.Riwayat) }
                )
            }

            composable(
                ShellRoute.FormDraf,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { e ->
                val id = e.arguments?.getInt("id") ?: -1
                FormPengajuanScreen(
                    mode = FormMode.DRAF,
                    profil = profil,
                    awal = FakeData.draf.firstOrNull { it.id == id },
                    onClose = { nav.popBackStack() },
                    onSubmit = { goTab(MainTab.Riwayat) }
                )
            }

            composable(
                ShellRoute.FormEdit,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { e ->
                val id = e.arguments?.getInt("id") ?: -1
                FormPengajuanScreen(
                    mode = FormMode.EDIT,
                    profil = profil,
                    awal = FakeData.riwayat.firstOrNull { it.id == id },
                    onClose = { nav.popBackStack() },
                    onSubmit = { goTab(MainTab.Riwayat) }
                )
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
