package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Singleton con utilidades varias.
 */
object Utils {

    /**
     * Redondea un número a una cantidad específica de decimales y lo devuelve como string.
     *
     * @param numero El número a redondear.
     * @param decimales El número de decimales a los que se debe redondear el número. Por defecto 2.
     * @return El número redondeado como una cadena con el formato especificado.
     */
    fun redondearNumero(numero: Double, decimales: Int = 2): String {
        return String.format("%.${decimales}f", numero)
    }

    /**
     * Formatea un objeto tipo LocalDateTime a una cadena de texto con el patrón `yyyyMMddHHmmss`.
     *
     * Ejemplo de salida: `"20250520143055"` representaría 20 de mayo de 2025 a las 14:30:55.
     *
     * @param fecha Objeto de tipo fecha que se desea formatear.
     * @return Una cadena de texto con la fecha formateada según el patrón establecido.
     */
    fun formatearFecha(fecha: LocalDateTime): String {
        val formatoFecha: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return fecha.format(formatoFecha)
    }
}