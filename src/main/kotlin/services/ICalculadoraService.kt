package services

interface ICalculadoraService {
    fun sumar(num1: Double, num2: Double): Double
    fun restar(num1: Double, num2: Double): Double
    fun multiplicar(num1: Double, num2: Double): Double
    fun dividir(num1: Double, num2: Double): Double
}