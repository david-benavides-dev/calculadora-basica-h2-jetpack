package data.dao

import data.db.DataSourceFactory
import utils.Utils.formatearFecha
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import javax.sql.DataSource

/**
 *
 */
class CalculadoraDAOH2Simple : ICalculosDAO {
    /**
     *
     */
    override fun agregar(fecha: LocalDateTime, num1: Double, operador: String, num2: Double, resultado: Double): Boolean {
        var ds: DataSource? = null
        var conn: Connection? = null
        var stmt: PreparedStatement? = null

        try {
            ds = DataSourceFactory.getDataSource()
            conn = ds.connection

            val sql = "INSERT INTO calculos (fecha, num1, operador, num2, resultado) VALUES (?, ?, ?, ?, ?)"

            stmt = conn.prepareStatement(sql)
            stmt.setString(1, formatearFecha(fecha))
            stmt.setDouble(2, num1)
            stmt.setString(3, operador)
            stmt.setDouble(4, num2)
            stmt.setDouble(5, resultado)

            return stmt.executeUpdate() > 0

        } catch (e: SQLException) {
            throw IllegalStateException("No se pudo insertar el cálculo en la base de datos")
        } finally {
            try {
                stmt?.close()
                conn?.close()
            } catch (e: Exception) {
                throw IllegalStateException("Error con el cierre de la conexión con la base de datos. Null found.")
            }
        }
    }

    /**
     *
     */
    override fun obtenerTodos(): List<String> {
        val todoCalculos: MutableList<String> = mutableListOf()

        var ds: DataSource? = null
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            val sql = "SELECT * FROM calculos"
            ds = DataSourceFactory.getDataSource()
            conn = ds.connection
            stmt = conn.prepareStatement(sql)

            rs = stmt.executeQuery()

            while (rs.next()) {
                val fecha = rs.getString("fecha")
                val num1 = rs.getDouble("num1")
                val operador = rs.getString("operador")
                val num2 = rs.getDouble("num2")
                val resultado = rs.getDouble("resultado")

                val registro = "$fecha - $num1 $operador $num2 = $resultado"

                todoCalculos.add(registro)
            }
        } catch (e: SQLException) {
            throw IllegalStateException("Error con la base de datos")
        } finally {
            conn?.close()
            stmt?.close()
            rs?.close()
        }
        return todoCalculos
    }
}