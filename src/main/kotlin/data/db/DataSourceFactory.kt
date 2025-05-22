package data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.*
import javax.sql.DataSource

/**
 * Singleton responsable de gestionar la conexión con la base de datos H2.
 * Además, contiene el método para la creación de la tabla inicial.
 *
 * La base de datos se almacena en el archivo `./data/calculos.mv.db`.
 */
object DataSourceFactory {
    private const val URL = "jdbc:h2:file:./data/calculos"
    private const val USER = "user"
    private const val PWD = "pwd"

    // Configuración HikariCP
    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = URL
        username = USER
        password = PWD
        driverClassName = "org.h2.Driver"
        maximumPoolSize = 10
    }

    /** FIX: DataSource configurado para la conexión a la base de datos, reutilizado para evitar la creación de múltiples pools de conexiones.*/
    private val dataSrc = HikariDataSource(hikariConfig)

    // ***************************************
    // Tabla de cálculos para inicializar al inicio del programa.
    // ***************************************
    private const val TABLA_CALCULOS = """
        CREATE TABLE IF NOT EXISTS Calculos (
            fecha VARCHAR(14) PRIMARY KEY,
            num1 DOUBLE NOT NULL,
            operador VARCHAR(10) NOT NULL,
            num2 DOUBLE NOT NULL,
            resultado DOUBLE NOT NULL
        );
        """

    /**
     * Obtiene una instancia de DataSource configurada con HikariCP.
     *
     * El método crea y devuelve un objeto de tipo DataSource utilizando la configuración
     * proporcionada en una constante. Se utiliza para gestionar conexiones a la base de datos.
     *
     * @return una instancia tipo DataSource, representando la fuente de datos.
     */
    fun getDataSource(): DataSource {
        return HikariDataSource(dataSrc)
    }

    /**
     * Inicializa la base de datos creando la tabla `Calculos` si no existe, ejecutando la sentencia SQL definida
     * en la constante TABLA_CALCULOS.
     *
     * @throws IllegalStateException si ocurre un error durante la creación de la tabla.
     */
    fun initDatabase() {
        try {
            getDataSource().connection.use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.executeUpdate(TABLA_CALCULOS)
                }
            }
        } catch (e: SQLException) {
            throw IllegalStateException("Error al crear la tabla.")
        }
    }
}