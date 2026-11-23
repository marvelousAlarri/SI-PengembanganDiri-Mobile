package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.Profil
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons
import com.example.sipengembangandiridtedi.util.inisial

@Composable
fun AvatarCircle(initials: String, size: androidx.compose.ui.unit.Dp = 40.dp, modifier: Modifier = Modifier) {
    Box(
        modifier.size(size).clip(CircleShape).background(Dt.TealSoft),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, color = Dt.TealDark, fontSize = (size.value * 0.32f).sp, fontWeight = FontWeight.Bold)
    }
}

/**
 * Top app bar: logo topi wisuda (atau tombol kembali) + judul + lonceng ber-badge + avatar.
 */
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    notifCount: Int = 0,
    initials: String = "",
    onBell: () -> Unit = {},
    onAvatar: () -> Unit = {}
) {
    Column(modifier.fillMaxWidth().background(Dt.Surface)) {
        Row(
            Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBack) {
                Box(
                    Modifier.size(40.dp).clip(CircleShape).clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(DtIcons.ArrowLeft, "Kembali", tint = Dt.TextMain, modifier = Modifier.size(22.dp))
                }
            } else {
                Box(
                    Modifier.size(40.dp).clip(CircleShape).background(Dt.BlueSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(DtIcons.Cap, null, tint = Dt.Blue, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(
                title,
                color = Dt.TextMain,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Box(
                Modifier.size(40.dp).clip(CircleShape).clickable(onClick = onBell),
                contentAlignment = Alignment.Center
            ) {
                Icon(DtIcons.Bell, "Notifikasi", tint = Dt.TextMain, modifier = Modifier.size(22.dp))
                if (notifCount > 0) {
                    Box(
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 6.dp, end = 6.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Dt.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(notifCount.toString(), color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
            AvatarCircle(initials, 40.dp, Modifier.clickable(onClick = onAvatar))
        }
        DtDivider()
    }
}

enum class MainTab(val route: String, val label: String, val icon: ImageVector) {
    Beranda("beranda", "Beranda", DtIcons.Home),
    Pengajuan("pengajuan", "Pengajuan", DtIcons.File),
    Riwayat("riwayat", "Riwayat", DtIcons.History),
    Panduan("panduan", "Panduan", DtIcons.Book)
}

@Composable
fun AppBottomBar(selected: MainTab?, onSelect: (MainTab) -> Unit) {
    Column(Modifier.fillMaxWidth().background(Dt.Surface)) {
        DtDivider()
        Row(
            Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            MainTab.values().forEach { tab ->
                val active = tab == selected
                Column(
                    Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelect(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .size(width = 64.dp, height = 32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (active) Dt.BlueSoft else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            tab.icon,
                            tab.label,
                            tint = if (active) Dt.Blue else Dt.TextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        tab.label,
                        fontSize = 11.sp,
                        fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
                        color = if (active) Dt.Blue else Dt.TextMuted
                    )
                }
            }
        }
    }
}

/** Bottom sheet profil (muncul saat avatar ditekan): nama, role, tombol Keluar. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(profil: Profil, onDismiss: () -> Unit, onLogout: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Dt.Surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarCircle(profil.nama.inisial(), 48.dp)
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(profil.nama, color = Dt.TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(profil.role, color = Dt.TextMuted, fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(16.dp))
            DtDivider()
            Spacer(Modifier.height(16.dp))
            val shape = RoundedCornerShape(12.dp)
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(Dt.RedSoft)
                    .border(1.dp, Color(0xFFFECACA), shape)
                    .clickable(onClick = onLogout)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(DtIcons.LogOut, null, tint = Dt.Red, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(12.dp))
                Text("Keluar", color = Dt.Red, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}