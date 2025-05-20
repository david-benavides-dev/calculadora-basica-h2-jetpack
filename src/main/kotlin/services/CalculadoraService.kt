package services

import model.ICalculadora

/**
 * Servicio que proporciona operaciones matemáticas utilizando una implementación
 * de la interfaz `ICalculadora`.
 *
 * Esta clase delega las operaciones matemáticas a una instancia de `ICalculadora`,
 * permitiendo realizar operaciones como suma, resta, multiplicación y división.
 *
 * @param calc Una instancia de `ICalculadora` que realiza las operaciones matemáticas.
 */
class CalculadoraService(private val calc: ICalculadora) : ICalculadoraService {

    /**
     * Realiza la suma de dos números.
     *
     * Delegado a la implementación de `sumar` de la clase `ICalculadora`.
     *
     * @param num1 El primer número a sumar.
     * @param num2 El segundo número a sumar.
     * @return El resultado de la suma de `num1` y `num2`.
     */
    override fun sumar(num1: Double, num2: Double): Double {
        return calc.sumar(num1, num2)
    }

    /**
     * Realiza la resta de dos números.
     *
     * Delegado a la implementación de `restar` de la clase `ICalculadora`.
     *
     * @param num1 El primer número (minuendo).
     * @param num2 El segundo número (sustraendo).
     * @return El resultado de restar `num2` de `num1`.
     */
    override fun restar(num1: Double, num2: Double): Double {
        return calc.restar(num1, num2)
    }

    /**
     * Realiza la multiplicación de dos números.
     *
     * Delegado a la implementación de `multiplicar` de la clase `ICalculadora`.
     *
     * @param num1 El primer número a multiplicar.
     * @param num2 El segundo número a multiplicar.
     * @return El resultado de multiplicar `num1` por `num2`.
     */
    override fun multiplicar(num1: Double, num2: Double): Double {
        return calc.multiplicar(num1, num2)
    }

    /**
     * Realiza la división de dos números.
     *
     * Delegado a la implementación de `dividir` de la clase `ICalculadora`.
     *
     * @param num1 El numerador.
     * @param num2 El denominador.
     * @return El resultado de dividir `num1` entre `num2`.
     */
    override fun dividir(num1: Double, num2: Double): Double {
        return calc.dividir(num1, num2)
    }
}