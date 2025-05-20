package ui.console

import model.Operadores
import java.util.*

/**
 * Clase que implementa la interfaz `IEntradaSalida`, proporcionando una manera
 * de interactuar con el usuario a través de la consola. Permite mostrar mensajes,
 * solicitar entradas, manejar errores y limpiar la terminal.
 */
class Consola : IEntradaSalida {

    private val scanner = Scanner(System.`in`)

    /**
     * Muestra un mensaje al usuario con diversas opciones de configuración,
     * como pausa, limpieza de terminal y salto de línea.
     *
     * @param msj El mensaje que se mostrará al usuario.
     * @param pausa Si es `true`, pausa la ejecución hasta que el usuario presione una tecla.
     * @param limpiar Si es `true`, limpia la terminal antes de mostrar el mensaje.
     * @param salto Si es `true`, muestra el mensaje con un salto de línea, si es `false`, sin salto.
     */
    override fun mostrarMensaje(msj: String, pausa: Boolean, limpiar: Boolean, salto: Boolean) {

        if (limpiar) {
            limpiarTerminal()
        }

        if (salto) {
            println(msj)
        } else {
            print(msj)
        }

        if (pausa) {
            pausar()
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param msj El mensaje de error que se mostrará al usuario.
     * @param msjError El prefijo que se agregará al mensaje de error.
     */
    override fun mostrarError(msj: String, msjError: String) {
        mostrarMensaje(msjError + msj, salto = true, pausa = true)
    }

    /**
     * Solicita al usuario que ingrese un operador válido.
     *
     * @return El operador introducido por el usuario.
     * @throws IllegalArgumentException Si el operador ingresado no es válido.
     */
    override fun pedirOperador(): String {
        mostrarMensaje("Introduce un operador > ")
        val operador = scanner.nextLine()
        val resultado = Operadores.getOperador(operador) ?: throw IllegalArgumentException("Introduce un operador válido.")

        return resultado.operator
    }

    /**
     * Solicita al usuario que ingrese un número.
     *
     * @return El número introducido por el usuario.
     * @throws IllegalArgumentException Si la entrada no es un número válido.
     */
    override fun pedirNumero(): Double {
        mostrarMensaje("Introduce un número > ")
        val entrada = scanner.nextLine()
        val numero = entrada.toDoubleOrNull() ?: throw IllegalArgumentException("Introduce un número válido.")

        return numero
    }

    /**
     * Pausa la ejecución hasta que el usuario presione una tecla.
     */
    override fun pausar(msjPausa: String) {
        mostrarMensaje(msjPausa)
        readln()
    }

    /**
     * Limpia la terminal imprimiendo varias líneas vacías (20).
     */
    override fun limpiarTerminal() {
        repeat(20) {
            println("\n")
        }
    }

    /**
     * Solicita al usuario si desea continuar realizando cálculos.
     *
     * @return `true` si el usuario desea continuar, `false` si no.
     */
    override fun pedirOpcion(msj: String): Boolean {
        mostrarMensaje(msj)
        return when (scanner.nextLine().lowercase().trim()) {
            "s" -> true
            "n" -> false
            else -> false
        }
    }
}