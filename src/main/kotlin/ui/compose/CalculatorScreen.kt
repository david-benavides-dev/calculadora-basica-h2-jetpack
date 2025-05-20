package ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lista de strings para representar los botones del teclado de la calculadora.
 */
val buttonlist = listOf(
    "AC", "DL", "%", "/",
    "7", "8", "9", "x",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    " ", "0", ".", "="
)

/**
 * Composable que representa la pantalla principal de la calculadora.
 *
 * Muestra la entrada actual, la operación seleccionada, el resultado y el teclado numérico.
 *
 * @param calculatorState Estado actual de la calculadora que contiene entradas, operación y resultado,
 *                       además de la lógica para manejar eventos de los botones gracias a su service.
 */
@Composable
fun CalculatorScreen(calculatorState: CalculatorState) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1C1C1C), Color(0xFF2E2E2E)),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = calculatorState.input1 + calculatorState.operacionActual + calculatorState.input2,
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = calculatorState.resultado,
                style = TextStyle(
                    fontSize = 48.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                ),
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
            ) {
                items(buttonlist) { button ->
                    CalculatorButton(button) {
                        calculatorState.eventoBoton(button)
                    }
                }
            }
        }
    }
}

/**
 * Composable que representa un botón individual de la calculadora.
 *
 * Asigna los colores de fondo según el tipo de botón
 * y notifica cuando el botón es presionado.
 *
 * @param text Texto que se muestra en el botón.
 * @param onClick Lambda que se ejecuta al hacer clic en el botón.
 */
@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    val backgroundColor = when (text) {
        "AC", "DL" -> Color(0xFF757575)
        "+", "-", "x", "/", "%" -> Color(0xFFFF9800)
        "=" -> Color(0xFFFB8C00)
        else -> Color(0xFF4D4D4D)
    }
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(6.dp)
            .aspectRatio(1f)
            .defaultMinSize(minHeight = 64.dp)
            .fillMaxWidth(),

        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        elevation = ButtonDefaults.elevation(defaultElevation = 6.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
    ) {
        Text(text = text, fontSize = 22.sp)
    }
}