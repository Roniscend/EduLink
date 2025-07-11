package com.example.hello_there.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FuturisticStudyBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "study-bg")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bg-phase"
    )
    // Gradient shifts
    val gradShift = 220f * (0.6f + 0.4f * sin(phase))
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Moving animated gradient
        Canvas(Modifier.matchParentSize()) {
            drawRect(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF26235C),
                        Color(0xFF4473C5),
                        Color(0xFF1DEBC7),
                        Color(0xFFDAD9FF)
                    ),
                    start = Offset(0f, gradShift),
                    end = Offset(size.width, size.height - gradShift)
                ),
                size = size
            )
        }
        // Atom-like orbits/floating dots
        Canvas(Modifier.fillMaxSize()) {
            val cx = size.width / 2
            val cy = size.height / 2
            val orbitRadius1 = size.minDimension / 2.3f
            val orbitRadius2 = size.minDimension / 3.1f
            val bubbleCount = 7
            for (i in 0 until bubbleCount) {
                val angle = phase + i * 2 * PI.toFloat() / bubbleCount
                val r = if (i % 2 == 0) orbitRadius1 else orbitRadius2
                val dotX = cx + r * cos(angle)
                val dotY = cy + r * sin(angle)
                drawCircle(
                    color = if (i % 2 == 0) Color(0xFF40F0FF).copy(alpha = 0.14f + 0.22f * sin(angle + phase)) else Color(
                        0xFF705CFE
                    ).copy(alpha = 0.14f + 0.22f * cos(angle)),
                    radius = if (i % 2 == 0) 54f else 32f, center = Offset(dotX, dotY)
                )
            }
            // Nucleus
            drawCircle(
                Color.White.copy(alpha = 0.08f),
                radius = size.minDimension / 5.8f,
                center = Offset(cx, cy)
            )
            // Ambient smaller dots as knowledge particle effect
            for (i in 0..11) {
                val p = i * 2 * PI.toFloat() / 12 + phase / 3
                drawCircle(
                    color = Color(0xFF82EFFF).copy(alpha = 0.13f + 0.09f * sin(p + phase)),
                    radius = 12f + 7f * sin(p + phase * 0.5f),
                    center = Offset(
                        cx + (size.minDimension / 1.5f) * cos(p + phase / 2),
                        cy + (size.minDimension / 2.2f) * sin(p + phase / 2)
                    )
                )
            }
        }
    }
}
