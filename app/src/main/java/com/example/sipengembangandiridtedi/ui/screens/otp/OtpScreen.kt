package com.example.sipengembangandiridtedi.ui.screens.otp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.components.AuthCard
import com.example.sipengembangandiridtedi.ui.components.AuthScaffold
import com.example.sipengembangandiridtedi.ui.components.OtpInput
import com.example.sipengembangandiridtedi.ui.components.PrimaryButton
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.ErrorRed
import com.example.sipengembangandiridtedi.ui.theme.TextSecondary

private const val OTP_LENGTH = 6

@Composable
fun OtpScreen(
    onBack: () -> Unit,
    onConfirmed: () -> Unit,
    email: String = "caysarara@gmail.com",
) {
    var code by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AuthScaffold(onBack = onBack) {
        AuthCard(rounded = true) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Verifikasi Kode",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Masukkan kode OTP yang telah dikirimkan melalui email ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(email) }
                        append(". Jangan sebarluaskan kode kepada siapapun!")
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = TextSecondary,
                )

                Spacer(Modifier.height(20.dp))

                OtpInput(
                    value = code,
                    onValueChange = {
                        code = it
                        error = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    length = OTP_LENGTH,
                )

                error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(text = it, color = ErrorRed, fontSize = 12.sp)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Kode akan kadaluwarsa dalam ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("2 jam") }
                    },
                    fontSize = 13.sp,
                    color = TextSecondary,
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Kirim Ulang Kode OTP",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* TODO: kirim ulang OTP saat backend siap */ },
                )

                Spacer(Modifier.height(12.dp))

                PrimaryButton(
                    text = "Konfirmasi",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (code.length == OTP_LENGTH) {
                            onConfirmed()
                        } else {
                            error = "Masukkan $OTP_LENGTH digit kode OTP."
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun OtpScreenPreview() {
    AppTheme { OtpScreen(onBack = {}, onConfirmed = {}) }
}
