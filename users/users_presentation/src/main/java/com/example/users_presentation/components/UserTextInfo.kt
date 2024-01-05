package com.example.users_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.core.LocalSpacing

@Composable
fun UserTextInfo(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    style: TextStyle = MaterialTheme.typography.body1.copy(color = Color.Gray, fontWeight = FontWeight.Bold)
) {

    val spacing = LocalSpacing.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = style,
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Text(
            text = value
        )
    }
}