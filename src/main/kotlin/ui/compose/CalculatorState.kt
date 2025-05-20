package ui.compose

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import services.CalculadoraService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import data.dao.ICalculosDAO
import kotlinx.coroutines.launch
import utils.FileManager
import utils.Utils.formatearFecha
import java.time.LocalDateTime

/**
 * Clase que representa el estado y la lógica de la calculadora.
 *
 * Maneja las entradas del usuario, las operaciones seleccionadas, los resultados,
 * y coordina los cálculos a través del servicio [CalculadoraService].
 * Además, persiste los resultados en una base de datos mediante la interfaz [ICalculosDAO],
 * y registra errores en un fichero mediante [FileManager].
 *
 * @property calculadoraService Servicio responsable de ejecutar las operaciones de la calculadora.
 * @property db Interfaz DAO para registrar los cálculos realizados.
 * @property coroutineScope Alcance de las corrutinas utilizado para operaciones asincrónicas.
 */
class CalculatorState(private val calculadoraService: CalculadoraService, private val db: ICalculosDAO, private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {

    /** Primer número ingresado. */
    var input1 by mutableStateOf("")

    /** Segundo número ingresado. */
    var input2 by mutableStateOf("")

    /** Resultado del cálculo actual. */
    var resultado by mutableStateOf("")

    /** Operación actual seleccionada (por ejemplo, +, -, etc.). */
    var operacionActual by mutableStateOf("")

    /**
     * Maneja la acción correspondiente cuando se presiona un botón.
     *
     * @param button Texto del botón presionado.
     */
    fun eventoBoton(button: String) {
        when (button) {
            "AC" -> reiniciarCalculos()
            "DL" -> borrarCaracter()
            "+", "-", "x", "/", "%" -> establecerOperador(button)
            "=" -> calcular()
            else -> agregarNumero(button)
        }
    }

    /**
     * Reinicia todos los campos de la calculadora a su estado inicial.
     */
    private fun reiniciarCalculos() {
        input1 = ""
        input2 = ""
        operacionActual = ""
        resultado = ""
    }

    /**
     * Elimina el último carácter ingresado.
     * Prioriza borrar de derecha a izquierda: input2 > operación > input1.
     */
    private fun borrarCaracter() {
        if (input2.isNotEmpty()) {
            input2 = input2.dropLast(1)
        } else if (operacionActual.isNotEmpty()) {
            operacionActual = ""
        } else if (input1.isNotEmpty()) {
            input1 = input1.dropLast(1)
        }
    }

    /**
     * Establece la operación si ya se ingresó un primer número.
     *
     * @param op Operador seleccionado.
     */
    private fun establecerOperador(op: String) {
        if (input1.isNotEmpty()) {
            operacionActual = op
        }
    }

    /**
     * Agrega un número (o punto decimal) a la entrada correspondiente según el estado actual.
     *
     * @param num Número o carácter ingresado.
     */
    private fun agregarNumero(num: String) {
        if (operacionActual.isEmpty()) {
            input1 += num
        } else {
            input2 += num
        }
    }

    /**
     * Realiza el cálculo en función de los valores ingresados y la operación seleccionada.
     *
     * Ejecuta el cálculo de forma asíncrona, actualiza el estado con el resultado,
     * registra el cálculo en la base de datos y maneja posibles errores mediante logs.
     */
    private fun calcular() {
        val num1 = input1.toDoubleOrNull()
        val num2 = input2.toDoubleOrNull()

        if (num1 != null && num2 != null && operacionActual.isNotEmpty()) {
            coroutineScope.launch {
                try {
                    val res = when (operacionActual) {
                        "+" -> calculadoraService.sumar(num1, num2)
                        "-" -> calculadoraService.restar(num1, num2)
                        "x" -> calculadoraService.multiplicar(num1, num2)
                        "/" -> calculadoraService.dividir(num1, num2)
                        "%" -> "No implementado"
                        else -> 0.0
                    }
                    resultado = res.toString()
                    db.agregar(LocalDateTime.now(), num1, operacionActual, num2, resultado.toDouble())
                    input1 = ""
                    input2 = ""
                    operacionActual = ""
                } catch (e: IllegalArgumentException) {
                    FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - ${e.message}")
                    resultado = "Error"
                    input1 = ""
                    input2 = ""
                    operacionActual = ""
                } catch (e: Exception) {
                    resultado = "Error"
                    FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - ${e.message}")
                    input1 = ""
                    input2 = ""
                    operacionActual = ""
                } catch (e: IllegalStateException) {
                    FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - ${e.message}")
                    resultado = "Error"
                    input1 = ""
                    input2 = ""
                    operacionActual = ""
                }
            }
        } else {
            FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - Error desconocido.")
            resultado = "Error"
            input1 = ""
            input2 = ""
            operacionActual = ""
        }
    }
}