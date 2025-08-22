package com.vasudev.dialer.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun GlowingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    glowColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(150),
        label = "glow_button_scale"
    )
    
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.8f else 0.4f,
        animationSpec = tween(150),
        label = "glow_alpha"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = glowAlpha),
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surface
                    ),
                    radius = 100f
                )
            )
            .border(
                width = 2.dp,
                color = glowColor,
                shape = shape
            )
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            },
        contentAlignment = Alignment.Center,
        content = content
    )
}
