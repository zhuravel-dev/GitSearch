package com.example.gitsearch.ui.compose.theme

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = White,
    secondary = White,
    secondaryVariant = Gray,
    onSecondary = Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = LightBackground,
    onBackground = Gray600,
    surface = Blue400,
    onSurface = White,
)

private val DarkThemeColors = darkColors(
    primary = Gray900,
    primaryVariant = Gray900,
    onPrimary = Gray300,
    secondary = Gray500,
    onSecondary = Gray300,
    error = RedErrorLight,
    background = Gray700,
    onBackground = Gray300,
    surface = Gray900,
    onSurface = Gray300,
)

@ExperimentalMaterialApi
@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors
    ) {
        val systemUiController = rememberSystemUiController()
        if(darkTheme){
            systemUiController.setSystemBarsColor(
                color = Gray900
            )
        }else{
            systemUiController.setSystemBarsColor(
                color = Blue600
            )
        }
        content()
    }
}