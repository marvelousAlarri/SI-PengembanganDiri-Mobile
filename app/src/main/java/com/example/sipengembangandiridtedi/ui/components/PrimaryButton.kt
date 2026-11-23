package com.example.sipengembangandiridtedi.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipengembangandiridtedi.ui.theme.AppTheme
import com.example.sipengembangandiridtedi.ui.theme.BluePrimary

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = BluePrimary,
    shape: Shape = RoundedCornerShape(14.dp),
    trailingIcon: ImageVector? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp,
        ),
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        if (trailingIcon != null) {
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    AppTheme {
        PrimaryButton(
            text = "Masuk",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
    }
}
