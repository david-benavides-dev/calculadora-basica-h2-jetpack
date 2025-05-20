package data.dao

import java.time.LocalDateTime

interface ICalculosDAO {
    fun agregar(fecha: LocalDateTime, num1: Double, operador: String, num2: Double, resultado: Double): Boolean
    fun obtenerTodos(): List<String>
}