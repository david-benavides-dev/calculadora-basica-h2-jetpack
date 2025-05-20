package model

/**
 * Implementación de la interfaz `ICalculadora` que proporciona operaciones matemáticas
 * básicas.
 */
class Calculadora : ICalculadora {

    /**
     * Suma dos números.
     *
     * @param num1 El primer número a sumar.
     * @param num2 El segundo número a sumar.
     * @return El resultado de la suma de `num1` y `num2`.
     */
    override fun sumar(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    /**
     * Resta dos números.
     *
     * @param num1 El primer número (minuendo).
     * @param num2 El segundo número (sustraendo).
     * @return El resultado de restar `num2` de `num1`.
     */
    override fun restar(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    /**
     * Multiplica dos números.
     *
     * @param num1 El primer número a multiplicar.
     * @param num2 El segundo número a multiplicar.
     * @return El resultado de multiplicar `num1` por `num2`.
     */
    override fun multiplicar(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    /**
     * Divide dos números.
     *
     * @param num1 El numerador.
     * @param num2 El denominador.
     * @return El resultado de dividir `num1` entre `num2`.
     * @throws IllegalArgumentException Si `num2` es igual a cero.
     */
    override fun dividir(num1: Double, num2: Double): Double {
        if (num2 == 0.0) {
            throw IllegalArgumentException("El divisor no puede ser cero.")
        }
        return num1 / num2
    }
}