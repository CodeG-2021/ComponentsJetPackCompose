package com.example.componentsjetpackcompose

import android.widget.Space
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random.Default.nextInt

@Composable
fun ColorSimpleAnimation() {
    var firstColor by rememberSaveable {
        mutableStateOf(false)
    }
    var showBox by rememberSaveable {
        mutableStateOf(true)
    }
    val realColor2 by animateColorAsState(
        targetValue = if (firstColor) Color.Red else Color.Yellow,
        animationSpec = tween(durationMillis = 2000),
        finishedListener = { showBox = false }
    )
    if (showBox) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = realColor2)
                .clickable { firstColor = !firstColor }
        )
    }


}

@Composable
fun SizeAnimation() {
    var smallSize by rememberSaveable { mutableStateOf(true) }
    val size by animateDpAsState(
        targetValue = if (smallSize) 50.dp else 100.dp,
        animationSpec = tween(durationMillis = 500),
    )
    Box(
        modifier = Modifier
            .size(size = size)
            .background(Color.Cyan)
            .clickable { smallSize = !smallSize }
    )
}

@Composable
fun VisibilityAnimation() {
    var isVisible by rememberSaveable { mutableStateOf(value = true) }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = "Mostrar/Ocultar")
        }
        Spacer(modifier = Modifier.size(50.dp))

        AnimatedVisibility(isVisible, enter = slideInHorizontally(), exit = slideOutHorizontally())
        {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun CrossfadeExampleAnimation() {
    var myComponentType: ComponentType by rememberSaveable { mutableStateOf(ComponentType.Text) }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { myComponentType = getComponentTypeRandom() }) {
            Text(text = "Cambiar componente")
        }
        Crossfade(targetState = myComponentType) {
            when (it) {
                ComponentType.Image -> Box(
                    modifier =
                    Modifier
                        .size(150.dp)
                        .background(Color.Green)
                )
                ComponentType.Text -> Box(
                    modifier =
                    Modifier
                        .size(150.dp)
                        .background(Color.Yellow)
                )
                ComponentType.Box -> {
                    Box(
                        modifier =
                        Modifier
                            .size(150.dp)
                            .background(Color.Cyan)
                    )
                }
                ComponentType.Error -> Box(
                    modifier =
                    Modifier
                        .size(150.dp)
                        .background(Color.Red)
                )
            }
        }

    }
}

fun getComponentTypeRandom(): ComponentType {
    return when (nextInt(from = 0, until = 4)) {
        0 -> ComponentType.Image
        1 -> ComponentType.Text
        2 -> ComponentType.Box
        else -> ComponentType.Error
    }
}

enum class ComponentType() {
    Image, Text, Box, Error
}
