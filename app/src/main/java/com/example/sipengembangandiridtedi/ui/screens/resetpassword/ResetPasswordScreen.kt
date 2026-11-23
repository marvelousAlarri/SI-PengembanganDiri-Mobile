package com.example.sipengembangandiridtedi.ui.screens.resetpassword

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.components.AuthCard
import com.example.sipengembangandiridtedi.ui.components.AuthScaffold
import com.example.sipengembangandiridtedi.ui.components.PasswordTextField
import com.example.sipengembangandiridtedi.ui.components.PrimaryButton
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.TextSecondary
import com.example.sipengembangandiridtedi.util.MIN_PASSWORD
import com.example.sipengembangandiridtedi.util.isValidPassword

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    AuthScaffold(onBack = onBack) {
        AuthCard(rounded = true) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = "Reset Kata Sandi",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Masukkan kata sandi terbaru yang lebih kuat dan sesuai dengan ketentuan!",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = TextSecondary,
                )

                Spacer(Modifier.height(20.dp))

                PasswordTextField(
                    label = "Kata Sandi Baru",
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    required = true,
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
                    required = true,
                    imeAction = ImeAction.Done,
                    error = confirmError,
                )

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    text = "Perbarui",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        passwordError = if (isValidPassword(password)) null else "Kata sandi minimal $MIN_PASSWORD karakter."
                        confirmError = if (confirm == password && confirm.isNotEmpty()) null else "Konfirmasi tidak sama."
                        if (passwordError == null && confirmError == null) onDone()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun ResetPasswordScreenPreview() {
    AppTheme { ResetPasswordScreen(onBack = {}, onDone = {}) }
}
