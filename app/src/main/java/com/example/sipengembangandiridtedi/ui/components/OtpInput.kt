package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BluePrimary

@Composable
fun OtpInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = { new ->
            if (new.length <= length && new.all { it.isDigit() }) onValueChange(new)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        cursorBrush = SolidColor(Color.Transparent),
        textStyle = TextStyle(color = Color.Transparent),
        decorationBox = { innerTextField ->
            Box(Modifier.fillMaxWidth()) {
                // Field asli disembunyikan, hanya dipakai untuk menerima ketikan
                Box(Modifier.size(1.dp)) { innerTextField() }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            focusRequester.requestFocus()
                            keyboard?.show()
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    repeat(length) { index ->
                        val char = value.getOrNull(index)?.toString().orEmpty()
                        val isActive = index == value.length

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(0.8f)
                                .border(
                                    width = if (isActive) 2.dp else 1.5.dp,
                                    color = BluePrimary,
                                    shape = RoundedCornerShape(12.dp),
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = char,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun OtpInputPreview() {
    AppTheme {
        Box(Modifier.padding(16.dp)) {
            OtpInput(value = "1234", onValueChange = {})
        }
    }
}
