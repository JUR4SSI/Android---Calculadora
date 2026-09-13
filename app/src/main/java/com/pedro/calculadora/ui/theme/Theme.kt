package com.pedro.calculadora.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


enum class TemaDoAPP{
    CLARO,
    ESCURO,
    DINAMICO,
    MEDIEVAL
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    error = green80,
    onError = white100
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val MedievalColorScheme = lightColorScheme(
    background = Bege,
    surface = Castanho,
    onSurface = Marrom,

    primary = Vermelho,
    onPrimary = white100,

    secondary = Dourado,
    onSecondary = Marrom,

    error = Marrom50,
    onError = white100
)

private val formasPadrao = Shapes(
    small = RoundedCornerShape(50.dp)
)

val formasMedievais = Shapes(
    small = RoundedCornerShape(8.dp)
)



@Composable
fun CalculadoraTheme(
    temaDoAPP: TemaDoAPP,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val eschemaDeCor = when(temaDoAPP){
        TemaDoAPP.CLARO -> {
            LightColorScheme
        }
        TemaDoAPP.ESCURO -> {
            DarkColorScheme
        }
        TemaDoAPP.DINAMICO ->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                if (isSystemInDarkTheme()){
                    dynamicDarkColorScheme(context)
                } else{
                    dynamicLightColorScheme(context)
                }
            } else {
                LightColorScheme
            }
        }
        TemaDoAPP.MEDIEVAL -> {
            MedievalColorScheme
        }
    }

    MaterialTheme(
        colorScheme = eschemaDeCor,
        typography = if (temaDoAPP == TemaDoAPP.MEDIEVAL) TipografiaMedieval else Typography,
        shapes = if (temaDoAPP == TemaDoAPP.MEDIEVAL) formasMedievais else formasPadrao,
        content = content
    )
}