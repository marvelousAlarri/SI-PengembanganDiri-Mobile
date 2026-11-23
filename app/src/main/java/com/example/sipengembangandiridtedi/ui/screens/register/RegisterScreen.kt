package com.example.sipengembangandiridtedi.ui.screens.register

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
import com.example.sipengembangandiridtedi.ui.components.AppDropdownField
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

// Contoh data. Ganti dengan daftar program studi yang sebenarnya.
private val programStudiOptions = listOf(
    "Teknologi Rekayasa Perangkat Lunak",
    "Teknologi Rekayasa Internet",
    "Teknologi Rekayasa Elektro",
)

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit,
    onRegistered: () -> Unit,
) {
    var nama by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var prodi by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    var namaError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var prodiError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    AuthScaffold(onBack = onBack) {
        AuthCard {
            AuthHeader(
                title = "Selamat Datang",
                subtitle = "Buat akun untuk mengajukan dan memantau dana pengembangan diri secara lebih terstruktur.",
            )

            Column(Modifier.padding(24.dp)) {
                AppTextField(
                    label = "Nama Lengkap",
                    value = nama,
                    onValueChange = {
                        nama = it
                        namaError = null
                    },
                    placeholder = "Masukkan nama lengkap",
                    error = namaError,
                )

                Spacer(Modifier.height(14.dp))

                AppTextField(
                    label = "Email",
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    placeholder = "nama@ugm.ac.id",
                    keyboardType = KeyboardType.Email,
                    error = emailError,
                )

                Spacer(Modifier.height(14.dp))

                AppDropdownField(
                    label = "Program Studi",
                    options = programStudiOptions,
                    selected = prodi,
                    onSelected = {
                        prodi = it
                        prodiError = null
                    },
                    error = prodiError,
                )

                Spacer(Modifier.height(14.dp))

                PasswordTextField(
                    label = "Kata Sandi",
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    placeholder = "Kata sandi minimal $MIN_PASSWORD karakter",
                    error = passwordError,
                )

                Spacer(Modifier.height(14.dp))

                PasswordTextField(
                    label = "Konfirmasi Kata Sandi",
                    value = confirm,
                    onValueChange = {
                        confirm = it
                        confirmError = null
                    },
                    placeholder = "Kata sandi minimal $MIN_PASSWORD karakter",
                    imeAction = ImeAction.Done,
                    error = confirmError,
                )

                Spacer(Modifier.height(18.dp))

                PrimaryButton(
                    text = "Daftar",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        namaError = if (nama.isBlank()) "Isi nama lengkap." else null
                        emailError = if (isValidEmail(email)) null else "Masukkan email yang valid."
                        prodiError = if (prodi.isBlank()) "Pilih program studi." else null
                        passwordError = if (isValidPassword(password)) null else "Kata sandi minimal $MIN_PASSWORD karakter."
                        confirmError = if (confirm == password && confirm.isNotEmpty()) null else "Konfirmasi tidak sama."

                        val valid = listOf(namaError, emailError, prodiError, passwordError, confirmError)
                            .all { it == null }
                        if (valid) onRegistered()
                    },
                )

                Spacer(Modifier.height(18.dp))
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
                            text = "Sudah punya akun?",
                            fontSize = 13.sp,
                            color = TextSecondary,
                        )
                        Text(
                            text = "Login dengan akun",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueDark,
                            modifier = Modifier.clickable(onClick = onLogin),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(onBack = {}, onLogin = {}, onRegistered = {})
    }
}
