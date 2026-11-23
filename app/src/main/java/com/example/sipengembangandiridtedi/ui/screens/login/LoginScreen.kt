package com.example.sipengembangandiridtedi.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.components.AppTextField
import com.example.sipengembangandiridtedi.ui.components.AuthCard
import com.example.sipengembangandiridtedi.ui.components.AuthHeader
import com.example.sipengembangandiridtedi.ui.components.AuthScaffold
import com.example.sipengembangandiridtedi.ui.components.PasswordTextField
import com.example.sipengembangandiridtedi.ui.components.PrimaryButton
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BlueDark
import com.example.sipengembangandiridtedi.ui.theme.DividerGray
import com.example.sipengembangandiridtedi.ui.theme.TextSecondary
import com.example.sipengembangandiridtedi.util.MIN_PASSWORD
import com.example.sipengembangandiridtedi.util.isValidEmail
import com.example.sipengembangandiridtedi.util.isValidPassword

@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onRegister: () -> Unit,
    onForgot: () -> Unit,
    onSuccess: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    AuthScaffold(onBack = onBack) {
        AuthCard {
            AuthHeader(
                title = "Selamat Datang",
                subtitle = "Masuk ke Sistem Pengembangan Diri DTEDI.",
            )

            Column(Modifier.padding(24.dp)) {
                Text(
                    text = "Masuk ke Akun Anda",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Gunakan email UGM Anda untuk verifikasi identitas sivitas akademika.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = TextSecondary,
                )

                Spacer(Modifier.height(20.dp))

                AppTextField(
                    label = "Email",
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    placeholder = "nama@ugm.ac.id",
                    required = true,
                    keyboardType = KeyboardType.Email,
                    error = emailError,
                )

                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    label = "Kata Sandi",
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    placeholder = "Kata sandi minimal $MIN_PASSWORD karakter",
                    required = true,
                    imeAction = ImeAction.Done,
                    error = passwordError,
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Lupa kata sandi?",
                    fontSize = 14.sp,
                    modifier = Modifier.clickable(onClick = onForgot),
                )

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    text = "Masuk",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        emailError = if (isValidEmail(email)) null else "Masukkan email yang valid."
                        passwordError = if (isValidPassword(password)) null else "Kata sandi minimal $MIN_PASSWORD karakter."
                        if (emailError == null && passwordError == null) onSuccess()
                    },
                )

                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = DividerGray)
                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Belum punya akun?",
                            fontSize = 13.sp,
                            color = TextSecondary,
                        )
                        Text(
                            text = "Daftar sebagai Dosen",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueDark,
                            modifier = Modifier.clickable(onClick = onRegister),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(onBack = {}, onRegister = {}, onForgot = {}, onSuccess = {})
    }
}
