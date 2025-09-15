package com.example.curso_introduccion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.scaleOut

class MainActivity : ComponentActivity() {
    companion object {
        // Creamos una constante
        const val MONEDA = "EUR"
    }

    var saldo = 300.54f
    var sueldo = 764.82f
    var entero: Int = 62

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Creamos una seria de variables de distintos tipos
        // val es como una especia de constante, no cambia, estos son los valores
        val fecha = "01/07/2025"
        // var si pueden cambiar, estas son las variables
        var nombre = "Alberto"
        var vip = true
        var inicial = 'A'
        
        // Creamos un saludo
        var saludo: String = "Hola ¿Cómo estás $nombre?"

        // Si el cliente es vip, al saludo se le añade con .plus el otro String
        if (vip) saludo = saludo.plus(", te queremos mucho")
        // En caso de que no sea vip se le pregunta si quiere serlo
        else saludo = saludo.plus(" ¿Quieres ser vip? Paga la cuota")

        // Imprimimos el saludo
        println(saludo)

        // Creamos el mes, lo obtenemos de la fecha creada anteriormente con .subSequence
        var mes = fecha.subSequence(3, 5).toString().toInt() // -> Con .toInt lo casteamos a Int
        // Obtenemos el día de la fecha
        var dia = fecha.subSequence(0, 2).toString().toInt()
        // Llamamos a la función de mostrar el saldo
        mostrarSaldo()
        // Si es día 1 se ingresará el sueldo
        if (dia == 1) ingresarSueldo()

        // Creamos un when, este cumple prácticamente la misma función que un Swich en Java
        when (mes) {
            1, 2, 3 -> println("\nEn invierno no hay oferta de inversiones")
            4, 5, 6 -> println("\n En primavera hay ofertas de inversores con el 1.5% de interés")
            7, 8, 9 -> println("\nEn verano hay ofertas de inversores con el 2.5% de interés")
            10, 11, 12 -> println("\nEn otoño hay ofertas de inversores con el 3.5%d de interés")
            // Es como el default del Swich, si no es ninguna de las anteriores, se ejecuta esta
            else -> println("\nLa fecha no es válida")
        }

        // Operadores lógicos (como en Java)
        var a = true
        var b = true
        var c = false
        var d = false

        // && -> AND
        // || -> OR
        if (a && b) println("a y b se cumplen")
        if (a && c) println("a y c se cumplen")
        if (!d || !b) println("d o b no se cumple")

        var pin = 1234
        var intentos = 0
        var claveIngresada = 1232

        // Bucle do-while en el cual se va a comprobar que el pin sea correcto y hay 3 intentos
        do {
            println("Introduce el PIN:")
            // Al poner en corchetes la clave con el post-incremento, el ++ no se mostrará como una cadena
            println("La clave ingresada es: ${claveIngresada++}")
            // En caso de que la clave sea igual al pin, se ejecuta un break por lo que se sale del bucle
            if (claveIngresada == pin) break
            intentos++

        } while (intentos < 3)

        // Llamamos a ingresar_dinero
        ingresarDinero(100f)
        // Llammos a retrar_dinero
        retirarDinero(50.08f)

        // Creamos un Array de los tipos de recibos
        var recibos: Array<String> = arrayOf("Luz", "Agua", "Gas")
        // Cambiamos el valor de la posición 2 del array
        recibos.set(2, "Internet")
        // Llamamos a la función
        recorrerArray(recibos)

        // Creamos una matríz de enteros
        var matriz: Array<IntArray> = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6, 8, 10, 12, 23),
            intArrayOf(7, 8, 9)
        )
        // Llamamos a la función
        recorrerMatriz(matriz)

        // Creamos una colección Set de enteros, esta es inmutable, inmodificbale
        val clientesVip: Set<Int> = setOf(1234, 8653, 9726, 4040)
        /* Se pueden crear Set mezclados, es decir que los valores que contienen
         sean de más de un tipo */
        val setMezclados = setOf(2, 4.87, "Casa", 'C')
        // Mostramos los clientes
        println("Clientes vip: ".plus(clientesVip))
        // Mostramos el set mezclado
        println("Set mezclado: ".plus(setMezclados))
        // Mostramos el número de clientes vip
        println("Número de clientes vip: ${clientesVip.size}")
        // Comprobamos si existe un cliente vip
        if (clientesVip.contains(1234)) println("1234 es cliente vip")

        // Creamos una colección MutableSet de enteros, esta se puede modificar
        var clientes: MutableSet<Int> = mutableSetOf(1234, 8653, 9726, 4040)
        // Añadimos clientes
        clientes.add(9091)
        clientes.add(1209)
        // Imprimimos el conjunto de clientes
        println("Clientes: $clientes")
        // Eliminamos un cliente
        clientes.remove(9091)
        // Volvemos a imprimir el conjunto de clientes
        println("Clientes: $clientes")
        // Limpiamos por completo el conjunto de clientes
        clientes.clear()
        // Volvemos a mostrar
        println("Clientes: $clientes")

        // Creamos una lista inmutable
        val divisas: List<String> = listOf("USD", "EUR", "YEN")
        // La imprimimos
        println(divisas)
        // Accedemos ahora a una posición de las divisas
        println(divisas.elementAt(1))

        // Creamos una lista mutable
        var empresas: MutableList<String> = mutableListOf("Adidas", "Amazon", "Nike", "Apple")
        // Añadimos otra empresa
        empresas.add("Coca-Cola")
        // Imprimimos la lista mutable
        println(empresas)
        // Eliminamos mediante índice una empresa
        empresas.removeAt(0)
        // Imprimimos las empresas
        println(empresas)
        // Imprimimos el primero y el último en la lista
        println("Primero: ${empresas.first()}\nÚltimo: ${empresas.last()}")
        // Si está vacía la lista de empresas se mostrará un mensaje
        if (empresas.none()) println("La lista de empresas está vacía")
        // Intetamos extraer el primero, en caso de que no haya se devolverá null
        println(empresas.firstOrNull())

        // Creamos un mapa, funcionan igual que en Java
        val mapa: Map<Int, String> = mapOf(
            1 to "España",
            2 to "Mexico",
            3 to "Colombia"
        )
        // Imprimimos el mapa de países
        println(mapa)
        // Accedemos a un valor por su clave
        println(mapa[2])

        // Creamos un mapa mutable
        var inversiones = mutableMapOf<String, Float>()
        // Añadimos varios inversores al mapa
        inversiones.put("Coca-Cola", 50f)
        inversiones.put("Pepsi", 100.4f)
        inversiones.put("Dinner Out Kebab", 246.28f)
        // Imprimimos el mapa
        println(inversiones)
        // Accedemos a un valor mediante su clave
        println("Inversión kebab: ${inversiones["Dinner Out Kebab"]}")
        // Eliminamos una empresa del mapa
        inversiones.remove("Coca-Cola")
        // Imprimimos el mapa de inversiones
        println(inversiones)

        var empresa: String?
        var cantidadInvertir = 90f
        var indice = 0
        // Creamos un bucle while para recorrer las inversiones e invertir
        while (saldo >= cantidadInvertir) {
            mostrarSaldo()
            empresa = empresas.elementAtOrNull(indice)
            if (empresa != null) {
                saldo -= 90
                indice++
                println("Se ha invertido en la empresa $empresa")

                if (!inversiones.containsKey(empresa)) {
                    inversiones.put(empresa, cantidadInvertir)

                } else {
                    var cantidadAntigua: Float? = inversiones[empresa]
                    if (cantidadAntigua != null) {
                        inversiones[empresa] = cantidadAntigua + cantidadInvertir
                    }
                }

            } else {
                indice = 0
            }
        }
        // Mostramos las inversiones
        println(inversiones)

        cuentaRegresiva()

        paresHasta10()

        mostrarContenidoArray(arrayOf("Arroz a la cubana", "Arroz con pollo",
            "Tortilla de papas"))
        mostrarContenidoArray2(arrayOf(setOf("Salchicha al vino", "7.5"),
            setOf("Paella", "10")))
        mostrarContenidoArray3(arrayOf(setOf(arrayOf("Canelones con chocolate"),
            arrayOf("9.61"), arrayOf("Queso", "Tomate", "Carne", "Chocolate")),
            setOf(arrayOf("Caldo de tortuga"), arrayOf("12.2"), arrayOf("Agua", "Fideos", "Tortuga"))))

        /*
        // Operadores de cálculo
        val suma = 5 + 5 // 10
        val resta = 10 - 5 // 5
        val multiplicacion = 5 * 5 // 25
        val division = 10 / 5 // 2
        val restoDivision = 10 % 3 // 1, se obtiene el resto de la división
        val divisionInfinita : Int =  10 / 6 // 1, porque aunque sea infinita al ser Int nos quedamos con la parte entera

        // Crecimientos y decrecimientos
        var preCrecimiento = 5
        var postCrecimiento = 5
        var preDecrecimiento = 5
        var postDecrecimiento = 5

        println("Pre-crecimiento")
        println(preCrecimiento)
        println(++preCrecimiento) // Se incrementa de inmediato
        println(preCrecimiento)

        println("Post-crecimiento")
        println(postCrecimiento)
        println(postCrecimiento++) // No se incrementa de inmediato
        println(postCrecimiento)

        println("Pre-decrecimiento")
        println(preDecrecimiento)
        println(--preDecrecimiento) // Se deincrementa de inmediato
        println(preDecrecimiento)

        println("Post-decrecimiento")
        println(postDecrecimiento)
        println(postDecrecimiento--) // No se deincremeta de inmediato
        println(postDecrecimiento)
        */
    }

    // Creamos una función (métodos en Java) que va a mostrar el saldo
    fun mostrarSaldo() {
        println("Tienes $saldo $MONEDA de saldo")
    }

    // Creamos una función que va a sumarle el sueldo al saldo
    fun ingresarSueldo() {
        saldo += sueldo
        println("Al saldo se le ha ingresado el sueldo de $sueldo $MONEDA")
        // Mostramos el saldo después del incremento
        mostrarSaldo()
    }

    // Creamos una función en la que se pueda ingresar el dinero que quieras
    fun ingresarDinero(dinero: Float) {
        saldo += dinero
        println("Al saldo se le ha ingresado $dinero $MONEDA")
        mostrarSaldo()
    }

    // Creamos una función en la que se va a retirar dinero del saldo
    fun retirarDinero(dinero: Float) {
        // Comprobamos si hay saldo suficiente
        if (comrpobarCantidad(dinero)) {
            saldo -= dinero
            println("Se han retirado $dinero $MONEDA del saldo")
            mostrarSaldo()

        } else println("Retirada mayor que el saldo")
    }

    // Creamos una función que va a verificar si el dinero que se quiere rertirar sobrepasa el saldo
    fun comrpobarCantidad(dinero: Float): Boolean {
        return saldo >= dinero
    }

    // Creamos una función para recorrer un array
    fun recorrerArray(array: Array<String>) {
        // Recorremos el array
        //for (i in array) println(i) // -> Imprimimos los elementos del array

        // Recorremos con el for, pero esta vez utilizando la i como en Java, un índice literal
        //for (i in array.indices) println(array.get(i)) // -> Con el .get devolvemos el valor

        // Recorremos de otra forma el array
        for (i in 0..array.size - 1) println(array.get(i))
    }

    // Creamos una función para recorrer una matríz
    fun recorrerMatriz(matriz: Array<IntArray>) {
        for (i in 0 until matriz.size) {
            for (j in 0 until matriz[i].size) {
                println("Posición[$i][$j] : ${matriz[i][j]}")
            }
        }
    }

    // Creamos una función que va a simular una cuenta regresiva del 9 al 0
    fun cuentaRegresiva() {
        var cuenta = 9
        do {
            println(cuenta--)

        } while (cuenta > -1)
    }

    // Creamos una función que va a mostrar los números pares del 1 al 10
    fun paresHasta10() {
        var indice = 1
        while (indice <= 10) {
            if (indice % 2 == 0) {
                println(indice)
            }
            indice++
        }
    }

    // Creamos una función que va a mostrar el contenido de un array
    fun mostrarContenidoArray(array: Array<String>) {
        for (i in array) {
            println("Plato: $i")
        }
    }

    // Creamos una función que va a mostrar el contenido de un array de conjuntos
    fun mostrarContenidoArray2(array: Array<Set<String>>) {
        for (i in array) {
            println("Plato: $i")
        }
    }

    // Creamos una función que va a mostrar el contenido de un array de conjuntos con un array dentro
    fun mostrarContenidoArray3(array: Array<Set<Array<String>>>) {
        for (i in array) {
            for (j in i) {
                /* Esta función nos permite imprimir el contenido de un Array, ya que de normal
                 no se puede como sí se puede con colecciones */
                println(j.contentDeepToString())
            }
        }
    }
}