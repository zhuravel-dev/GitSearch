package com.example.gitsearch.ui.compose.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = DarkGrey,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = LightGray,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = DarkGrey,
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black,
    onSurface = Color.White,
)

@ExperimentalMaterialApi
@Composable
fun AppTheme(
    /*displayProgressBar: Boolean,
    scaffoldState: ScaffoldState,*/
    content: @Composable () -> Unit,
) {
    MaterialTheme(
       // colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        shapes = AppShapes
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
               // .background(color = if(!darkTheme) Grey1 else Color.Black)
        ){
            content()
           /* CircularProgress(
                isDisplayed = displayProgressBar,
                0.3f
            )*/
        }
    }
}

/*
@Composable
fun JetpackComposeAndroidExamplesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}*/
