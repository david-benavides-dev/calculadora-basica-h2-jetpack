package model

/**
 * Enum que representa los operadores matemáticos básicos: suma, resta, multiplicación y división.
 * Cada operador está asociado a su símbolo correspondiente.
 *
 * @property operator El símbolo del operador (por ejemplo, "+" para la suma).
 */
enum class Operadores(val operator: String) {
    SUMA("+"),
    RESTA("-"),
    MULTIPLICACION("*"),
    DIVISION("/");

    /**
     * Obtenemos el operador correspondiente a un valor de cadena.
     *
     * Este method devuelve el operador correspondiente al símbolo de la cadena
     * pasada como parámetro, si es un operador válido. En caso contrario, retorna `null`.
     *
     * @param valor El símbolo del operador en formato de cadena (por ejemplo, "+" para la suma).
     * @return El operador correspondiente o `null` si el símbolo no es válido.
     */
    companion object {
        fun getOperador(valor: String): Operadores? {
            return when (valor.trim().lowercase()) {
                "+" -> SUMA
                "-" -> RESTA
                "*" -> MULTIPLICACION
                "/" -> DIVISION
                else -> null
            }
        }
    }
}