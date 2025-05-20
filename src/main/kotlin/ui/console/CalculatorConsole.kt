package ui.console

import data.dao.ICalculosDAO
import services.CalculadoraService
import utils.FileManager
import utils.Utils
import utils.Utils.formatearFecha
import java.time.LocalDateTime

/**
 * Clase principal que representa el menú principal de la calculadora que interactúa con
 * el usuario para realizar operaciones matemáticas básicas.
 *
 * @property calculadora Servicio que realiza las operaciones matemáticas.
 * @property consola Interfaz de E/S de datos del usuario.
 */
class CalculatorConsole(private val calculadora: CalculadoraService, private val consola: IEntradaSalida, private val db: ICalculosDAO) {
    /**
     * Inicia el menú de la calculadora, mostrando las opciones y permitiendo
     * al usuario ingresar dos números y un operador para realizar una operación.
     * El resultado de la operación se muestra al usuario, además de guardarse en un archivo log.
     * El proceso se repite hasta que el usuario decida salir.
     * También se manejan las excepciones que puedan ocurrir durante la operación.
     *
     */
    fun menuCalculadora() {
        consola.mostrarMensaje("*** Calculadora ***", salto = true)
        var opcion = consola.pedirOpcion("¿Desea realizar cálculos? s/n > ")

        while (opcion) {
            try {
                val num1 = consola.pedirNumero()
                val operador = consola.pedirOperador()
                val num2 = consola.pedirNumero()

                val resultado = when (operador) {
                    "+" -> calculadora.sumar(num1, num2)
                    "-" -> calculadora.restar(num1, num2)
                    "*" -> calculadora.multiplicar(num1, num2)
                    "/" -> calculadora.dividir(num1, num2)
                    else -> {
                        consola.mostrarError("Operador no válido")
                        continue
                    }
                }
                consola.mostrarMensaje(Utils.redondearNumero(resultado), salto = true)
                db.agregar(LocalDateTime.now(), num1, operador, num2, resultado)
            } catch (e: IllegalArgumentException) {
                consola.mostrarError(e.message.toString())
                FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - ${e.message}")
            } catch (e: IllegalStateException) {
                FileManager.editFichero(texto = "${formatearFecha(LocalDateTime.now())} - ${e.message}")
                consola.mostrarError(e.message.toString())
            }
            opcion = consola.pedirOpcion("¿Desea seguir realizando cálculos? s/n > ")
        }
        if (db.obtenerTodos().isNotEmpty()) {
            db.obtenerTodos().forEach { println(it) }
        } else {
            consola.mostrarMensaje("Saliendo del programa...")
        }
    }
}