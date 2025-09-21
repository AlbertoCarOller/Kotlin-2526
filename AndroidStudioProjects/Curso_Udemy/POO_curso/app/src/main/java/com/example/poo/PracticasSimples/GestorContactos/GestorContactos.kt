package com.example.poo.PracticasSimples.GestorContactos

fun main() {
    var contacto = Contacto("Pepa", "6784587", "pepa@gmail.com")
    var contacto1 = Contacto("Pepa", "6784587", "pepa@gmail.com")
    var contacto2 = Contacto("Pepe", "7834534", "pepe@gmail.com")

    /* Aquí simplemente comprobamos si realmente son iguales o no, como es data class si todos sus
     atributos son iguales, pues serán iguales */
    println(contacto.equals(contacto1))
    println(contacto1.equals(contacto2))

    // También vamos a comprobar que las data class tienen un .toString propio
    println(contacto)
    println(contacto2)

    /* Las data class también tienen una función .copy que permite copiar el mismo objeto
    y también copiarlo cambiando el valor de uno de sus atributos */
    var contacto3 = contacto.copy(telefono = "00000000")
    println(contacto3)
}

/* Las data class son clases que solo permiten tener datos, no permite las funciones,
* además no tenemos que definir un equals, ya que tienen uno interno */
data class Contacto(var nombre: String, var telefono: String, var email: String)