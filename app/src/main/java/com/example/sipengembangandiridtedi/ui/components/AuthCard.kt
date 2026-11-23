package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BlueDark
import com.example.sipengembangandiridtedi.ui.theme.CardBg

@Composable
fun AuthCard(
    modifier: Modifier = Modifier,
    rounded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = if (rounded) RoundedCornerShape(20.dp) else RoundedCornerShape(0.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (rounded) Modifier.shadow(8.dp, shape) else Modifier)
            .clip(shape)
            .background(CardBg),
        content = content,
    )
}

@Composable
fun AuthHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueDark)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = subtitle,
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun AuthScaffold(
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        AppTopBar(onBack = onBack)

        BoxWithConstraints(Modifier.weight(1f).fillMaxWidth()) {
            val minHeight = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = minHeight)
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                content = content,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthCardPreview() {
    AppTheme {
        AuthCard {
            AuthHeader(
                title = "Selamat Datang",
                subtitle = "Masuk ke Sistem Pengembangan Diri DTEDI.",
            )
        }
    }
}
