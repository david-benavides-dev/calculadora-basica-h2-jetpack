package ui.console

interface IEntradaSalida {
    fun mostrarMensaje(msj: String, pausa: Boolean = false, limpiar: Boolean = false, salto: Boolean = false)
    fun mostrarError(msj: String, msjError: String = "*ERROR* ")
    fun pedirOperador(): String
    fun pedirNumero(): Double
    fun pedirOpcion(msj: String): Boolean
    fun limpiarTerminal()
    fun pausar(msjPausa: String = "Presiona ENTER para continuar...")
}