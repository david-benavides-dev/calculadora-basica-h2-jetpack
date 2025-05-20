package utils

import java.io.File


/**
 * Singleton para gestionar ficheros y directorios.
 */
object FileManager {
    private const val RUTA = "./data"
    private const val NOMBRE_LOG = "log.txt"

    /**
     * Escribe una línea de texto al final de un fichero.
     *
     * Si el fichero no existe, se crea automáticamente antes de escribir.
     *
     * @param ruta Ruta del directorio donde se encuentra o se creará el fichero.
     * @param nombreFichero Nombre del fichero a editar o crear. Por defecto `log.txt`.
     * @param texto Línea de texto que se desea registrar en el fichero.
     */
    fun editFichero(ruta: String = RUTA, nombreFichero: String = NOMBRE_LOG, texto: String) {
        if (!comprobarFichero(ruta, nombreFichero)) {
            File(ruta, nombreFichero).createNewFile()
            File(ruta, nombreFichero).appendText(texto + "\n")
        } else {
            File(ruta, nombreFichero).appendText(texto + "\n")
        }
    }

    /**
     * Verifica si un fichero existe y es un archivo regular.
     *
     * @param ruta Ruta del directorio donde se espera encontrar el fichero.
     * @param nombreFichero Nombre del fichero.
     * @return `true` si el fichero existe y es válido, `false` en el caso contrario.
     */
    fun comprobarFichero(ruta: String, nombreFichero: String): Boolean {
        return File(ruta, nombreFichero).exists() && File(ruta, nombreFichero).isFile
    }
}