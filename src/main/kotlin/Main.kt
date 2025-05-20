import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import data.dao.CalculosDAOH2
import data.db.DataSourceFactory
import model.Calculadora
import services.CalculadoraService
import ui.compose.CalculatorScreen
import ui.compose.CalculatorState
import ui.console.CalculatorConsole
import ui.console.Consola

/**
 * Inicializa la base de datos y determina el modo de ejecución de la calculadora:
 * - Si se pasan argumentos por línea de comandos, se lanza la versión de consola.
 * - En caso contrario, se inicia con interfaz gráfica.
 *
 * @param args Argumentos opcionales pasados desde la línea de comandos.
 */
fun main(args: Array<String>) {

    // Inicializa la base de datos y crea las tablas necesarias si no existen.
    DataSourceFactory.initDatabase()

    val dataSrc = try {
        DataSourceFactory.getDataSource()
    } catch (e: IllegalStateException) {
        println("Error en la creacion de la base de datos: ${e.message}")
        return
    }

    // Si hay argumentos, se ejecuta la calculadora en modo consola.
    if (args.isNotEmpty()) {
        CalculatorConsole(CalculadoraService(Calculadora()), Consola(), CalculosDAOH2(dataSrc)).menuCalculadora()
    } else {
        // Si no, se ejecuta el modo interfaz gráfica.
        application {
            val windowState = WindowState(width = 360.dp, height = 600.dp)

            Window(
                onCloseRequest = ::exitApplication,
                title = "Calculadora",
                state = windowState,
                resizable = false
            ) {
                CalculatorScreen(CalculatorState(CalculadoraService(Calculadora()), CalculosDAOH2(dataSrc)))
            }
        }
    }
}