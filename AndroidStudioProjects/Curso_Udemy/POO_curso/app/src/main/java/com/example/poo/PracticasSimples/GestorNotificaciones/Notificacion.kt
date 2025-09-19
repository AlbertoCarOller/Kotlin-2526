package com.example.poo.PracticasSimples.GestorNotificaciones

fun main() {
    var emailNotificacion = EmailNotificacion("Buenas tardes, le hablamos del equipo de...")
    gestorMensajes.enviarNotificacion(emailNotificacion)

    // Creamos una clase anónima que va a implementar de 'Notificable'
    var notificacinAnonima = object : Notificable {
        override fun enviar() {
            println("Mensaje anónimo, CUIDADO CHELU")
        }
    }
    var notificacionSMS = SMSNotificacion("Hola, cómo estás Chelu?")

    gestorMensajes.enviarNotificacion(notificacinAnonima)
    gestorMensajes.enviarNotificacion(notificacionSMS)
}

interface Notificable {
    fun enviar()
}

abstract class Notificacion(protected var mensaje: String) : Notificable {
    fun obtenerMensaje(): String {
        return mensaje
    }
}

class EmailNotificacion(mensaje: String) : Notificacion(mensaje) {
    override fun enviar() {
        println("El mensaje fué enviado por email: ${this.mensaje}")
    }
}

class SMSNotificacion(mensaje: String) : Notificacion(mensaje) {
    override fun enviar() {
        println("El mensaje fué enviado por SMS: ${this.mensaje}")
    }
}

// Creamos un objeto singleton
object gestorMensajes {
    fun enviarNotificacion(notificable: Notificable) {
        notificable.enviar()
    }
}