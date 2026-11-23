package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.data.StatusPengajuan
import com.example.sipengembangandiridtedi.ui.theme.Dt
import com.example.sipengembangandiridtedi.ui.theme.DtIcons

/** Kartu putih ber-border tipis. */
fun Modifier.dtCard(
    radius: Dp = 16.dp,
    color: Color = Dt.Surface,
    borderColor: Color = Dt.Border
): Modifier {
    val shape = RoundedCornerShape(radius)
    return this.clip(shape).background(color, shape).border(1.dp, borderColor, shape)
}

@Composable
fun DtDivider(modifier: Modifier = Modifier, color: Color = Dt.Border) {
    Box(modifier.fillMaxWidth().height(1.dp).background(color))
}

/** Pil status: Diperiksa (kuning), Disetujui (hijau), Diajukan (biru), Ditolak (merah), dst. */
@Composable
fun StatusChip(status: StatusPengajuan, modifier: Modifier = Modifier) {
    val (bg, fg) = when (status) {
        StatusPengajuan.DIPERIKSA -> Color(0xFFFDE68A) to Color(0xFF1F2937)
        StatusPengajuan.DISETUJUI -> Color(0xFFBBF7D0) to Color(0xFF14532D)
        StatusPengajuan.DIAJUKAN -> Color(0xFFBFDBFE) to Color(0xFF1E3A8A)
        StatusPengajuan.DITOLAK -> Color(0xFFFECACA) to Color(0xFF7F1D1D)
        StatusPengajuan.REVISI -> Color(0xFFFED7AA) to Color(0xFF7C2D12)   // asumsi warna
        StatusPengajuan.SELESAI -> Color(0xFFA7F3D0) to Color(0xFF065F46)  // asumsi warna
    }
    Text(
        text = status.label,
        color = fg,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(bg)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

/** Badge hijau-tosca kecil: "4 draft", "4 pengajuan". */
@Composable
fun CountBadge(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Dt.Teal)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp, color = Dt.TextMain),
        cursorBrush = SolidColor(Dt.Blue),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .dtCard(12.dp),
        decorationBox = { inner ->
            Row(
                Modifier.fillMaxSize().padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(DtIcons.Search, null, tint = Dt.Blue, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(placeholder, color = Dt.TextMuted, fontSize = 14.sp)
                    }
                    inner()
                }
            }
        }
    )
}

/** Dropdown berbentuk pil: "Semua Jenis Kegiatan ⌄". */
@Composable
fun FilterPill(
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var open by remember { mutableStateOf(false) }
    Box(modifier) {
        Row(
            Modifier
                .height(40.dp)
                .dtCard(20.dp)
                .clickable { open = true }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                selected,
                color = Dt.TextMain,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(Modifier.width(8.dp))
            Icon(DtIcons.ChevronDown, null, tint = Dt.TextMain, modifier = Modifier.size(18.dp))
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt, fontSize = 13.sp) },
                    onClick = { onSelect(opt); open = false }
                )
            }
        }
    }
}

/** Tombol outline biru, mis. "Detail →" atau "Tutup". */
@Composable
fun DtOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null
) {
    val shape = RoundedCornerShape(10.dp)
    Row(
        modifier
            .height(44.dp)
            .clip(shape)
            .background(Dt.Surface)
            .border(1.dp, Dt.BlueBorder, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text, color = Dt.Blue, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        if (trailingIcon != null) {
            Spacer(Modifier.width(8.dp))
            Icon(trailingIcon, null, tint = Dt.Blue, modifier = Modifier.size(16.dp))
        }
    }
}

/** Tombol ikon persegi bergaris (panah hijau / hapus merah di kartu draf). */
@Composable
fun IconOutlineButton(
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    borderColor: Color,
    background: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier
            .size(width = 44.dp, height = 40.dp)
            .clip(shape)
            .background(background)
            .border(1.dp, borderColor, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription, tint = tint, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun FabBuatPengajuan(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = Dt.Blue,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(DtIcons.Plus, null, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Text("Buat Pengajuan", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

/** Kartu accordion bernomor (Panduan & FAQ). */
@Composable
fun AccordionCard(
    number: Int,
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier.fillMaxWidth().dtCard(16.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(28.dp).clip(CircleShape).background(Dt.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(number.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(12.dp))
            Text(
                title,
                color = Dt.TextMain,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                if (expanded) DtIcons.ChevronUp else DtIcons.ChevronDown,
                null,
                tint = Dt.TextMain,
                modifier = Modifier.size(20.dp)
            )
        }
        if (expanded) {
            Column(Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp), content = content)
        }
    }
}