package data.dao

import utils.Utils.formatearFecha
import java.sql.SQLException
import java.time.LocalDateTime
import javax.sql.DataSource

/**
 * Implementación de ICalculosDAO que interactúa con una base de datos H2 para
 * almacenar y recuperar cálculos.
 *
 * @property ds Fuente de datos que provee las conexiones a la base de datos H2.
 */
class CalculosDAOH2(val ds: DataSource) : ICalculosDAO {

    /**
     * Inserta el registro de un cálculo en la base de datos.
     *
     * @param fecha Fecha + hora en que se realizó el cálculo.
     * @param num1 Primer número del cálculo.
     * @param operador Operador matemático utilizado ("+", "-", "*" o "/").
     * @param num2 Segundo número del cálculo.
     * @param resultado Resultado del cálculo.
     * @return `true` si la inserción fue exitosa.
     * @throws IllegalStateException Si ocurre un error al intentar insertarlo en la base de datos.
     */
    override fun agregar(fecha: LocalDateTime, num1: Double, operador: String, num2: Double, resultado: Double): Boolean {
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(
                    "INSERT INTO Calculos (fecha, num1, operador, num2, resultado) VALUES (?, ?, ?, ?, ?)"
                ).use { stmt ->
                    stmt.setString(1, formatearFecha(fecha))
                    stmt.setDouble(2, num1)
                    stmt.setString(3, operador)
                    stmt.setDouble(4, num2)
                    stmt.setDouble(5, resultado)
                    stmt.executeUpdate()
                }
            }
            return true
        } catch (e: SQLException) {
            throw IllegalStateException("No se pudo insertar el cálculo en la base de datos")
        }
    }

    /**
     * Recupera todos los cálculos almacenados en la base de datos.
     *
     * Cada cálculo se devuelve como una cadena con formato:
     * `fecha - num1 operador num2 = resultado`.
     *
     * @return Una lista de strings con los cálculos almacenados.
     */
    override fun obtenerTodos(): List<String> {

        val calculos = mutableListOf<String>()

        ds.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery("SELECT fecha, num1, operador, num2, resultado FROM Calculos").use { rs ->
                    while (rs.next()) {
                        val fecha = rs.getString("fecha")
                        val num1 = rs.getDouble("num1")
                        val operador = rs.getString("operador")
                        val num2 = rs.getDouble("num2")
                        val resultado = rs.getString("resultado")

                        calculos.add("$fecha - $num1 $operador $num2 = $resultado")
                    }
                }
            }
        }
        return calculos
    }
}