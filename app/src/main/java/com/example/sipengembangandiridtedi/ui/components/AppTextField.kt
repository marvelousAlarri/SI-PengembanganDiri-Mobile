package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BluePrimary
import com.example.sipengembangandiridtedi.ui.theme.BorderGray
import com.example.sipengembangandiridtedi.ui.theme.ErrorRed
import com.example.sipengembangandiridtedi.ui.theme.PlaceholderGray

@Composable
fun FieldLabel(text: String, required: Boolean) {
    Row {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        if (required) {
            Text(
                text = " *",
                color = ErrorRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
internal fun appFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    errorContainerColor = Color.White,
    focusedBorderColor = BluePrimary,
    unfocusedBorderColor = BorderGray,
    errorBorderColor = ErrorRed,
    focusedPlaceholderColor = PlaceholderGray,
    unfocusedPlaceholderColor = PlaceholderGray,
    errorPlaceholderColor = PlaceholderGray,
    cursorColor = BluePrimary,
)

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    required: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    error: String? = null,
) {
    Column(modifier.fillMaxWidth()) {
        FieldLabel(label, required)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            singleLine = true,
            isError = error != null,
            shape = RoundedCornerShape(12.dp),
            colors = appFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            supportingText = if (error != null) {
                { Text(error, color = ErrorRed, fontSize = 12.sp) }
            } else {
                null
            },
        )
    }
}

@Composable
fun PasswordTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    required: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    error: String? = null,
) {
    var visible by remember { mutableStateOf(false) }

    Column(modifier.fillMaxWidth()) {
        FieldLabel(label, required)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            singleLine = true,
            isError = error != null,
            shape = RoundedCornerShape(12.dp),
            colors = appFieldColors(),
            visualTransformation = if (visible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction,
            ),
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (visible) "Sembunyikan kata sandi" else "Tampilkan kata sandi",
                    )
                }
            },
            supportingText = if (error != null) {
                { Text(error, color = ErrorRed, fontSize = 12.sp) }
            } else {
                null
            },
        )
    }
}

@Composable
fun AppDropdownField(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "-- pilih --",
    required: Boolean = false,
    error: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier.fillMaxWidth()) {
        FieldLabel(label, required)
        Spacer(Modifier.height(8.dp))
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(placeholder) },
                singleLine = true,
                isError = error != null,
                shape = RoundedCornerShape(12.dp),
                colors = appFieldColors(),
                trailingIcon = {
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                },
                supportingText = if (error != null) {
                    { Text(error, color = ErrorRed, fontSize = 12.sp) }
                } else {
                    null
                },
            )

            // Lapisan transparan agar seluruh field bisa diklik untuk membuka menu
            Box(
                Modifier
                    .matchParentSize()
                    .clickable { expanded = true },
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AppTheme {
        Column(Modifier.padding(16.dp)) {
            AppTextField(label = "Email", value = "nama@ugm.ac.id", onValueChange = {}, required = true)
            Spacer(Modifier.height(16.dp))
            PasswordTextField(label = "Kata Sandi", value = "123456", onValueChange = {}, required = true)
            Spacer(Modifier.height(16.dp))
            AppDropdownField(
                label = "Program Studi",
                options = listOf("Teknologi Rekayasa Perangkat Lunak"),
                selected = "Teknologi Rekayasa Perangkat Lunak",
                onSelected = {},
                required = true,
            )
        }
    }
}
